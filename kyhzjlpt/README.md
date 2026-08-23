# 考研互助平台

一个面向考研学生的资料分享、院校信息查询、在线问答和学习交流平台。项目采用前后端分离架构，包含用户端、管理端和 Spring Boot 后端，适合作为软件测试实训、课程设计或学习型社区项目使用。

> 项目当前未声明开源许可证。若要在 GitHub 上公开发布或允许他人再分发，请先补充合适的 LICENSE 文件。

## 目录

- [项目功能](#项目功能)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [运行环境](#运行环境)
- [快速开始](#快速开始)
- [软件测试](#软件测试)
- [常见问题](#常见问题)
- [安全与提交建议](#安全与提交建议)
- [贡献](#贡献)

## 项目功能

### 用户与权限

- 用户注册、登录和个人信息管理
- 管理员、普通用户等角色及用户分组
- 权限、登录拦截和基础系统管理

### 学习资源与院校信息

- 考研资料上传、分类、浏览、点赞、收藏和评论
- 资料类型及分享内容管理
- 院校、专业、分数线等报考信息展示

### 交流与问答

- 论坛分类、发帖、回帖、评论、点赞和浏览
- 在线提问与答疑，支持附件和内容审核
- 公告、轮播图及平台信息展示

### 在线考试

- 题库与考试管理
- 单选、多选、判断、填空和主观题
- 客观题自动评分、主观题人工评分、成绩和答题详情

## 技术栈

| 部分 | 主要技术 |
| --- | --- |
| 用户端 / 管理端 | Vue 2、Vue Router、Vuex、Axios、Element UI、Bootstrap-Vue、Quill |
| 后端 | Java 8、Spring Boot 2.2.5、MyBatis-Plus 3.3.1、Spring Data JPA、Lombok |
| 数据库 | MySQL 8+；测试使用 H2 内存数据库 |
| 构建工具 | Maven、npm、Vue CLI 4 |
| 测试与质量 | JUnit 5、Spring Boot Test、MockMvc、Mockito、JaCoCo、PIT |

## 项目结构

```text
.
├── client_home/       # 前台用户端（Vue 2，默认端口 8081）
├── client_admin/      # 后台管理端（Vue 2，默认端口 8080）
├── server/            # 后端服务（Spring Boot）
├── kyhzjlpt.sql       # 数据库表结构及初始化数据
└── README.md          # 项目说明
```

## 运行环境

- JDK 8
- Maven 3.6+
- Node.js 14.x 左右及 npm（项目依赖较旧，建议使用与 Vue CLI 4 兼容的版本）
- MySQL 8+
- Git

## 快速开始

以下命令均在 `kyhzjlpt` 目录下执行。

### 1. 初始化数据库

创建数据库后导入项目脚本：

```sql
CREATE DATABASE kyhzjlpt DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

```bash
mysql -u root -p kyhzjlpt < kyhzjlpt.sql
```

### 2. 配置并启动后端

后端配置文件为 `server/src/main/resources/application.yml`，默认配置如下：

- 服务端口：`5000`
- API 上下文路径：`/api`
- 数据库：`127.0.0.1:3306/kyhzjlpt`

请根据本机环境修改数据库用户名和密码。公共仓库中不要提交真实密码、密钥或生产环境配置。

```bash
cd server
mvn clean package
java -jar target/project-spring_boot-1.0-SNAPSHOT.jar
```

也可以在 IDE 中运行项目的 Spring Boot 启动类。后端接口基地址为：

```text
http://localhost:5000/api
```

### 3. 启动用户端

```bash
cd client_home
npm install
npm run serve
```

用户端默认访问地址：<http://localhost:8081>

生产构建：

```bash
npm run build
```

### 4. 启动管理端

```bash
cd client_admin
npm install
npm run dev
```

管理端默认访问地址：<http://localhost:8080>

生产构建：

```bash
npm run build
```

首次启动时请确认前端请求地址、后端端口和跨域配置一致；端口可通过各自的 `vue.config.js` 或后端配置调整。

## 软件测试

本项目将软件测试作为后端开发的重要组成部分，覆盖业务逻辑、接口行为、输入校验、并发场景和基础安全性验证。测试代码位于 `server/src/test/java`，测试配置位于 `server/src/test/resources/application-test.yml`。

### 测试分层

| 测试类型 | 位置 / 示例 | 目标 |
| --- | --- | --- |
| 服务层测试 | `service/*ServiceGroupTest.java`、`service/base` | 验证用户、资料、论坛、问答、考试和系统服务的业务规则 |
| 控制器单元测试 | `controller/mockito` | 使用 Mockito 隔离依赖，验证控制器参数处理和响应逻辑 |
| MockMvc / 接口集成测试 | `controller/mockmvc`、`AbstractIntegrationTest.java` | 验证 Spring MVC 路由、请求参数、响应状态和 JSON 结果 |
| 输入校验测试 | `InputValidationTest.java`、`UserValidationDataDrivenTest.java` | 验证空值、边界值、非法格式等输入场景 |
| 实体与拦截器测试 | `entity`、`interceptor` | 验证实体基础行为和登录拦截逻辑 |
| 并发与性能基线 | `concurrency`、`performance` | 观察并发请求和关键操作的基础性能表现 |
| 安全测试 | `security/SqlInjectionSecurityTest.java` | 验证典型 SQL 注入输入不会绕过业务查询约束 |

### 测试环境

带有 `@ActiveProfiles("test")` 的集成测试会加载测试配置，并使用 H2 内存数据库，数据库生命周期由测试自动创建和清理。因此，执行后端测试通常不需要连接本地 MySQL，也不会修改开发数据库。

### 执行测试

```bash
cd server
mvn clean test
```

测试报告通常位于：

```text
target/surefire-reports/
```

项目已配置 JaCoCo，在测试阶段生成 HTML 覆盖率报告：

```text
target/site/jacoco/index.html
```

Windows 环境下，`pom.xml` 将 JaCoCo 数据文件配置为 `d:/maven/jacoco.exec`，用于规避中文路径在 forked JVM 中可能产生的编码问题；如果本机没有 `D:` 盘或该目录不可写，请按本机环境调整 `jacoco.destFile` 后再执行测试。

### 变异测试

项目配置了 PIT，可用于从测试用例对代码变更的检出能力角度进行补充评估：

```bash
cd server
mvn org.pitest:pitest-maven:mutationCoverage
```

PIT HTML 报告默认输出到：

```text
target/pit-reports/
```

README 不预先声明通过率、覆盖率、性能或安全结论；这些结果应以本地实际执行生成的报告为准。

## 常见问题

1. **数据库连接失败**：检查 MySQL 是否启动、数据库是否创建，以及 `application.yml` 中的地址、端口、用户名和密码。
2. **前端无法访问后端**：确认后端已启动，前端 API 地址指向 `http://localhost:5000/api`，并检查跨域配置。
3. **依赖安装失败**：删除对应前端目录下的 `node_modules` 后重新执行 `npm install`；同时确认 Node.js 版本与 Vue CLI 4 兼容。
4. **文件上传失败**：检查上传目录是否存在且具有写权限，并确认请求大小不超过后端的 100 MB 限制。
5. **测试启动失败**：优先确认使用 JDK 8，并检查 `d:/maven` 是否可写；集成测试应使用 `test` profile 的 H2 配置。

## 安全与提交建议

- 不要将真实数据库密码、Token、私钥、`.env` 文件或本地上传文件提交到 GitHub。
- 根目录 `.gitignore` 已忽略 Maven/Node 构建产物、依赖目录、IDE 配置、测试报告和本地运行文件。
- 数据库脚本中的初始化账号仅用于开发和测试，部署到真实环境后请立即修改默认凭据。
- 发布前请检查提交历史，确认敏感信息没有曾经被提交；已经泄露的凭据应立即轮换，而不只是删除当前文件。

## 贡献

欢迎通过 Issue 反馈问题或提交 Pull Request。提交代码前建议至少执行后端测试，并对前端修改分别运行对应的构建命令。
