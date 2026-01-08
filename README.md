# KeepAwake (Java)

Prevents the system from sleeping / dimming while enabled.
Does NOT fake mouse/keyboard input.

## Build
mvn -q -DskipTests package

## Run
java -jar target/keepawake-1.0.0.jar

## Notes
- Windows: uses SetThreadExecutionState (no external processes)
- macOS: uses `caffeinate` (native tool)
- Linux: uses `systemd-inhibit` if available