### 几点说明

1. 数据库连接池采用基于DataSource的HikariCP，相关配置文件在datasource.properties中
2. 创建数据库的命令在create.sql中
3. 分页底层实现采用了Mysql的LIMIT机制
4. 插入随机数据的详细做法在test中
5. 运行截图在screenshots中
6. 项目存放地址的路径请不要包含空格，若包含空格，可以在ConnectionPool类中采用被注释的配置方式