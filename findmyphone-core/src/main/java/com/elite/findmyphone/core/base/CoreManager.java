package com.elite.findmyphone.core.base;

import android.util.Log;

import com.elite.findmyphone.core.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by wjc133
 * Date: 2015/12/30
 * Time: 20:42
 * 回调管理
 */
public class CoreManager {
    private static final String TAG = "CoreManager";
    //接口class与注册为该接口实现类的映射关系
    private static Map<Class<? extends CoreClient>, List<CoreClient>> clients;
    //接口class与该接口方法表的映射关系
    private static Map<Class<? extends CoreClient>, Map<String, Method>> clientMethods;

    static {
        clients = new HashMap<>();
        clientMethods = new HashMap<>();
    }


    /**
     * 如果clientClass还未被注册到回调管理器中，则对其进行注册
     *
     * @param clientClass CoreClient的实例
     */
    private static void addClientMethodsIfNeeded(Class<? extends CoreClient> clientClass) {
        Map<String, Method> methods = clientMethods.get(clientClass);
        if (methods == null) {
            methods = new HashMap<>();
            Method[] allMethods = clientClass.getMethods();
            for (Method m : allMethods) {
                methods.put(m.getName(), m);
            }
            clientMethods.put(clientClass, methods);
        }
    }

    /**
     * 监听某个接口的回调，监听者需要实现该接口
     * 注意在不需要回调时要用removeClient
     *
     * @param clientClass 接口
     * @param client      监听者
     */
    public static void addClient(Class<? extends CoreClient> clientClass, CoreClient client) {

        if (clientClass == null || client == null) {
            return;
        }
        //查看该接口之前是否有注册过其他的实现类，没有就新建一个实现类的列表
        List<CoreClient> clientList = clients.get(clientClass);
        if (clientList == null) {
            clientList = new ArrayList<>();
            clients.put(clientClass, clientList);
        }

        //添加接口方法映射关系
        addClientMethodsIfNeeded(clientClass);

        if (clientList.contains(client)) {
            return;
        }

        clientList.add(client);
        Log.d(TAG, "client(" + client + ") added for " + clientClass.getName());
    }

    private static void addClient(CoreClient client, Class<?> clientClass) {
        if (clientClass == null)
            return;

        Class<?>[] interfaces = clientClass.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            //判断该接口是否是CoreClient的子接口，是则强转
            if (CoreClient.class.isAssignableFrom(anInterface)) {
                Class<? extends CoreClient> intf = (Class<? extends CoreClient>) anInterface;
                CoreManager.addClient(intf, client);
                //logger.info("client(" + client + ") added for " + clientClass.getName());
            }
        }

        //继续查看该类的父类有无继承CoreClient接口，以此确保之前实现的所有CoreClient都被注册
        Class<?> superClass = clientClass.getSuperclass();
        addClient(client, superClass);
    }

    /**
     * 监听所有client声明实现的CoreClient的接口
     *
     * @param client 监听者
     */

    public static void addClient(CoreClient client) {

        if (client == null) {
            return;
        }

        addClient(client, client.getClass());
    }

    /**
     * 移除对象对某个接口的监听
     *
     * @param clientClass
     * @param client
     */
    public static void removeClient(Class<? extends CoreClient> clientClass, CoreClient client) {

        if (clientClass == null || client == null) {
            return;
        }

        List<CoreClient> clientList = clients.get(clientClass);
        if (clientList == null) {
            return;
        }

        clientList.remove(client);
    }


    /**
     * 移除该对象所有监听接口
     *
     * @param client
     */
    public static void removeClient(CoreClient client) {

        if (client == null) {
            return;
        }

        Collection<List<CoreClient>> c = clients.values();
        for (List<CoreClient> list : c) {
            list.remove(client);
        }

    }


    /**
     * 返回监听该接口的对象列表
     *
     * @param clientClass
     * @return
     */
    public static List<CoreClient> getClients(Class<? extends CoreClient> clientClass) {

        if (clientClass == null) {
            return null;
        }

        List<CoreClient> clientList = clients.get(clientClass);
        if (clientList != null) {
            // 每次均构造一个新的对象返回，防止遍历中修改出问题
            clientList = new ArrayList<CoreClient>(clientList);
        }

        return clientList;
    }

    public static interface ICallBack {
        void onCall(CoreClient client);
    }

    /**
     * 执行回调接口
     *
     * @param clientClass
     * @param callBack
     */
    public static void notifyClients(Class<? extends CoreClient> clientClass, ICallBack callBack) {
        if (clientClass == null || callBack == null) {
            return;
        }

        List<CoreClient> clientList = CoreManager.getClients(clientClass);
        if (clientList == null) {
            return;
        }
        try {
            int length = clientList.size();
            for (int i = 0; i < length; i++) {
                CoreClient ic = clientList.get(i);
                callBack.onCall(ic);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 回调所有监听了该接口的对象。methodName为回调的方法名
     * 注意：所有用addClient和addEventListener注册了此接口的对象都会被回调
     * 注意：methodName所指定函数的参数列表个数必须匹配。目前没有对参数类型严格检查，使用时要注意
     *
     * @param clientClass
     * @param methodName
     * @param args
     */
    public static void notifyClients(Class<? extends CoreClient> clientClass, String methodName, Object... args) {

        if (clientClass == null || methodName == null || methodName.length() == 0) {
            return;
        }

        List<CoreClient> clientList = CoreManager.getClients(clientClass);
        if (clientList == null) {
            return;
        }

        try {

            Map<String, Method> methods = clientMethods.get(clientClass);
            Method method = methods.get(methodName);

            if (method == null) {
                Log.e(TAG, "cannot find client method " + methodName + " for args[" + (args == null ? 0 : args.length) + "]: " + StringUtils.join(args, ','));
                return;
            } else if (args != null && method.getParameterTypes().length != args.length) {
                Log.e(TAG, "method " + methodName + " param number not matched: method(" + method.getParameterTypes().length + "), args(" + args.length + ")");

                return;
            }
            for (CoreClient c : clientList) {
                method.invoke(c, args);
            }

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static <T extends BaseCore> T getCore(Class<T> cls) {
        return CoreFactory.getCore(cls);
    }

}
