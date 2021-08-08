这个服务器会自动储存播放过的歌曲，以节省网络，如果局域网足够强也有加快数据返回的作用。
现在服务器有返回MV地址和服务器本地存一遍MV再返回的两种功能，默认使用返回MV地址的功能。

Dockerfile文件已经在项目中编写好，可以使用IDEA直接导入docker。
运行docker容器时需注意，通过-e命令加入环境变量DB_HOST，作为数据库地址，端口固定为3306。

如下：
````
docker run -p 8075:8075 -e DB_HOST=192.168.x.x containername
````

如果不用docker，可以下载release版本，在服务器上运行：
```
java -jar 包名.jar
```
即可

注意根据自身需求更改数据库配置和端口号
如果不使用数据库，可将配置文件中dataoption.isEnable=true改为false
