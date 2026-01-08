package com.drew.keepawake;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

public final class KeepAwakeController {
    private final KeepAwakeService service;
    private final AtomicBoolean enabled = new AtomicBoolean(false);
    private volatile Instant lastToggled = Instant.EPOCH;

    public KeepAwakeController(KeepAwakeService service) {
        this.service = service;
    }

    public synchronized void setEnabled(boolean on) {
        if (on == enabled.get()) return;

        if (on) service.enable();
        else service.disable();

        enabled.set(on);
        lastToggled = Instant.now();
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public Instant lastToggled() {
        return lastToggled;
    }

    public String implementationName() {
        return service.implementationName();
    }
}