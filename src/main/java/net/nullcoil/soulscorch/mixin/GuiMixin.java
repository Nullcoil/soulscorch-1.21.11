package net.nullcoil.soulscorch.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.nullcoil.soulscorch.Soulscorch;
import net.nullcoil.soulscorch.attribute.ModAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow protected abstract LivingEntity getPlayerVehicleWithHealth();
    @Shadow protected abstract int getVehicleMaxHearts(LivingEntity livingEntity);

    // Your freshly renamed and updated sprites!
    @Unique private static final Identifier CORRUPTED_FULL = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "hud/heart/corrupted_full");
    @Unique private static final Identifier CORRUPTED_HALF = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "hud/heart/corrupted_half");
    @Unique private static final Identifier CORRUPTED_HARDCORE_FULL = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "hud/heart/corrupted_hardcore_full");
    @Unique private static final Identifier CORRUPTED_HARDCORE_HALF = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "hud/heart/corrupted_hardcore_half");
    @Unique private static final Identifier CORRUPTED_VEHICLE_HALF = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID, "hud/heart/corrupted_vehicle_half");

    @Unique private float soulscorch$playerMaxHealth = 0;
    @Unique private float soulscorch$playerCorruption = 0;
    @Unique private int soulscorch$playerHeartIndex = -1;

    // ================================
    //       PLAYER HEALTH LOGIC
    // ================================

    @Inject(method = "renderHearts", at = @At("HEAD"))
    private void soulscorch$startPlayerRender(GuiGraphics guiGraphics, Player player, int x, int y, int height, int offsetHeartIndex, float maxHealth, int currentHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        soulscorch$playerMaxHealth = player.getMaxHealth();
        var attr = player.getAttribute(ModAttributes.CORRUPTION);
        soulscorch$playerCorruption = attr != null ? (float) attr.getValue() : 0f;

        // Vanilla loops backward from (Max Health + Absorption) down to 0.
        int p = Mth.ceil((double)maxHealth / 2.0D);
        int q = Mth.ceil((double)absorptionAmount / 2.0D);
        soulscorch$playerHeartIndex = p + q - 1;
    }

    @Inject(method = "renderHeart", at = @At("TAIL"))
    private void soulscorch$overlayPlayerCrackedHeart(
            GuiGraphics guiGraphics, @Coerce Object heartType,
            int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci
    ) {
        if (heartType.toString().equals("CONTAINER")) {
            int currentS = soulscorch$playerHeartIndex;
            soulscorch$playerHeartIndex--;

            if (soulscorch$playerCorruption > 0) {
                float safeHealth = soulscorch$playerMaxHealth - soulscorch$playerCorruption;
                int leftHalf = currentS * 2;
                int rightHalf = currentS * 2 + 1;

                if (leftHalf < soulscorch$playerMaxHealth) {
                    boolean fullyCorrupted = leftHalf >= safeHealth && rightHalf >= safeHealth;
                    boolean halfCorrupted = leftHalf < safeHealth && rightHalf >= safeHealth;

                    if (fullyCorrupted) {
                        Identifier sprite = hardcore ? CORRUPTED_HARDCORE_FULL : CORRUPTED_FULL;
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, 9, 9);
                    } else if (halfCorrupted) {
                        Identifier sprite = hardcore ? CORRUPTED_HARDCORE_HALF : CORRUPTED_HALF;
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, 9, 9);
                    }
                }
            }
        }
    }

    // ================================
    //      VEHICLE HEALTH LOGIC
    // ================================

    @Inject(method = "renderVehicleHealth", at = @At("TAIL"))
    private void soulscorch$overlayVehicleCrackedHearts(GuiGraphics guiGraphics, CallbackInfo ci) {
        LivingEntity vehicle = this.getPlayerVehicleWithHealth();
        if (vehicle == null) return;

        var attr = vehicle.getAttribute(ModAttributes.CORRUPTION);
        float corruption = attr != null ? (float) attr.getValue() : 0f;

        if (corruption <= 0) return;

        float maxHealth = vehicle.getMaxHealth();
        int hearts = this.getVehicleMaxHearts(vehicle);
        if (hearts == 0) return;

        float safeHealth = maxHealth - corruption;

        int y = guiGraphics.guiHeight() - 39;
        int rightEdgeX = guiGraphics.guiWidth() / 2 + 91;
        int currentHeartIndex = 0;

        for (int n = 0; hearts > 0; n += 20) {
            int rowHearts = Math.min(hearts, 10);
            hearts -= rowHearts;

            for (int p = 0; p < rowHearts; ++p) {
                int x = rightEdgeX - p * 8 - 9;

                int leftHalf = currentHeartIndex * 2;
                int rightHalf = currentHeartIndex * 2 + 1;

                if (leftHalf < maxHealth) {
                    boolean fullyCorrupted = leftHalf >= safeHealth && rightHalf >= safeHealth;
                    boolean halfCorrupted = leftHalf < safeHealth && rightHalf >= safeHealth;

                    if (fullyCorrupted) {
                        // Feel free to swap this if you end up making a corrupted_vehicle_full!
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CORRUPTED_FULL, x, y, 9, 9);
                    } else if (halfCorrupted) {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CORRUPTED_VEHICLE_HALF, x, y, 9, 9);
                    }
                }
                currentHeartIndex++;
            }
            y -= 10;
        }
    }
}