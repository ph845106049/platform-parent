#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
CONFIG_DIR="${ROOT_DIR}/nacos/configs"
NACOS_SERVER_ADDR="${NACOS_SERVER_ADDR:-127.0.0.1:8858}"
NACOS_GROUP="${NACOS_GROUP:-DEFAULT_GROUP}"
NACOS_NAMESPACE="${NACOS_NAMESPACE:-}"
NACOS_USERNAME="${NACOS_USERNAME:-nacos}"
NACOS_PASSWORD="${NACOS_PASSWORD:-nacos}"

if [ ! -d "${CONFIG_DIR}" ]; then
  echo "配置目录不存在: ${CONFIG_DIR}"
  exit 1
fi

echo "等待 Nacos 就绪: http://${NACOS_SERVER_ADDR}/nacos"
for i in {1..60}; do
  if curl -fsS "http://${NACOS_SERVER_ADDR}/nacos/" >/dev/null 2>&1; then
    break
  fi
  sleep 2
  if [ "$i" -eq 60 ]; then
    echo "Nacos 未就绪，请先启动 Nacos"
    exit 1
  fi
done

TOKEN=""
if [ -n "${NACOS_USERNAME}" ] && [ -n "${NACOS_PASSWORD}" ]; then
  TOKEN="$(curl -fsS -X POST "http://${NACOS_SERVER_ADDR}/nacos/v1/auth/login" \
    -d "username=${NACOS_USERNAME}&password=${NACOS_PASSWORD}" | sed -n 's/.*"accessToken":"\([^"]*\)".*/\1/p' || true)"
fi

for file in "${CONFIG_DIR}"/*.yml; do
  [ -f "$file" ] || continue
  data_id="$(basename "$file")"
  echo "发布配置: ${data_id}"

  if [ -n "${TOKEN}" ]; then
    curl -fsS -X POST "http://${NACOS_SERVER_ADDR}/nacos/v1/cs/configs?accessToken=${TOKEN}" \
      --data-urlencode "dataId=${data_id}" \
      --data-urlencode "group=${NACOS_GROUP}" \
      --data-urlencode "tenant=${NACOS_NAMESPACE}" \
      --data-urlencode "type=yaml" \
      --data-urlencode "content@${file}" >/dev/null
  else
    curl -fsS -X POST "http://${NACOS_SERVER_ADDR}/nacos/v1/cs/configs" \
      --data-urlencode "dataId=${data_id}" \
      --data-urlencode "group=${NACOS_GROUP}" \
      --data-urlencode "tenant=${NACOS_NAMESPACE}" \
      --data-urlencode "type=yaml" \
      --data-urlencode "content@${file}" >/dev/null
  fi
done

echo "Nacos 配置发布完成"
