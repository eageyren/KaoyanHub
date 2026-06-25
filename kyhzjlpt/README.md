# 考研资料与交流平台

## 项目简介

本项目是一个面向考研学生的资料分享与交流平台，集成了资料管理、院校信息、在线问答、论坛交流、考试系统等多种功能。系统采用前后端分离架构，包含后台管理端和前台用户端，适合高校、教育机构或个人搭建考研学习社区。

---

## 目录结构

```
.
├── client_home/      # 前台用户端（Vue2）
├── client_admin/     # 后台管理端（Vue2）
├── server/           # 后端服务（Spring Boot + MyBatis-Plus + JPA）
├── sql.sql           # 数据库初始化脚本
└── README.md         # 项目说明文档
```

---

## 技术栈

- **前端（用户端/管理端）**：Vue2、Element-UI、Bootstrap-Vue、Vuex、Vue-Router、Axios、Quill 编辑器等
- **后端**：Java 8、Spring Boot 2.2.5、MyBatis-Plus 3.3.1、Spring Data JPA、Lombok、Fastjson、POI
- **数据库**：MySQL 8+
- **构建工具**：Maven

---

## 功能模块

### 1. 用户系统
- 多角色支持：管理员、系统用户、游客
- 用户注册、登录、分组、权限管理

### 2. 资料管理
- 考研资料上传、分类、点赞、浏览、评论
- 资料类型自定义
- 资料分享功能

### 3. 院校信息
- 报考院校信息展示（专业、分数线、详情等）
- 点赞、浏览、评论

### 4. 论坛交流
- 发帖、回帖、点赞、评论
- 论坛分类管理
- 支持多媒体内容

### 5. 在线问答
- 在线提问、答疑，支持附件
- 问题审核、答疑内容管理

### 6. 考试系统
- 在线考试、题库管理
- 多题型支持：单选、多选、判断、填空、主观题
- 自动评分与主观题人工评分
- 成绩与答题详情

### 7. 公告系统
- 平台公告、关于我们、联系方式等信息展示

---

## 数据库设计

请参考 `sql.sql` 文件，包含所有表结构及部分初始化数据。主要表有：

- 用户相关：`user`、`user_group`、`auth`
- 资料相关：`postgraduate_examination_materials`、`data_sharing`、`data_type`
- 院校相关：`colleges_and_universities`
- 论坛相关：`forum`、`forum_type`、`comment`、`collect`、`praise`、`hits`
- 考试相关：`exam`、`exam_question`、`user_answer`
- 其他：`notice`、`online_questions`、`online_qa`、`slides`、`upload`、`access_token`、`system_user`

---

## 快速开始

### 1. 数据库初始化

1. 安装 MySQL 数据库
2. 新建数据库（如 `kyhzjlpt`）
3. 执行 `sql.sql` 文件，初始化表结构和测试数据

```bash
mysql -u root -p kyhzjlpt < sql.sql
```

### 2. 后端部署

1. 进入 `server` 目录
2. 配置数据库连接（`application.properties` 或 `application.yml`，通常在 `src/main/resources` 下）
3. 安装依赖并打包

```bash
mvn clean package
```

4. 启动服务

```bash
java -jar target/project-spring_boot-1.0-SNAPSHOT.jar
```
或使用 `运行.bat` 脚本

### 3. 前端部署

#### 用户端（client_home）

```bash
cd client_home
npm install
npm run serve
```

#### 管理端（client_admin）

```bash
cd client_admin
npm install
npm run serve
```

### 4. 访问方式

- 前台用户端：http://localhost:8080
- 后台管理端：http://localhost:8081
- 后端 API：http://localhost:8088

> 端口号可根据实际配置调整

---

## 账号说明

- 管理员账号：`admin` / 密码：`admin`
- 其他测试账号请参考 `user` 表初始数据

---

## 常见问题

1. **数据库连接失败？**
   - 检查数据库配置、端口、用户名密码是否正确
2. **前后端无法通信？**
   - 检查 API 地址、端口、跨域配置
3. **文件上传失败？**
   - 检查后端上传目录权限、Nginx/Apache 配置

