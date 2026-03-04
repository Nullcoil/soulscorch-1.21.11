package net.nullcoil.soulscorch.event;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class SleepHealthResetHandler {
    private SleepHealthResetHandler() {}

    public static void register() {
        EntitySleepEvents.STOP_SLEEPING.register(((entity, blockPos) -> {
            if (!(entity instanceof Player player)) return;
            if (player.level().isClientSide()) return;

            AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttr == null) return;

            maxHealthAttr.setBaseValue(20d);
        }));
    }
}
