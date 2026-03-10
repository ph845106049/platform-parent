package com.platform.nacosmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

/**
 * 类说明：NacosManagerApplication 负责提供可在 IDEA 中直接运行的 Nacos 管理入口。
 */
public class NacosManagerApplication {

    public static void main(String[] args) throws Exception {
        String command = args.length == 0 ? "start-and-publish" : args[0];
        Path projectRoot = Path.of("").toAbsolutePath();
        Path nacosInfraDir = projectRoot.resolve("infra").resolve("nacos");
        Path platformInfraDir = projectRoot.resolve("infra").resolve("platform");
        Path publishScript = projectRoot.resolve("scripts").resolve("nacos").resolve("publish_configs.sh");
        String nacosServerAddr = System.getenv().getOrDefault("NACOS_SERVER_ADDR", "127.0.0.1:8858");

        switch (command) {
            case "start" -> runAndPrint(List.of("docker", "compose", "up", "-d"), nacosInfraDir.toFile());
            case "stop" -> runAndPrint(List.of("docker", "compose", "down"), nacosInfraDir.toFile());
            case "restart" -> {
                runAndPrint(List.of("docker", "compose", "down"), nacosInfraDir.toFile());
                runAndPrint(List.of("docker", "compose", "up", "-d"), nacosInfraDir.toFile());
            }
            case "status" -> runAndPrint(List.of("docker", "compose", "ps"), nacosInfraDir.toFile());
            case "publish" -> runAndPrint(List.of("bash", publishScript.toString()), projectRoot.toFile());
            case "start-and-publish" -> {
                runAndPrint(List.of("docker", "compose", "up", "-d"), platformInfraDir.toFile());
                runAndPrint(List.of("bash", publishScript.toString()), projectRoot.toFile());
            }
            case "start-infra" -> runAndPrint(List.of("docker", "compose", "up", "-d"), platformInfraDir.toFile());
            case "stop-infra" -> runAndPrint(List.of("docker", "compose", "down"), platformInfraDir.toFile());
            case "infra-status" -> runAndPrint(List.of("docker", "compose", "ps"), platformInfraDir.toFile());
            case "start-custom-nacos" -> {
                String customNacosDir = requireEnv("REGISTER_PROJECT_DIR");
                ensureCustomProjectExists(customNacosDir);
                runAndPrint(List.of("mvn", "spring-boot:run", "-DskipTests"), new File(customNacosDir));
            }
            case "start-custom-and-publish" -> {
                String customNacosDir = requireEnv("REGISTER_PROJECT_DIR");
                ensureCustomProjectExists(customNacosDir);
                if (!isNacosReady(nacosServerAddr)) {
                    System.out.println("检测到 " + nacosServerAddr + " 尚未就绪，请先在单独 Run Configuration 启动 start-custom-nacos。");
                    System.out.println("当前仅执行配置发布，若 Nacos 未就绪会失败。");
                }
                runAndPrint(List.of("bash", publishScript.toString()), projectRoot.toFile());
            }
            default -> printHelp(projectRoot.toString());
        }
    }

    private static void runAndPrint(List<String> command, File workDir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(workDir);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int code = process.waitFor();
        if (code != 0) {
            throw new IllegalStateException("命令执行失败(" + code + "): " + String.join(" ", command));
        }
    }

    private static void printHelp(String root) {
        System.out.println("NacosManagerApplication 使用方式：");
        System.out.println("args=start              启动 Nacos");
        System.out.println("args=stop               停止 Nacos");
        System.out.println("args=restart            重启 Nacos");
        System.out.println("args=status             查看 Nacos 状态");
        System.out.println("args=publish            发布 nacos/configs 下配置");
        System.out.println("args=start-and-publish  默认命令：启动本项目 Nacos 并发布配置");
        System.out.println("args=start-infra        启动基础设施(MySQL/Redis/RabbitMQ/Nacos)");
        System.out.println("args=stop-infra         停止基础设施");
        System.out.println("args=infra-status       查看基础设施状态");
        System.out.println("args=start-custom-nacos 启动外部 Nacos 项目(可选)");
        System.out.println("args=start-custom-and-publish 基于外部 Nacos 发布配置(可选)");
        System.out.println("环境变量 REGISTER_PROJECT_DIR 用于外部 Nacos 项目路径(仅 custom 命令需要)");
        System.out.println("环境变量 NACOS_SERVER_ADDR 可指定发布目标，例如 127.0.0.1:8858");
        System.out.println("当前项目根目录: " + root);
    }

    private static String requireEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("缺少环境变量 " + key + "，请显式指定外部 Nacos 项目路径。");
        }
        return value;
    }

    private static void ensureCustomProjectExists(String dir) {
        File project = new File(dir);
        if (!project.exists() || !project.isDirectory()) {
            throw new IllegalArgumentException("外部 Nacos 项目路径不存在: " + dir);
        }
    }

    private static boolean isNacosReady(String serverAddr) {
        try {
            URL url = new URL("http://" + serverAddr + "/nacos/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(800);
            conn.setReadTimeout(800);
            conn.setRequestMethod("GET");
            return conn.getResponseCode() < 500;
        } catch (Exception ex) {
            return false;
        }
    }
}
