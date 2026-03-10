# nacos-manager

用于在 IDEA 中直接管理 Nacos 的独立模块。

主类：

`com.platform.nacosmanager.NacosManagerApplication`

默认行为（不传参数）：

- 自动执行 `start-and-publish`（启动本项目 Nacos 并发布配置）

## IDEA 运行方式

在 Run Configuration 的 Program arguments 里传入以下命令之一：

- `start`：启动 Nacos（docker compose up -d）
- `stop`：停止 Nacos（docker compose down）
- `restart`：重启 Nacos
- `status`：查看 Nacos 状态
- `publish`：发布 `nacos/configs/*.yml` 到 Nacos
- `start-and-publish`：启动 Nacos 并发布配置
- `start-infra`：启动基础设施（MySQL/Redis/RabbitMQ/Nacos）
- `stop-infra`：停止基础设施
- `infra-status`：查看基础设施状态
- `start-custom-nacos`：启动外部 Nacos 项目（可选）
- `start-custom-and-publish`：基于外部 Nacos 发布配置（可选）

可选环境变量：

- `REGISTER_PROJECT_DIR`：外部 Nacos 项目路径（仅 custom 命令需要显式设置）
- `NACOS_SERVER_ADDR`：Nacos 地址（默认 `127.0.0.1:8858`）

> 该模块会基于项目根目录执行命令，依赖本机已安装 Docker。
