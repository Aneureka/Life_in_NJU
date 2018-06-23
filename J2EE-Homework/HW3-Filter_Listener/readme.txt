### （也不知道有没有人看的一点点）说明

1. listener 的基本实现过程：
    当游客进入login页面时，创建一个session（如果当前没有的话）；
    当游客登录时，在当前session添加attribute；
    当游客登出时，remove掉当前session所有的attribute；
    当session（因timeout）被移除时，remove掉当前session。

    所以通过监听session及其attribute，就能统计在线的游客和已登录用户动态的数目啦。

2. 针对"用户登录后直接关闭浏览器"的处理，采用session的timeout机制，在web.xml中将session的timeout设置为2，即两分钟。这样的话会存在一定的时间误差，但也算初步解决了这个问题。

