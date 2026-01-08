package com.drew.keepawake.services;

import com.drew.keepawake.KeepAwakeService;

import java.io.IOException;

public final class MacCaffeinateService implements KeepAwakeService {
    private volatile Process proc;

    @Override
    public synchronized void enable() {
        if (isEnabled()) return;
        try {
            // -d prevents display sleep, -i prevents idle sleep, -s keeps system awake on AC
            proc = new ProcessBuilder("caffeinate", "-d", "-i", "-s").start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start caffeinate: " + e.getMessage(), e);
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
        return "macOS caffeinate";
    }
}