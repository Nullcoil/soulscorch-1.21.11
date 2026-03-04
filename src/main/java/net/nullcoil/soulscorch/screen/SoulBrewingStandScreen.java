package net.nullcoil.soulscorch.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.nullcoil.soulscorch.Soulscorch;

@Environment(EnvType.CLIENT)
public class SoulBrewingStandScreen extends AbstractContainerScreen<SoulBrewingStandScreenHandler> {
    private static final Identifier FUEL_LENGTH_SPRITE = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID,"container/brewing_stand/soul_fuel_length");
    private static final Identifier BREW_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID,"container/brewing_stand/brew_progress");
    private static final Identifier BUBBLES_SPRITE = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID,"container/brewing_stand/bubbles");
    private static final Identifier BREWING_STAND_LOCATION = Identifier.fromNamespaceAndPath(Soulscorch.MOD_ID,"textures/gui/container/soul_brewing_stand.png");
    private static final int[] BUBBLELENGTHS = new int[]{29, 24, 20, 16, 11, 6, 0};

    // Changed the first parameter to your custom ScreenHandler
    public SoulBrewingStandScreen(SoulBrewingStandScreenHandler brewingStandMenu, Inventory inventory, Component component) {
        super(brewingStandMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BREWING_STAND_LOCATION, k, l, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        // Removed the cast to BrewingStandMenu, 'this.menu' is inherently your SoulBrewingStandScreenHandler now
        int m = this.menu.getFuel();
        int n = Mth.clamp((18 * m + 20 - 1) / 20, 0, 18);
        if (n > 0) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, FUEL_LENGTH_SPRITE, 18, 4, 0, 0, k + 60, l + 44, n, 4);
        }

        // Removed the cast here too
        int o = this.menu.getBrewingTicks();
        if (o > 0) {
            int p = (int)(28.0F * (1.0F - (float)o / 400.0F));
            if (p > 0) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BREW_PROGRESS_SPRITE, 9, 28, 0, 0, k + 97, l + 16, 9, p);
            }

            p = BUBBLELENGTHS[o / 2 % 7];
            if (p > 0) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUBBLES_SPRITE, 12, 29, 0, 29 - p, k + 63, l + 14 + 29 - p, 12, p);
            }
        }
    }
}