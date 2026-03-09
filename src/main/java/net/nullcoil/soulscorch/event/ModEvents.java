package net.nullcoil.soulscorch.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.nullcoil.soulscorch.attribute.ModAttributes;
import net.nullcoil.soulscorch.damage.ModDamageTypes;

public class ModEvents {

    public static void register() {

        // 1. The Death Check: Run every tick to see if Corruption >= Max HP
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerList().getPlayers()) {
                if (!player.isAlive()) continue;

                AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
                AttributeInstance corruptionAttr = player.getAttribute(ModAttributes.CORRUPTION);

                if (maxHealthAttr != null && corruptionAttr != null) {
                    if (corruptionAttr.getValue() >= maxHealthAttr.getValue()) {

                        // Grab our custom damage type!
                        // If they were recently hit by a mob, we pass that mob in so we get the "...fighting %2$s" message
                        DamageSource source = player.getLastHurtByMob() != null
                                ? player.damageSources().source(ModDamageTypes.CORRUPTION_DEATH, player.getLastHurtByMob())
                                : player.damageSources().source(ModDamageTypes.CORRUPTION_DEATH);

                        // Deal infinite damage to bypass totems and armor
                        player.hurt(source, Float.MAX_VALUE);
                    }
                }
            }
        });

        // 2. The Respawn Punishment: Set Corruption to (Max HP - 1)
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            // "alive" is false if they died. If it's true, they just returned from the End dimension.
            if (!alive) {
                AttributeInstance newMaxHealth = newPlayer.getAttribute(Attributes.MAX_HEALTH);
                AttributeInstance newCorruption = newPlayer.getAttribute(ModAttributes.CORRUPTION);

                if (newMaxHealth != null && newCorruption != null) {
                    // Set their corruption so they spawn with exactly 1 health point (half a heart)
                    double punishingCorruption = newMaxHealth.getValue() - 1.0;
                    newCorruption.setBaseValue(punishingCorruption);

                    // Force their current health to 1.0 to match
                    newPlayer.setHealth(1.0F);
                }
            }
        });
    }
}