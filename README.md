---

# 🚀 Platform Parent

统一平台父工程，基于 **Java 21 + Spring Boot 3.x + Maven 多模块架构**，采用 DDD 分层设计，用于沉淀平台级能力并支撑多业务服务扩展。

---

## 📦 Project Structure

```bash
platform-parent
├── pom.xml                 # 父工程，仅做模块聚合与版本管理
├── idpauth                 # 登录 / 鉴权服务
│   ├── pom.xml
│   └── src/main/java/com/platform/idpauth
│       ├── application     # 应用层：用例编排、事务边界、DTO 转换
│       ├── interfaces      # 接口层：Controller / VO / 入参校验
│       ├── domain          # 领域层：聚合根 / 值对象 / 领域服务
│       ├── infrastructure  # 基础设施层：Mapper / Repository / MQ / 外部系统
│       └── config          # Spring / MyBatis / 安全配置
│
├── common-core (planned)   # 公共基础能力模块
└── platform-auth-api (planned) # 登录鉴权对外契约模块（DTO / Enum）
```

---

## 🧠 Architecture

### DDD Layer Responsibilities

| Layer          | Responsibility                                |
| -------------- | --------------------------------------------- |
| interfaces     | REST API / Controller / Validation            |
| application    | Use Case Orchestration / Transaction Boundary |
| domain         | Aggregates / Domain Services / Business Rules |
| infrastructure | Mapper / Repository / MQ / External Systems   |
| config         | Spring / Security / MyBatis Configuration     |

---

## 🔀 Module Strategy

### Current Modules

| Module  | Description                        |
| ------- | ---------------------------------- |
| idpauth | Login & Authorization core service |

### Planned Modules

| Module            | Responsibility                                        |
| ----------------- | ----------------------------------------------------- |
| common-core       | Result / Exception / BaseEntity / Common Utils        |
| platform-auth-api | LoginRequest / TokenInfo / Auth Enums (Contract Only) |

> ⚠ **Do NOT place Entity / Repository / Domain Models into `common-core`.**

---

## ▶️ How to Run

### Maven CLI

```bash
mvn clean install
cd idpauth
mvn spring-boot:run
```

### IntelliJ IDEA

Run the main class:

```
com.platform.idpauth.IdpAuthApplication
```

---

## 🛡 Common Module Rules

A class can be moved into `common-core` only if:

* It is reused by **more than two services**
* It is **stable** (rarely changed)
* It does **not depend on database schema or infrastructure detail**

---

## 🧰 Tech Stack

| Tech        | Version |
| ----------- | ------- |
| Java        | 21      |
| Spring Boot | 3.2.x   |
| Maven       | 3.9+    |

---

