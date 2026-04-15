# bsjob backend

## 启动方式
1. 使用 MySQL 执行 `src/main/resources/schema.sql` 初始化数据库。
2. 修改 `src/main/resources/application.yml` 中数据库账号密码。
3. 在 IDEA 中打开 `backend` 目录并运行 `BsjobApplication`。

## 主要接口
- `POST /api/auth/register` 注册
- `POST /api/auth/login` 登录
- `GET /api/jobs` 职位列表/关键词查询
- `POST /api/jobs` 企业发布职位（需 token）
- `POST /api/resumes` 求职者投递（需 token）
- `GET /api/resumes/mine` 求职者查看投递记录（需 token）


## 常见问题
- 如果出现 `java.lang.ExceptionInInitializerError` / `com.sun.tools.javac.code.TypeTag :: UNKNOWN`，通常是 JDK 与 Lombok 版本不兼容导致。本项目已固定 Lombok 版本并配置编译插件；请使用 `mvn clean package` 重新构建。
