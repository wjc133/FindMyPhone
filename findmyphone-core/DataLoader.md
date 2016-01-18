#DataLoader使用说明
1. 目前提供了两类Loader，DataLoader 和 AsyncDataLoader，分别对应同步加载和异步加载。
2. 注意Loader可分为两类，一类是调用GET接口，返回页面数据；一类是调用POST接口，执行一个数据操作的。
 **for GET：**
 在页面初始化处（onCreate or onActivityCreated） initLoader(0, null, this);
 需要加载数据处 restartLoader(id, bundle, callback);
 实现对应回调接口 FilterDataLoaderCallback
 **for POST：**
 需要加载数据处 restartLoader(id, bundle, callback);
 实现对应回调接口 FilterDataLoaderCallback，注意 onCreateLoader时，loader的构造函数里 public BaseLoader(Context context, boolean destroyOnLoadFinish), destroyOnLoadFinish 须为true，即保证了该loader的回调是一次性的，下次界面onStart时不会再次回调执行结果。
3. Loader的生命周期是和所依附的activity或Fragment是一致的，每个activity或Fragment里的loader都是独立的实例，所以不同组件里的loader id是不会冲突的，但单个组件里的loader id需要区分开。
 