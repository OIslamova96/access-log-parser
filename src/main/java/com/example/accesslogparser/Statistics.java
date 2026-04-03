package com.example.accesslogparser;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    private Map<String, Integer> osTypeCounts;
    private Map<String, Integer> browserTypeCounts;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
        this.osTypeCounts = new HashMap<>();
        this.browserTypeCounts = new HashMap<>();
    }

    public void addEntry(LogEntry entry) {
        this.totalTraffic += entry.getDataSize();

        LocalDateTime entryTime = entry.getDateTime();
        if (entryTime.isBefore(this.minTime)) {
            this.minTime = entryTime;
        }
        if (entryTime.isAfter(this.maxTime)) {
            this.maxTime = entryTime;
        }

        String os = entry.getUserAgent().getOsType();
        osTypeCounts.put(os, osTypeCounts.getOrDefault(os, 0) + 1);

        String browser = entry.getUserAgent().getBrowserType();
        browserTypeCounts.put(browser, browserTypeCounts.getOrDefault(browser, 0) + 1);
    }

    public double getTrafficRate() {
        if (minTime.equals(LocalDateTime.MAX) || maxTime.equals(LocalDateTime.MIN)) {
            return 0.0;
        }

        Duration duration = Duration.between(minTime, maxTime);
        long hours = duration.toHours();

        if (hours == 0 && duration.toMinutes() > 0) {
            return (double) totalTraffic / (duration.toMinutes() / 60.0);
        } else if (hours == 0) {
            return 0.0;
        }

        return (double) totalTraffic;
    }

    public Map<String, Integer> getOsTypeCounts() { return osTypeCounts; }
    public Map<String, Integer> getBrowserTypeCounts() { return browserTypeCounts; }
}