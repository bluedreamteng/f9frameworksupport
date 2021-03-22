# f9frameworksupport

背景

在开发的过程中总有一些工作是重复性的，做这些工作一次两次没问题，时间长了便会产生厌烦的情绪。如果这些重复性工作能让程序帮我们解决，那不但能提高开发效率，还能使我们更专注于更重要的工作。
在用idea开发时，一些重复性的工作，idea自带的功能便能帮我们实现，如自动生成getter和setter，以及自定义模板的功能。这些功能可以帮助我们去生成一些有规律的代码，来简化我们的开发工作。但是还是有一些重复性工作是idea自带功能所不能满足的，这时我们便可以自己开发插件来拓展idea，使之帮助我们完成这些重复性的工作。
目前针对日常工作的重复劳动，开发了F9FrameWorkSupport这个插件。地址为
https://github.com/tenglovemeng/f9frameworksupport



功能

目前插件还在完善中，现在支持如下功能

1.根据链接导航到相应action

功能描述：根据方法参数所提供的链接，通过ctrl+click能直接定位到相应的action
解决痛点：省去了每次都要手动搜索controller的繁琐步骤
ps:增加对html标签的action支持，找到对应的方法
//直接定位到类
epoint.initPage('generatetestdataaction');

//直接定位到方法
function generateEnvironmentData() {
    epoint.execute('generateEnvironmentData', "@none", callBack)
}





2.提供对相关引用的代码自动补全功能




3.列举出实体类的所有的set方法

解决痛点：工作中遇到的实体类字段大都比较多，当给一个实体设置属性的时候，需要一个个查找暴露的setter方法。这样的话就可能会漏掉许多字段，而且也会比较繁琐。




4.Job类自动添加相关配置

此功能等待后续开发。。。
