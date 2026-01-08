# Changelog

All notable changes to this project will be documented in this file.

The format is based on *Keep a Changelog*, and this project adheres to *Semantic Versioning*.

## Unreleased

## 1.0.0 - 2026-01-08

### Added

- System tray UI to toggle “Keep awake” ON/OFF, display status/last toggle time, and show the active implementation.
- Cross-platform sleep inhibition implementations:
  - Windows: `SetThreadExecutionState` (via JNA; no external processes).
  - macOS: `caffeinate -d -i -s`.
  - Linux: `systemd-inhibit --what=idle:sleep` (runs under an inhibitor lock while enabled).
- Packaging workflow using `jpackage` to build installers for macOS (DMG), Windows (MSI), and Linux (DEB/RPM).
- Prevents sleep/dimming **without** simulating mouse/keyboard input.


