# Nacos 统一配置使用说明

本项目已改为“本地仅保留 Nacos 引导配置，业务配置全部托管 Nacos”。
并且采用强依赖模式：`Nacos 不可用时服务会启动失败`。

## 1. 启动本项目 Nacos（推荐）

```bash
cd /Users/xinyang/projects/platform-parent/infra/nacos
docker compose up -d
```

默认地址：`http://127.0.0.1:8858/nacos`

你也可以在 IDEA 中运行 `nacos-manager`，使用：

- `start`
- `start-and-publish`

## 2. 启动基础设施（可选）

```bash
cd /Users/xinyang/projects/platform-parent/infra/platform
docker compose up -d
```

包含：`MySQL + Redis + RabbitMQ + Nacos`

## 3. 外部 Nacos（可选）

如果你确实要复用其他项目的 Nacos，只能通过显式方式启用：

- 运行前设置环境变量 `REGISTER_PROJECT_DIR`
- 再执行 `start-custom-nacos` 或 `start-custom-and-publish`

这两个命令不是默认行为，不会影响你另一个项目。

## 4. 发布项目配置到 Nacos

```bash
cd /Users/xinyang/projects/platform-parent
./scripts/nacos/publish_configs.sh
```

脚本会把 `nacos/configs/*.yml` 发布到 Nacos：

- `idp-auth.yml`
- `user-service.yml`
- `sign-service.yml`
- `sms-service.yml`
- `gateway-service.yml`
- `ai-service.yml`

## 5. 启动服务

建议先设置环境变量：

```bash
export NACOS_SERVER_ADDR=127.0.0.1:8858
export NACOS_GROUP=DEFAULT_GROUP
export NACOS_NAMESPACE=
```

然后按模块启动即可。

## 6. 当前结构说明

- 各模块 `application.yml` 仅保留：
  - `spring.application.name`
  - `spring.config.import=optional:nacos:...`
  - `spring.cloud.nacos.*` 连接信息
- 业务配置统一放在：
  - `/Users/xinyang/projects/platform-parent/nacos/configs`

## 7. 网关统一入口

建议只通过网关访问业务接口：`http://localhost:7000`

- `/api/auth/**` -> `idp-auth`
- `/api/emergency/**` -> `user-service`
- `/api/sign/**` -> `sign-service`
- `/api/ai/**` -> `ai-service`
