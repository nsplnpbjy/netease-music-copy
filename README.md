这个服务器会自动储存播放过的歌曲，以节省网络，如果局域网足够强也有加快数据返回的作用

Dockerfile文件已经在项目中编写好，可以使用IDEA直接导入docker。
注意，本项目的部署需要允许docker访问外部数据库（宿主机数据库），所以容器必须运行在host网络上。
请在运行容器时加入命令：
--net=host

如果不用docker，可以下载release版本，在服务器上运行：
```
java -jar 包名.jar
```
即可

注意根据自身需求更改数据库配置和端口号
如果不使用数据库，可将配置文件中dataoption.isEnable=true改为false
