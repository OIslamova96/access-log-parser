package com.example.accesslogparser;

public class UserAgent {
    private final String osType;
    private final String browserType;

    public UserAgent(String userAgentString) {
        if (userAgentString.contains("Windows")) {
            this.osType = "Windows";
        } else if (userAgentString.contains("Macintosh") || userAgentString.contains("Mac OS")) {
            this.osType = "macOS";
        } else if (userAgentString.contains("Linux")) {
            this.osType = "Linux";
        } else {
            this.osType = "Other OS";
        }

        if (userAgentString.contains("Edg")) {
            this.browserType = "Edge";
        } else if (userAgentString.contains("Firefox")) {
            this.browserType = "Firefox";
        } else if (userAgentString.contains("Chrome")) {
            this.browserType = "Chrome";
        } else if (userAgentString.contains("Opera") || userAgentString.contains("OPR")) {
            this.browserType = "Opera";
        } else if (userAgentString.contains("Safari")) {
            this.browserType = "Safari";
        } else if (userAgentString.contains("Googlebot") || userAgentString.contains("YandexBot")) {
            this.browserType = "Bot";
        } else {
            this.browserType = "Other Browser";
        }
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowserType() {
        return browserType;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osType='" + osType + '\'' +
                ", browserType='" + browserType + '\'' +
                '}';
    }
}