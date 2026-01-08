package com.drew.keepawake;

import javax.swing.*;

public final class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KeepAwakeService service = KeepAwakeService.forCurrentPlatform();
            KeepAwakeController controller = new KeepAwakeController(service);

            // If system tray not supported, fallback to simple dialog controls.
            if (!TrayApp.isSupported()) {
                JOptionPane.showMessageDialog(null,
                        "System tray not supported on this environment.\n" +
                                "KeepAwake will run headless (always OFF by default).",
                        "KeepAwake", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            new TrayApp(controller).start();
        });
    }
}