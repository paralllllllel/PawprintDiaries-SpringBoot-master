# PawprintDiaries (catcat) 项目部署指南

本文档将指导您完成部署 `PawprintDiaries` Spring Boot 项目所需的所有准备工作。请按照以下步骤逐一完成。

## 1. 环境与工具准备

在开始之前，请确保您的开发环境中已安装以下软件：

- **Java Development Kit (JDK)**:
    - **要求**: **Java 17**。项目的 `pom.xml` 文件指定了此版本。
    - **验证**: 在终端运行 `java -version`，确保版本正确。

- **Apache Maven**:
    - **要求**: 用于项目构建和依赖管理。
    - **验证**: 在终端运行 `mvn -v`。

## 2. 核心外部服务部署

本项目依赖多个外部服务。您需要在本地或服务器上安装并运行它们。

### 2.1 MySQL 数据库

1.  **安装并启动 MySQL 服务**。
2.  **创建数据库**: 连接到您的 MySQL 服务，并执行以下 SQL 命令来创建一个名为 `catcat` 的数据库。推荐使用 `utf8mb4` 字符集以支持更广泛的字符。
    ```sql
    CREATE DATABASE catcat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```
3.  **初始化表结构**:
    - 选择刚刚创建的数据库（例如，在 MySQL 命令行中执行 `USE catcat;`）。
    - 运行项目根目录下的 `schema.sql` 文件中的所有 `CREATE TABLE` 语句，以初始化数据库表结构。

### 2.2 Redis

1.  **安装并启动 Redis 服务**。
2.  项目默认会尝试连接到 `localhost:6379`。如果您的 Redis 部署在其他地址或端口，请在后续步骤中修改配置文件。

### 2.3 RabbitMQ

1.  **安装并启动 RabbitMQ 服务**。
2.  项目默认会使用 `guest`/`guest` 用户连接到 `localhost:5672`。如果您的配置不同，请在后续步骤中修改配置文件。

打开方式
管理员权限打开终端；
cd D:\codes\Environment\rabbitMQ\rabbitmq-server-windows-4.1.1\rabbitmq_server-4.1.1\sbin

.\rabbitmq-service.bat start

其他
安装
.\rabbitmq-service.bat install
启用管理插件
.\rabbitmq-plugins.bat enable rabbitmq_management
停止
.\rabbitmq-service.bat stop
### 2.4 Elasticsearch

1.  **安装并启动 Elasticsearch 服务**。
2.  项目配置文件中指定的 URI 为 `https://localhost:9200`，这表示它期望通过 HTTPS 连接。请确保您的 Elasticsearch 实例支持 HTTPS，或者相应地修改配置文件中的 URI。

## 3. 云服务与本地配置

### 3.1 七牛云对象存储

项目使用七牛云存储用户上传的图片文件。

1.  **注册七牛云**: 如果您还没有账户，请前往七牛云官网注册。
2.  **创建存储空间 (Bucket)**: 创建一个公开的存储空间。
3.  **获取密钥**: 在您的账户中找到 **Access Key (AK)** 和 **Secret Key (SK)**。
4.  **配置信息**: 将您的 AK、SK、存储空间名称和访问域名记录下来，后续需要填入配置文件。

### 3.2 本地文件上传目录

项目有一个配置项指向本地文件系统。

1.  **创建目录**: 在您的电脑上创建一个目录，用于临时存放上传的文件。例如 `D:/catcat_uploads`。
2.  **记录路径**: 记下这个路径，后续需要填入配置文件。

## 4. 修改项目配置文件

这是最关键的一步。打开项目中的 `src/main/resources/application-dev.yml` 文件，根据您自己的环境，修改以下配置：

- **数据库连接 (`spring.datasource`)**:
  ```yaml
  url: jdbc:mysql://localhost:3306/catcat?useSSL=false&...
  username: YOUR_MYSQL_USERNAME
  password: YOUR_MYSQL_PASSWORD
  ```

- **RabbitMQ (`spring.rabbitmq`)**:
  ```yaml
  host: YOUR_RABBITMQ_HOST # e.g., localhost
  port: 5672
  username: YOUR_RABBITMQ_USERNAME
  password: YOUR_RABBITMQ_PASSWORD
  ```

- **Elasticsearch (`spring.elasticsearch`)**:
  ```yaml
  uris: https://YOUR_ELASTICSEARCH_HOST:9200
  username: YOUR_ELASTICSEARCH_USERNAME
  password: YOUR_ELASTICSEARCH_PASSWORD
  ```

- **本地文件上传目录 (`file`)**:
  ```yaml
  upload-dir: D:/catcat_uploads # 修改为您在 3.2 步骤中创建的路径
  ```

- **七牛云 (`qiniu`)**:
  ```yaml
  access-key: YOUR_QINIU_ACCESS_KEY
  secret-key: YOUR_QINIU_SECRET_KEY
  bucket: YOUR_QINIU_BUCKET_NAME
  upload-url: https://YOUR_QINIU_CDN_DOMAIN/ # 您的七牛云存储桶访问域名
  ```

## 5. 运行项目

完成以上所有步骤后，您就可以在项目根目录下通过 Maven 运行项目了：

```bash
mvn spring-boot:run
```

如果一切顺利，您将在控制台看到 Spring Boot 启动成功的日志。 