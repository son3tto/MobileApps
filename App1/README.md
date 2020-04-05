# 创建一个Android工程并同步GitHub



## 前期准备

1.[Android Studio 3.0.1](https://dl.google.com/dl/android/studio/install/3.0.1.0/android-studio-ide-171.4443003-windows.exe )

2.[Git](https://git-scm.com/downloads )

## 打开Android Studio

- **选择create a new project，之后一路点击确定**

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/9.jpg)

- **进入界面之后---找到menu bar----file-----setting------Version Control-----Git**
  - 点击test确认前面下好的Git执行路径，若出现successful提示，则成功

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/10.jpg)

- **找到setting------Version Control-----GitHub**

  - 登录自己的GitHub账号，点击test，若出现successful提示，则成功

    *注:出现connection的问题，多连接几次就可以成功*

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/11.jpg)

************************************************************** 至此Android Studio的工作结束 *****************************************************************

## 工程同步GitHub

- ##### 创建新的ssh key

> ssh-keygen -t rsa -b 4096 -C “[1013278470@qq.com](mailto:1013278470@qq.com)”

[![1568775033256](https://github.com/sonettofighting/Cloud_Computing/raw/master/imgs/1568775033256.png)](https://github.com/sonettofighting/Cloud_Computing/blob/master/imgs/1568775033256.png)

- ##### 复制密钥的内容到GitHub的SSH key中

  ![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/4.jpg)

- ##### 测试是否成功

  ![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/5.jpg)

- ##### 新建代码仓库

  ![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/12.jpg)

- ##### 创建本地代码仓库

- ##### [![1568775932395](https://github.com/sonettofighting/Cloud_Computing/raw/master/imgs/1568775932395.png)](https://github.com/sonettofighting/Cloud_Computing/blob/master/imgs/1568775932395.png)

- ##### 将本地仓库和GitHub的仓库关联

  ![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/6.jpg)

- ##### 新建仓库创建README文件

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/1.jpg)

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/0.jpg)

- ##### 将文件夹里的所有文件添加，并提交(commit)到本地仓库

  ![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/3.jpg)

- ##### 将本地仓库的内容推送到GitHub

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/7.jpg)



*遇到的一点问题

从网上找了一份 .gitignore 文件想直接使用

内容如下

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/2.jpg)

结果推送上去，把所有的文件都ignore了哈哈哈哈哈哈哈

然后把.gitignore文件修改恢复为默认的文件时，需要删除缓存文件。

![](https://github.com/sonettofighting/MobileApps/blob/master/App1/imgs/8.jpg)