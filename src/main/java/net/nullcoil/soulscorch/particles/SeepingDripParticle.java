package net.nullcoil.soulscorch.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class SeepingDripParticle extends SingleQuadParticle {

    protected boolean isGlowing = true;

    // Soul fire blue
    private static final float R = 0x2E / 255f;
    private static final float G = 0xD5 / 255f;
    private static final float B = 0xDA / 255f;

    protected SeepingDripParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z, sprite);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.setColor(R, G, B);
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Override
    public int getLightColor(float f) {
        return this.isGlowing ? 240 : super.getLightColor(f);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
            return;
        }
        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98F;
        this.yd *= 0.98F;
        this.zd *= 0.98F;
    }

    @Environment(EnvType.CLIENT)
    static class HangParticle extends SeepingDripParticle {
        private final ParticleOptions fallingParticle;

        HangParticle(ClientLevel level, double x, double y, double z,
                     ParticleOptions fallingParticle, TextureAtlasSprite sprite) {
            super(level, x, y, z, sprite);
            this.fallingParticle = fallingParticle;
            this.gravity *= 0.02F;
            this.lifetime = 100;
        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
                return;
            }
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }
    }

    @Environment(EnvType.CLIENT)
    static class FallParticle extends SeepingDripParticle {
        private final ParticleOptions landParticle;

        FallParticle(ClientLevel level, double x, double y, double z,
                     ParticleOptions landParticle, TextureAtlasSprite sprite) {
            super(level, x, y, z, sprite);
            this.landParticle = landParticle;
            this.lifetime = (int)(64.0F / (this.random.nextFloat() * 0.8F + 0.2F));
        }

        @Override
        public void tick() {
            super.tick();
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0, 0, 0);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class LandParticle extends SeepingDripParticle {
        LandParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
            super(level, x, y, z, sprite);
            this.lifetime = (int)(28.0F / (this.random.nextFloat() * 0.8F + 0.2F));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class HangProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public HangProvider(SpriteSet sprites) { this.sprites = sprites; }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz, RandomSource rng) {
            return new HangParticle(level, x, y, z,
                    ModParticles.SEEPING_DRIP_FALL, this.sprites.get(rng));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class FallProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public FallProvider(SpriteSet sprites) { this.sprites = sprites; }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz, RandomSource rng) {
            return new FallParticle(level, x, y, z,
                    ModParticles.SEEPING_DRIP_LAND, this.sprites.get(rng));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class LandProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public LandProvider(SpriteSet sprites) { this.sprites = sprites; }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz, RandomSource rng) {
            return new LandParticle(level, x, y, z, this.sprites.get(rng));
        }
    }
}