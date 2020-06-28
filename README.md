# BigSea

使用了洋神的[玩安卓](https://www.wanandroid.com/)API
视频接口使用了开眼的api(仅学习使用)

- 封装Retrofit和协程完成网络请求
- 组件化开发
- 自定义navigation，使用起来更灵活，自定义FragmentNavigator解决底部导航栏切换导致的fragment重建，支持底部导航栏数量配置，支持组件之间跳转和传值。
- 使用paging3.0组件实现列表的滑动加载，下拉刷新使用[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
- 自定义LiveDataBus实现页面间，组件间通信
- 使用Room数据库缓存对象
- 使用cameraX完成拍照和视频录制的功能
- ....还有一些优秀的开源库

![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/home_page.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/home_video.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_take.png)