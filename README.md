# QQMusicTool

用JavaFx写的QQ音乐工具,可在线听和缓存收藏歌曲,支持收藏歌曲到本地,支持linux

## ver1.0

功能：

> 1.可在线听取和下载音乐
>
> 2.支持收藏歌曲和缓存
>
> 3.列表顺序播放音乐



## 运行截图

### 搜索

![搜索](https://raw.githubusercontent.com/wanghaoxin20/QQMusicTool/master/img/searchResult.png)



### 我的收藏

![我的收藏](https://raw.githubusercontent.com/wanghaoxin20/QQMusicTool/master/img/collect.png)



### 设置

![设置](https://raw.githubusercontent.com/wanghaoxin20/QQMusicTool/master/img/settings.png)



## 使用

>1.鼠标左键双击播放音，右键单击添加/删除收藏，右键双击下载歌曲
>
>2.暂时只支持顺序播放



## Build

>+ 执行mvn clean compile assembly:singgle即可生成可运行的${jarname}.jar
>
>+ javaw -jar  -Dfile.encoding=UTF-8 -Xms100m -Xmx100m ${jarname}.jar



## 声明

此软件供学习和交流用，请支持正版QQ音乐！！！！