package net.nullcoil.soulscorch.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Ghast;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.entity.ai.BlaztEntity;
import net.nullcoil.soulscorch.entity.ai.SoullessEntity;
import net.nullcoil.soulscorch.entity.projectile.SoulChargeProjectile;

public class ModEntities {

    public static final EntityType<BlaztEntity> BLAZT = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "blazt"),
            EntityType.Builder.of(BlaztEntity::new, MobCategory.MONSTER)
                    .sized(3.0f, 3.0f)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "blazt")))
    );

    public static final EntityType<SoulChargeProjectile> SOUL_CHARGE_PROJECTILE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "soul_charge_projectile"),
            EntityType.Builder.<SoulChargeProjectile>of(SoulChargeProjectile::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F) // Standard fireball hitbox
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "soul_charge")))
    );

    public static final EntityType<SoullessEntity> SOULLESS = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "soulless"),
            EntityType.Builder.of(SoullessEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "soulless")))
    );

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(BLAZT, BlaztEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SOULLESS, SoullessEntity.createAttributes());
    }

    public static void register() {
        Soulscorch.LOGGER.info("Registering Mod Entities for " + Soulscorch.MOD_ID);
        registerAttributes();
    }
}