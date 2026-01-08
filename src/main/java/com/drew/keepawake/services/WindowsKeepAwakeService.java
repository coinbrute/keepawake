package com.drew.keepawake.services;

import com.drew.keepawake.KeepAwakeService;
import com.sun.jna.platform.win32.Kernel32;

public final class WindowsKeepAwakeService implements KeepAwakeService {
    // https://learn.microsoft.com/en-us/windows/win32/api/winbase/nf-winbase-setthreadexecutionstate
    private static final int ES_CONTINUOUS = 0x80000000;
    private static final int ES_SYSTEM_REQUIRED = 0x00000001;
    private static final int ES_DISPLAY_REQUIRED = 0x00000002;

    private volatile boolean enabled = false;

    @Override
    public void enable() {
        int flags = ES_CONTINUOUS | ES_SYSTEM_REQUIRED | ES_DISPLAY_REQUIRED;
        Kernel32.INSTANCE.SetThreadExecutionState(flags);
        enabled = true;
    }

    @Override
    public void disable() {
        Kernel32.INSTANCE.SetThreadExecutionState(ES_CONTINUOUS);
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String implementationName() {
        return "Windows SetThreadExecutionState";
    }
}