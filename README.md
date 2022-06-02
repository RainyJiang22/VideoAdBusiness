# 组件化视频广告推送APP(初步开发)

## APP整体功能

- 网络请求组件开发
- 浏览大图功能开发
- APP首页开发
- 分享模块开发
- 异步图片加载模块开发
- 搜索模块开发
- zxing二维码模块开发
- 推送模块开发
- 视频播放SDK开发（可对外开放）
- 适配新权限模块，目前Android11有权限功能限制


## 第一部分，APP首页框架开发搭建(目前)

### 首页导航(HomeActivity)
1. 关于导航栏这里我使用了一个技巧可以在原生导航栏BottomNavigationBar中加入动画，
这里是将每个itemview做了分离，在切换的时候进行图标和文字效果的变化，主要有三个Fragment切换，首页Fragment，
MessageFragment，MineFragment
2. 开发步骤如下
- HomeActivity的创建
- 首页所需Fragment创建
- Fragment之间的切换（重要的地方）
3. 模块开发中的思考
- fragment切换有多少种方式，有区别吗
- 创建activity一定要在mainfest中申明么
- activity有几种启动模式，知道各自的使用场景么
- 对各种文件的命名有什么规范

## 第二部分 网络请求组件开发

- 需要实现的部分
1. get/post请求发送及响应
2. 支持https加密请求
3. 封装方便使用的API简化调用方式
4. 后期实现文件上传、下载功能

- okhttp核心
1. 发送get/post请求
2. 请求相关参数设置
3. request中的请求参数，url以及为我们创建好了请求对象
4. https支持
5. callback(这里我封装了一个CommonJsonCallback类)
 - 处理回调函数
 - 异常处理
 - 转发消息到我们的UI线程中
 - 将json转化为对应的实体


 ## 第三部分 异步图片加载模块开发

 - 需要实现的部分
 1. 实现异步图片加载
    - uiverseimagelloader
      1. 配置简单，使用方便
      2. 高度的可定制性
      3. 强大的图片缓存机制 
    - picasso/fresco/glide 
 2. 封装一个好用的图片加载组件
   - 开发一个ImageLoaderManager
     1. 默认参数配置以及提供必要的参数配置API
     2. 提供单例对象对外提供接口
     3. 对外提供加载图片API
    


## 第四部分 首页列表开发与测试


