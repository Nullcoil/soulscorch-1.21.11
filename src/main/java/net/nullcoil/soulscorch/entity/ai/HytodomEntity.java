package net.nullcoil.soulscorch.entity.ai;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.nullcoil.soulscorch.entity.ai.jellyfish.JellyfishEntity;

public class HytodomEntity extends JellyfishEntity {
    public HytodomEntity(EntityType<? extends JellyfishEntity> type, Level level) {
        super(type, level);
    }
}