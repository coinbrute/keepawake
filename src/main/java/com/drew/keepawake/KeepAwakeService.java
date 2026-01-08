package com.drew.keepawake;

import com.drew.keepawake.services.LinuxInhibitService;
import com.drew.keepawake.services.MacCaffeinateService;
import com.drew.keepawake.services.WindowsKeepAwakeService;

public interface KeepAwakeService {
    void enable();
    void disable();
    boolean isEnabled();
    String implementationName();

    static KeepAwakeService forCurrentPlatform() {
        return switch (Platform.detect()) {
            case WINDOWS -> new WindowsKeepAwakeService();
            case MAC -> new MacCaffeinateService();
            case LINUX -> new LinuxInhibitService();
            default -> new KeepAwakeService() {
                private volatile boolean enabled = false;
                @Override public void enable() { enabled = true; }
                @Override public void disable() { enabled = false; }
                @Override public boolean isEnabled() { return enabled; }
                @Override public String implementationName() { return "No-op (unsupported platform)"; }
            };
        };
    }
}