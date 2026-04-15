# bsjob 招聘系统

项目结构：

```
bsjob/
├── backend/   # Spring Boot + MyBatis Plus + MySQL
└── frontend/  # 原生 HTML/CSS/JavaScript
```

## 一、后端启动（IDEA）
1. 用 IDEA 打开 `backend` 目录（Maven 项目）。
2. 等待 Maven 依赖下载完成。
3. 修改 `backend/src/main/resources/application.yml` 的数据库用户名和密码。
4. 运行 `com.bsjob.BsjobApplication`。
5. 默认端口 `8080`。

## 二、数据库初始化
1. 启动 MySQL。
2. 执行 SQL 脚本：`backend/src/main/resources/schema.sql`。
3. 脚本会自动创建 `bsjob` 数据库与三张表：`user`、`job`、`resume`。

## 三、前端运行
1. 直接用浏览器打开 `frontend/login.html` 或 `frontend/index.html`。
2. 页面使用 `fetch` 调用 `http://localhost:8080` 的后端 REST 接口。

## 四、核心功能
- 用户注册（user/company）与登录（JWT token）
- 企业发布职位
- 职位列表查询与关键词搜索
- 求职者投递简历
- 求职者查看已投递记录
