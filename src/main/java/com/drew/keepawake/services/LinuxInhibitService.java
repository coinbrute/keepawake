package com.drew.keepawake.services;

import com.drew.keepawake.KeepAwakeService;

import java.io.IOException;

public final class LinuxInhibitService implements KeepAwakeService {
    private volatile Process proc;

    @Override
    public synchronized void enable() {
        if (isEnabled()) return;

        // Prefer systemd-inhibit if available.
        // This runs a long-lived sleep command under an inhibitor lock.
        try {
            proc = new ProcessBuilder(
                    "systemd-inhibit",
                    "--what=idle:sleep",
                    "--who=KeepAwake",
                    "--why=Prevent sleep while enabled",
                    "bash", "-lc", "while true; do sleep 3600; done"
            ).start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start systemd-inhibit: " + e.getMessage(), e);
        }
    }

    @Override
    public synchronized void disable() {
        if (proc != null) {
            proc.destroy();
            proc = null;
        }
    }

    @Override
    public synchronized boolean isEnabled() {
        return proc != null && proc.isAlive();
    }

    @Override
    public String implementationName() {
        return "Linux systemd-inhibit";
    }
}