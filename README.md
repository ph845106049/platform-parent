platform-parent
├── pom.xml                 # 父工程，仅做模块聚合与依赖版本管理
├── idpauth                 # 登录 / 鉴权服务
│   ├── pom.xml
│   └── src/main/java/com/platform/idpauth
│       ├── application     # 应用层：用例编排、事务边界、DTO 转换
│       ├── interfaces      # 接口层：Controller / VO / 入参校验
│       ├── domain          # 领域层：聚合根 / 值对象 / 领域服务
│       ├── infrastructure  # 基础设施层：Mapper / Repository / MQ / 外部系统
│       └── config          # Spring / MyBatis / 安全配置
│
├── common-core (规划中)    # 公共基础能力模块
└── platform-auth-api(规划中) # 登录鉴权对外契约模块（DTO / Enum）
