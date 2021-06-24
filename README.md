# hongpeng
一个在线课程客户端
- 使用 MVVM 和 MVP 架构
- 导航组件的配置采用注解形式，利用 json 作为中介完成配置
- 改进 Fragment 之间切换的方式，用 hide/show 来代替 replace，使得浏览位置得以保留
- 使用 Paging 框架进行数据的加载，改进 PagedList 实现增删改功能，利用 Room 数据库对网络数据
进行缓存
- 自定义 ExoPlayer 的 View 中的控制器，通过反射调用 StyledPlayerControlView 中的方法实现全屏
功能。
