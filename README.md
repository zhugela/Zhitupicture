# 智图后端（Zhitupicture Backend）

一个基于 Spring Boot + MyBatis-Plus 的图片管理后端项目，提供用户管理、图片上传（本地文件与 URL）、图片审核、文件上传下载、接口文档等能力。

## 1. 项目简介

本项目是 `backend` 服务，默认以 REST API 形式对外提供图片管理相关能力。核心特性包括：

- 用户注册、登录、注销与用户管理。
- 图片上传（文件上传 / URL 上传）。
- 图片信息维护、查询、删除与审核流程。
- 基于腾讯云 COS 的文件上传下载能力。
- Knife4j 接口文档支持。

## 2. 技术栈

- Java 8
- Spring Boot 2.6.13
- MyBatis-Plus 3.5.9
- MySQL
- Knife4j 4.4.0
- Tencent COS SDK 5.6.227
- Hutool 5.8.26

## 3. 目录结构

```text
.
├── pom.xml
├── sql/
│   └── create_table.sql
└── src/
    ├── main/
    │   ├── java/com/itheima/backend/
    │   │   ├── controller/      # 接口层（用户、图片、文件、健康检查）
    │   │   ├── service/         # 业务层
    │   │   ├── mapper/          # 数据访问层
    │   │   ├── manager/         # 外部服务封装（如 COS）
    │   │   ├── config/          # 配置类
    │   │   └── exception/       # 全局异常与错误码
    │   └── resources/
    │       ├── application.yml
    │       ├── mapper/
    │       └── static/
    └── test/
```

## 4. 环境准备

请先确保本地具备以下环境：

1. JDK 1.8+
2. Maven 3.6+
3. MySQL 5.7 / 8.0
4. 可用的腾讯云 COS 账号与存储桶配置

## 5. 配置说明

### 5.1 应用基础配置

默认配置位于 `src/main/resources/application.yml`：

- 服务端口：`8124`
- 上下文路径：`/api`
- 数据库地址：`jdbc:mysql://localhost:3306/picture`

### 5.2 数据库配置

请根据本地环境修改以下配置项：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/picture
    username: root
    password: your_password
```

### 5.3 COS 配置

本项目通过 `cos.client` 前缀读取腾讯云 COS 配置，请在 `application.yml` 中补充：

```yaml
cos:
  client:
    host: your-cos-host
    secret-id: your-secret-id
    secret-key: your-secret-key
    region: your-region
    bucket: your-bucket-name
```

> 注意：`secret-key` 等敏感信息不要提交到公开仓库，建议通过环境变量或外部配置注入。

## 6. 初始化数据库

执行 SQL 脚本创建数据库与核心表：

```bash
mysql -u root -p < sql/create_table.sql
```

脚本会创建：

- `picture` 数据库
- `user` 表
- `picture` 表（包含审核状态字段和相关索引）

## 7. 启动项目

在项目根目录执行：

```bash
mvn spring-boot:run
```

启动成功后访问：

- 健康检查：`GET http://localhost:8124/api/health`
- 首页静态页：`GET http://localhost:8124/api/`（如有映射）

## 8. 接口文档

项目已启用 Knife4j，启动后可尝试访问：

- `http://localhost:8124/api/doc.html`

若文档页无法访问，请先确认：

1. 服务已正常启动。
2. `knife4j.enable=true`。
3. 上下文路径（`/api`）是否已正确拼接。

## 9. 主要接口模块

- `/api/user/**`：用户注册、登录、查询、管理。
- `/api/picture/**`：图片上传、查询、编辑、删除、审核、标签分类。
- `/api/file/**`：文件测试上传、下载。
- `/api/health`：健康检查。

## 10. 开发与排错建议

- 启动前优先检查数据库与 COS 配置是否完整。
- 图片上传与下载依赖 COS，若出现 500，先排查密钥、地域、桶名、权限。
- 涉及管理员接口时，注意登录态与角色校验（`admin`）。

## 11. 常用命令

```bash
# 编译
mvn clean compile

# 运行测试
mvn test

# 启动服务
mvn spring-boot:run
```

---

如需对接前端，建议先通过 Knife4j 验证接口可用性，再进行联调。
