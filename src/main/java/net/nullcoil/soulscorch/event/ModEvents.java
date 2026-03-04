package net.nullcoil.soulscorch.event;

public class ModEvents {
    public static void register() {
        SleepHealthResetHandler.register();
        SoulbreakEventHandler.register();
    }
}
