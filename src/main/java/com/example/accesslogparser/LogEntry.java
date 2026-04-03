package com.example.accesslogparser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod httpMethod;
    private final String path;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final UserAgent userAgent;

    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\S+) - - \\[(\\d{2}/\\w{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} [+-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d+) (\\d+) \"([^\"]*)\" \"([^\"]*)\"$"
    );

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH
    );

    public LogEntry(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректный формат строки лога: " + logLine);
        }

        this.ipAddress = matcher.group(1);

        try {
            this.dateTime = LocalDateTime.parse(matcher.group(2), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты/времени в логе: " + matcher.group(2), e);
        }

        String methodString = matcher.group(3).toUpperCase();
        this.httpMethod = HttpMethod.valueOf(methodString);

        this.path = matcher.group(4);

        try {
            this.responseCode = Integer.parseInt(matcher.group(6));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный код ответа: " + matcher.group(6), e);
        }

        this.dataSize = Integer.parseInt(matcher.group(7));

        this.referer = matcher.group(8).isEmpty() ? "-" : matcher.group(8);
        this.userAgent = new UserAgent(matcher.group(9));
    }

    public String getIpAddress() { return ipAddress; }
    public LocalDateTime getDateTime() { return dateTime; }
    public HttpMethod getHttpMethod() { return httpMethod; }
    public String getPath() { return path; }
    public int getResponseCode() { return responseCode; }
    public int getDataSize() { return dataSize; }
    public String getReferer() { return referer; }
    public UserAgent getUserAgent() { return userAgent; }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", dateTime=" + dateTime +
                ", httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", responseCode=" + responseCode +
                ", dataSize=" + dataSize +
                ", referer='" + referer + '\'' +
                ", userAgent=" + userAgent +
                '}';
    }
}