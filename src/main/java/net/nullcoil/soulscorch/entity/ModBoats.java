package net.nullcoil.soulscorch.entity;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDataImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.entity.vehicle.*;

import java.util.function.Supplier;

public class ModBoats {
    public static final Identifier SEEPING = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "seeping");
    public static final Identifier CRIMSON = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "crimson");
    public static final Identifier WARPED = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "warped");

    public static final BoatItem CRIMSON_BOAT = registerCustomBoat(CRIMSON, false, false);
    public static final BoatItem CRIMSON_CHEST_BOAT = registerCustomBoat(CRIMSON, true, false);
    public static final BoatItem WARPED_BOAT = registerCustomBoat(WARPED, false, false);
    public static final BoatItem WARPED_CHEST_BOAT = registerCustomBoat(WARPED, true, false);
    public static final BoatItem SEEPING_BOAT = registerCustomBoat(SEEPING, false, true);
    public static final BoatItem SEEPING_CHEST_BOAT = registerCustomBoat(SEEPING, true, true);

    private static BoatItem registerCustomBoat(Identifier id, boolean chest, boolean seeping) {
        TerraformBoatDataImpl boatData = TerraformBoatDataImpl.empty(id);

        var itemKey = chest ? boatData.chestBoatKey() : boatData.boatKey();
        var entityTypeKey = chest ? boatData.chestBoatEntityTypeKey() : boatData.boatEntityTypeKey();

        Item[] itemHolder = { Items.AIR };
        Supplier<Item> itemSupplier = () -> itemHolder[0];

        if (chest) {
            EntityType<? extends NetherChestBoat> entityType = Registry.register(BuiltInRegistries.ENTITY_TYPE, entityTypeKey,
                    EntityType.Builder.<NetherChestBoat>of(
                                    seeping
                                            ? (type, world) -> new SeepingChestBoat(type, world, itemSupplier)
                                            : (type, world) -> new NetherChestBoat(type, world, itemSupplier),
                                    MobCategory.MISC)
                            .noLootTable()
                            .sized(1.375f, 0.5625f)
                            .eyeHeight(0.5625f)
                            .clientTrackingRange(10)
                            .build(entityTypeKey));
            TerraformBoatDataImpl.addChestBoat(id, (EntityType<ChestBoat>)(EntityType<?>) entityType);
            BoatItem item = Registry.register(BuiltInRegistries.ITEM, itemKey,
                    new BoatItem((EntityType<? extends AbstractBoat>) entityType, new Item.Properties().stacksTo(1).setId(itemKey)));
            itemHolder[0] = item;
            DispenserBlock.registerBehavior(item, new BoatDispenseItemBehavior((EntityType<? extends AbstractBoat>) entityType));
            return item;
        } else {
            EntityType<? extends NetherBoat> entityType = Registry.register(BuiltInRegistries.ENTITY_TYPE, entityTypeKey,
                    EntityType.Builder.<NetherBoat>of(
                                    seeping
                                            ? (type, world) -> new SeepingBoat(type, world, itemSupplier)
                                            : (type, world) -> new NetherBoat(type, world, itemSupplier),
                                    MobCategory.MISC)
                            .noLootTable()
                            .sized(1.375f, 0.5625f)
                            .eyeHeight(0.5625f)
                            .clientTrackingRange(10)
                            .build(entityTypeKey));
            TerraformBoatDataImpl.addBoat(id, (EntityType<Boat>)(EntityType<?>) entityType);
            BoatItem item = Registry.register(BuiltInRegistries.ITEM, itemKey,
                    new BoatItem((EntityType<? extends AbstractBoat>) entityType, new Item.Properties().stacksTo(1).setId(itemKey)));
            itemHolder[0] = item;
            DispenserBlock.registerBehavior(item, new BoatDispenseItemBehavior((EntityType<? extends AbstractBoat>) entityType));
            return item;
        }
    }

    public static void register() {
        Soulscorch.LOGGER.info("Registering boats for " + Soulscorch.MOD_ID);
    }
}