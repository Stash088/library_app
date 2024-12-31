package org.example.utils;


import io.javalin.http.Context;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

public class HealthCheckController {

    public static void healthCheck(Context ctx) {
        Map<String, Object> healthStatus = new HashMap<>();

        // Проверка доступности сервера
        healthStatus.put("status", "UP");

        // Информация о времени работы
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        healthStatus.put("uptime", formatUptime(uptime));

        // Информация об использовании памяти
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long usedHeapMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxHeapMemory = memoryBean.getHeapMemoryUsage().getMax();
        double memoryUsage = (double) usedHeapMemory / maxHeapMemory;
        healthStatus.put("memoryUsage", String.format("%.2f%%", memoryUsage * 100));

        // Информация о загрузке процессора
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getSystemLoadAverage();
        healthStatus.put("cpuLoad", cpuLoad >= 0 ? String.format("%.2f", cpuLoad) : "N/A");

        // Версия приложения (предполагается, что у вас есть константа VERSION)
        healthStatus.put("version", "1.0.0"); // Замените на актуальную версию вашего приложения

        ctx.status(200).json(healthStatus);
    }

    private static String formatUptime(long uptimeMillis) {
        long seconds = uptimeMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return String.format("%d days, %d hours, %d minutes", days, hours % 24, minutes % 60);
    }
}