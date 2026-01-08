package com.drew.keepawake;

public enum Platform {
    WINDOWS, MAC, LINUX, OTHER;

    public static Platform detect() {
        String os = System.getProperty("os.name", "unknown").toLowerCase();
        if (os.contains("win")) return WINDOWS;
        if (os.contains("mac")) return MAC;
        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) return LINUX;
        return OTHER;
    }
}