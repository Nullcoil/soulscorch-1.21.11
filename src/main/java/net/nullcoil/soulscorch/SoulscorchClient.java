package net.nullcoil.soulscorch;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.animal.feline.CatModel;
import net.minecraft.client.model.animal.feline.OcelotModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.Identifier;
import net.nullcoil.soulscorch.block.ModBlocks;
import net.nullcoil.soulscorch.entity.ModEntities;
import net.nullcoil.soulscorch.entity.ai.RestlessEntity;
import net.nullcoil.soulscorch.entity.client.blazt.BlaztModel;
import net.nullcoil.soulscorch.entity.client.blazt.BlaztRenderer;
import net.nullcoil.soulscorch.entity.client.companion.SoulCatRenderer;
import net.nullcoil.soulscorch.entity.client.companion.SoulWolfRenderer;
import net.nullcoil.soulscorch.entity.client.jellyfish.JellyfishModel;
import net.nullcoil.soulscorch.entity.client.jellyfish.JellyfishRenderer;
import net.nullcoil.soulscorch.entity.client.restless.RestlessManeModel;
import net.nullcoil.soulscorch.entity.client.restless.RestlessModel;
import net.nullcoil.soulscorch.entity.client.restless.RestlessRenderer;
import net.nullcoil.soulscorch.entity.client.soulless.SoullessModel;
import net.nullcoil.soulscorch.entity.client.soulless.SoullessRenderer;
import net.nullcoil.soulscorch.screen.ModScreenHandlers;
import net.nullcoil.soulscorch.screen.SoulBrewingStandScreen;

public class SoulscorchClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModScreenHandlers.SOUL_BREWING_STAND, SoulBrewingStandScreen::new);
        BlockRenderLayerMap.putBlock(ModBlocks.SOUL_BREWING_STAND, ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.GHOST_PEPPER_SHRUB, ChunkSectionLayer.CUTOUT);

        EntityModelLayerRegistry.registerModelLayer(BlaztModel.BLAZT, BlaztModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntities.BLAZT, BlaztRenderer::new);

        EntityRendererRegistry.register(ModEntities.SOUL_CHARGE_PROJECTILE, net.minecraft.client.renderer.entity.ThrownItemRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SoullessModel.SOULLESS, SoullessModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntities.SOULLESS, SoullessRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RestlessModel.RESTLESS, RestlessModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RestlessManeModel.MANE_LAYER, RestlessManeModel::createManeLayer);
        EntityRendererRegistry.register(ModEntities.RESTLESS, RestlessRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(JellyfishModel.HYTODOM, JellyfishModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntities.HYTODOM, JellyfishRenderer::new);

        EntityRendererRegistry.register(ModEntities.SOULBORNE_CAT, SoulCatRenderer::new);
        EntityRendererRegistry.register(ModEntities.SOULBORNE_WOLF, SoulWolfRenderer::new);

    }
}
