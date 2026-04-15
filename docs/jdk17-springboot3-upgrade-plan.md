# bsjob 升级方案：JDK 17 + Spring Boot 3.x

> 目标：在不一次性推翻重来的前提下，把当前 `Spring Boot 2.7 + Java 8` 项目平滑升级到 `Spring Boot 3.x + JDK 17`。

---

## 1. 当前基线与目标基线

### 当前基线
- Java: 1.8
- Spring Boot: 2.7.18
- MyBatis-Plus: `mybatis-plus-boot-starter`
- javax 包：`javax.servlet.*`、`javax.validation.*`

### 目标基线
- Java: 17
- Spring Boot: 3.x（建议先上 3.3.x/3.4.x 的稳定小版本）
- MyBatis-Plus: `mybatis-plus-spring-boot3-starter`
- jakarta 包：`jakarta.servlet.*`、`jakarta.validation.*`

---

## 2. 升级分阶段执行（推荐）

## 阶段 A：先切 JDK 17（仍保留 Boot 2.7）
1. 本地、CI、服务器统一安装 JDK 17。
2. Maven 编译目标改为 17（先不改 Boot 版本）。
3. 跑完整构建与冒烟测试，先确认“仅 JDK 升级”没有业务回归。

> 目的：把“JDK 升级影响”和“Boot 大版本升级影响”拆开，便于定位问题。

## 阶段 B：Boot 2.7 -> 3.x
1. parent 改为 Boot 3.x。
2. `java.version` 保持 17。
3. MyBatis-Plus starter 改为 Boot3 专用 starter。
4. 全量替换 `javax.*` -> `jakarta.*`。
5. 重新跑接口测试 + 前端联调。

## 阶段 C：稳定化
1. 补齐集成测试（登录、发职位、投递、查询）。
2. 增加灰度开关与回滚策略。
3. 观察日志与性能（启动时长、接口 RT、错误率）。

---

## 3. 代码改造点（按本项目）

## 3.1 Maven 关键改动（示例）

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>3.3.x</version>
</parent>

<properties>
  <java.version>17</java.version>
  <mybatis-plus.version>3.5.7</mybatis-plus.version>
</properties>

<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
  <version>${mybatis-plus.version}</version>
</dependency>
```

## 3.2 包名迁移（必须）
- `javax.servlet.http.*` -> `jakarta.servlet.http.*`
- `javax.validation.constraints.*` -> `jakarta.validation.constraints.*`

本项目中受影响的典型文件：
- `config/LoginInterceptor.java`
- `dto/*.java`

## 3.3 认证与拦截
- `HandlerInterceptor` 机制可继续使用。
- JWT 逻辑可保留，但建议补充：
  - 统一 token 过期响应体（不仅返回 401 状态码）
  - 细化 role claim 校验与审计日志

## 3.4 数据库层
- MyBatis-Plus mapper/实体结构基本可复用。
- 建议把表名 `user` 改为 `users`（规避关键字/方言兼容问题）。

---

## 4. 风险清单与应对

1. **javax/jakarta 混用导致编译失败**
   - 应对：一次性全仓检索替换并编译。
2. **第三方 starter 版本不匹配**
   - 应对：只保留 Boot3 兼容 starter，逐个升。
3. **运行时 404/参数绑定变化**
   - 应对：增加接口回归用例，覆盖登录、发布、投递主链路。
4. **生产环境 JDK 不一致**
   - 应对：在 CI、镜像、服务器统一为 JDK17。

---

## 5. 验收标准（DoD）

- `mvn clean package` 通过。
- `BsjobApplication` 在 JDK17 正常启动。
- 核心接口全绿：
  - 注册 / 登录
  - 发布职位 / 列表 / 关键词搜索
  - 投递简历 / 查看我的投递
- 前端 4 个页面均可联调。

---

## 6. 回滚方案

- 保留 `release/boot2.7-java8` 长期分支。
- 生产发布采用蓝绿或金丝雀；若错误率上升，切回旧分支镜像。
- 数据库本次升级不做破坏性变更，可原路回退。

---

## 7. 建议的实施顺序（1~2 天）

- Day 1 AM：JDK17 基线切换 + 构建通过
- Day 1 PM：Boot3 与 jakarta 迁移 + 编译通过
- Day 2 AM：接口回归 + 前端联调
- Day 2 PM：压测/灰度 + 发布
