# 使用教程
### 项目介绍
使用Lucene实现的一个文件检索系统, Demo中使用到了JSP、Servlet等技术。可以对html、pdf、doc等文件内容实现检索。

### 在IDEA配置Tomcat
&emsp;&emsp;&emsp;&emsp;1、打开"Run/Debug Configurations" --> 点击右上角"+"号 --> 选择"Tomcat Server" --> "local"

### 导出war包
&emsp;&emsp;&emsp;&emsp;1、右击工程名 --> 点击"Open Module Settings" --> 点击"Artifacts"(File->Project Structure->Artifacts) <br/>
&emsp;&emsp;&emsp;&emsp;2、点击上方"+"号 --> 点击"Web Application:Archive" --> 点击右下角"OK", 并记住输出目录!!! <br/>
&emsp;&emsp;&emsp;&emsp;3、点击IDEA菜单栏的"Build" --> 点击"Build Artifacts" --> 选择刚刚创建的"Web Application:Archive" --> 点击"Build" <br/>
&emsp;&emsp;&emsp;&emsp;4、在输出目录中找到war文件 <br/>
&emsp;&emsp;&emsp;&emsp;5、将war文件放入%Tomcat%/webapps目录下,然后运行tomcat,即可直接访问对应的网站  <br/>

### 网站使用指南
&emsp;&emsp;&emsp;&emsp;1、在"文档路径"后面的输入框中输入需要建立索引的目录,然后点击"建立索引",就会在"索引目录"所指向的路径生成索引 <br/>
&emsp;&emsp;&emsp;&emsp;2、输入需要查询的关键字,然后点击查询即可 <br/>

### 参考链接
&emsp;&emsp;&emsp;&emsp;1、使用IntelliJ IDEA开发java web : `http://www.cnblogs.com/carsonzhu/p/5468223.html` <br/>

