package com.elite.findmyphone.core.base;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by wjc133
 * Date: 2015/12/30
 * Time: 20:42
 */
public class CoreFactory {
    //接口映射表
    private static final Map<Class<? extends BaseCore>, BaseCore> CORES;
    //实现映射表
    private static final Map<Class<? extends BaseCore>, Class<? extends AbstractBaseCore>> CORE_CLASSES;

    static {
        CORES = new HashMap<>();
        CORE_CLASSES = new HashMap<>();
    }

    /**
     * 实现了BaseCore接口的业务实例通过registerCoreClass方法注册后
     * 就可以使用getCore拿到该业务实例。
     *
     * @param cls 集成了BaseCore的业务接口的Class
     * @return 如果生成对象失败，返回null
     */
    public static <T extends BaseCore> T getCore(Class<T> cls) {

        if (cls == null) {
            return null;
        }

        BaseCore core = CORES.get(cls);
        if (core == null) {
            Class<? extends AbstractBaseCore> implClass = CORE_CLASSES.get(cls);
            try {
                if (implClass == null) {
                    if (cls.isInterface()) {
                        Log.e("CoreFactory",
                                "No registered core class for: "
                                        + cls.getName());
                    } else {
                        // 未找到对应的实现类，且传入的不是接口，则传入的本身就是一个实现类，直接使用此类生成对象
                        core = cls.newInstance();
                    }
                } else {
                    //找到了对应的实现类，则实例化此实现类
                    core = implClass.newInstance();
                }

                if (core != null) {
                    CORES.put(cls, core);
                    Log.i("CoreFactory", cls.getName() + " created: "
                            + ((implClass != null) ? implClass.getName() : cls.getName()));
                }

            } catch (Exception e) {
                Log.e("CoreFactory", "newInstance() failed for: "
                        + ((implClass != null) ? implClass.getName() : cls.getName()), e);
            }
        }

        return (T) core;
    }

    /**
     * 注册某个接口实现类
     *
     * @param coreInterface 继承了BaseCore的业务接口
     * @param coreClass     该业务接口的实现类
     */
    public static void registerCoreClass(Class<? extends BaseCore> coreInterface, Class<? extends AbstractBaseCore> coreClass) {
        if (coreInterface == null || coreClass == null) {
            return;
        }

        CORE_CLASSES.put(coreInterface, coreClass);
        Log.d("CoreFactory", "registered class " + coreClass.getName() + " for core: " + coreInterface.getName());
    }

    /**
     * 返回某个接口是否有注册实现类
     *
     * @param coreInterface
     * @return
     */
    public static boolean hasRegisteredCoreClass(Class<? extends BaseCore> coreInterface) {
        if (coreInterface == null) {
            return false;
        }
        return CORE_CLASSES.containsKey(coreInterface);
    }
}
