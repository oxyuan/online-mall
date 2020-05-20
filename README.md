# 农副产品网上商城

- [x] 1.【商品服务】后台商品管理模块
  - [x] 查询商品
  - [x] 新增商品
    - 难点：  
      - 列表类目
        1. 数据库表设计  
      - 上传图片
        1. 图片服务器搭建
  - [x] 规格参数
    - 难点：
      - 数据库表设计
      - 前端逻辑
- [x] 2.【内容服务】前台商品展示模块
  - [x] CMS 系统的实现
    - 内容分类管理
    - 内容管理
    - 难点：
      - Redis 环境配置搭建（暂时集群环境连接有问题，单机环境没问题）
- [x] 3.【搜索服务】
  - [x] 单机版Solr 环境配置搭建
  - [x] 索引库导入、查询
- [x] 4.【登录服务】
  - [x] 跨域问题
- [x] 5.【购物车服务】
- [x] 6.【订单服务】

## 一、环境搭建

项目采用docker容器进行部署，docker命令如下。

- 1.1 **MySQL**
  - 拉取镜像
    - ``` docker pull mysql:5.7.23 ```
  - 启动容器
    - ``` docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7.23 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci ```

- 1.2 **Zookeeper**
  - 拉取镜像
    - ``` docker pull zookeeper:3.4.13 ```
  - 启动容器
    - ``` docker run --name zookeeper -p 2181:2181 --restart always -d zookeeper:3.4.13 ```
- 1.3 **FastDFS**
  - 拉取镜像
    - ``` docker pull morunchang/fastdfs ```
  - 运行 *tracker* 实例
    - ``` docker run -d --name mall-fastdfs-tracker --net=host morunchang/fastdfs sh tracker.sh ```
  - 运行 *storage* 实例
    - ``` docker run -d --name mall-fastdfs-storage --net=host -e TRACKER_IP=localhost:22122 -e GROUP_NAME=group1 morunchang/fastdfs sh storage.sh ```
  - 修改 *nginx* 配置，不拦截上传内容
    1. 进入容器
        - ```docker exec -it mall-fastdfs-storage /bin/bash```
    2. 编辑nginx配置文件
        - ``` vi /data/nginx/conf/nginx.conf ```
    3. 修改以下内容
        - ``` bash
          location /group1/M00 {
                proxy_next_upstream http_502 http_504 error timeout invalid_header;
                proxy_cache http-cache;
                proxy_cache_valid 200 304 12h;
                proxy_cache_key $uri$is_args$args;
                proxy_pass http://fdfs_group1;
                expires 30d;
          }
          ```
    4. 退出
        - ``` exit ```
  - 重启 *storage* 容器
    - ``` docker restart mall-fastdfs-storage ```

- 1.4 **ActiveMQ**
  - 拉取镜像
    - ``` docker pull webcenter/activemq:5.14.3 ```
    - 启动容器
      - ``` docker run -d --name mall-activemq -p 61616:61616 -p 8161:8161 webcenter/activemq:5.14.3 ```

- 1.5 **Redis**
  - 拉取镜像
    - ``` docker pull redis:3.2 ```
  - 启动容器（单机环境）
    - ```docker run -d -p 6379:6379 --name mall-redis redis:3.2```

- 1.6 **Solr**
  - 拉取镜像
    - ``` docker pull solr:7.4.0 ```
  - 启动容器（可访问 *<http://ip:8983>*）
    - ``` docker run --name mall-solr -d -p 8983:8983 -t solr:7.4.0 ```
  - 新建 *SolrCore*，名称为 collection1
    - ```docker exec -it mall-solr bin/solr create_core -c collection1```
  - 容器拷贝宿主机 【方便后续修改配置，将容器 solr 文件拷贝本地 /usr/local/solr/】
    - ```docker cp mall-solr:/opt/solr/ /usr/local/```
  - 设置中文分词IK分词器
    1. 进入 */opt/solr/server/solr-webapp/webapp/WEB-INF/lib* 添加 jar 包
        - [![J5gmQ0.png](https://s1.ax1x.com/2020/04/28/J5gmQ0.png)](https://imgchr.com/i/J5gmQ0)
    2. *solr-dataimporthandler-7.4.0.jar* 和 *solr-dataimporthandler-extras-7.4.0.jar* 在 */usr/local/mysolr/solr/dist* 文件夹下
        - ``` docker cp /usr/local/solr/server/solr-webapp/webapp/WEB-INF/lib/solr-dataimporthandler-extras-7.4.0.jar mall-solr:/opt/solr/server/solr-webapp/webapp/WEB-INF/lib/solr-dataimporthandler-extras-7.4.0.jar ```
        - ``` docker cp /usr/local/solr/server/solr-webapp/webapp/WEB-INF/lib/solr-dataimporthandler-7.4.0.jar mall-solr:/opt/solr/server/solr-webapp/webapp/WEB-INF/lib/solr-dataimporthandler-7.4.0.jar ```
        - ``` docker cp  /usr/local/solr/server/solr-webapp/webapp/WEB-INF/lib/mysql-connector-java-5.1.46.jar  mall-solr:/opt/solr/server/solr-webapp/webapp/WEB-INF/lib/mysql-connector-java-5.1.46.jar ```
        - ``` docker cp /usr/local/solr/server/solr-webapp/webapp/WEB-INF/lib/ik-analyzer-7.4.0.jar mall-solr:/opt/solr/server/solr-webapp/webapp/WEB-INF/lib/ik-analyzer-7.4.0.jar ```
    3. 配置 *managed-schema*
        - ① 暂停容器
          - ``` docker stop mall-solr ```
        - ② 编辑managed-schema文件
          - ``` vim /usr/local/solr/server/solr/collection1/conf/managed-schema ```
            [![J589bT.png](https://s1.ax1x.com/2020/04/28/J589bT.png)](https://imgchr.com/i/J589bT)
        - ③ 从主机将文件复制至容器，覆盖配置
          - ``` docker cp /usr/local/solr/server/solr/collection1/conf/managed-schema mall-solr:/opt/solr/server/solr/collection1/conf/managed-schema ```
        - ④ 重启容器
          - ``` docker restart mall-solr ```

## 二、部署

项目采用分布式架构，各个模块有依赖关系，需要依次部署。

- 2.1 后台管理
  - manager-service
  - content-service
  - manager-web
  
- 2.2 首页展示
  - Portal

- 2.3 单点登录服务
  - SSO-service
  - SSO-web

- 2.4 搜索服务
  - search-service
  - search-web

- 2.5 商品详情展示
  - item-web
  
- 2.6 购物车模块
  - cart-service
  - cart-web
  
- 2.7 订单服务
  - order-service
  - order-web
  
