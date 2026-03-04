package net.nullcoil.soulscorch;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.nullcoil.soulscorch.block.ModBlocks;
import net.nullcoil.soulscorch.entity.ModEntities;
import net.nullcoil.soulscorch.entity.client.blazt.BlaztModel;
import net.nullcoil.soulscorch.entity.client.blazt.BlaztRenderer;
import net.nullcoil.soulscorch.screen.ModScreenHandlers;
import net.nullcoil.soulscorch.screen.SoulBrewingStandScreen;

public class SoulscorchClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModScreenHandlers.SOUL_BREWING_STAND, SoulBrewingStandScreen::new);
        BlockRenderLayerMap.putBlock(ModBlocks.SOUL_BREWING_STAND, ChunkSectionLayer.CUTOUT);
        EntityModelLayerRegistry.registerModelLayer(BlaztModel.BLAZT, BlaztModel::createBodyLayer);
        EntityRendererRegistry.register(ModEntities.BLAZT, BlaztRenderer::new);
        EntityRendererRegistry.register(ModEntities.SOUL_CHARGE_PROJECTILE, net.minecraft.client.renderer.entity.ThrownItemRenderer::new);
    }
}
