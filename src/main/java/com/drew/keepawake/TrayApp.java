package com.drew.keepawake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.time.format.DateTimeFormatter;

public final class TrayApp {
    private final KeepAwakeController controller;

    public TrayApp(KeepAwakeController controller) {
        this.controller = controller;
    }

    public static boolean isSupported() {
        return SystemTray.isSupported();
    }

    public void start() {
        try {
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu menu = new PopupMenu();

            CheckboxMenuItem enabledItem = new CheckboxMenuItem("Keep awake");
            MenuItem statusItem = new MenuItem(statusText());
            statusItem.setEnabled(false);

            MenuItem implItem = new MenuItem("Impl: " + controller.implementationName());
            implItem.setEnabled(false);

            MenuItem exitItem = new MenuItem("Exit");

            enabledItem.addItemListener(e -> {
                boolean on = (e.getStateChange() == ItemEvent.SELECTED);
                controller.setEnabled(on);
                statusItem.setLabel(statusText());
            });

            exitItem.addActionListener(e -> {
                controller.setEnabled(false);
                tray.remove(getOnlyTrayIcon(tray));
                System.exit(0);
            });

            menu.add(enabledItem);
            menu.addSeparator();
            menu.add(statusItem);
            menu.add(implItem);
            menu.addSeparator();
            menu.add(exitItem);

            Image image = buildTrayImage();
            TrayIcon icon = new TrayIcon(image, "KeepAwake", menu);
            icon.setImageAutoSize(true);

            tray.add(icon);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to start tray app:\n" + ex.getMessage(),
                    "KeepAwake", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String statusText() {
        String onOff = controller.isEnabled() ? "ON" : "OFF";
        String ts = DateTimeFormatter.ISO_INSTANT.format(controller.lastToggled());
        return "Status: " + onOff + " (last toggle: " + ts + ")";
    }

    private TrayIcon getOnlyTrayIcon(SystemTray tray) {
        TrayIcon[] icons = tray.getTrayIcons();
        if (icons.length == 0) throw new IllegalStateException("No tray icon present.");
        return icons[0];
    }

    private Image buildTrayImage() {
        int size = 16;
        Image img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Simple moon-ish icon
        g.fillOval(2, 2, 12, 12);
        g.setComposite(AlphaComposite.Clear);
        g.fillOval(6, 2, 10, 12);
        g.dispose();
        return img;
    }
}