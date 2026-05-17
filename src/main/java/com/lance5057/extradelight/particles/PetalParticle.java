package com.lance5057.extradelight.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PetalParticle extends TextureSheetParticle {
	private static final float ACCELERATION_SCALE = 0.0025F;
	private static final int INITIAL_LIFETIME = 300;
	private static final int CURVE_ENDPOINT_TIME = 300;
	private static final float WIND_BIG = 2.0F;
	private float rotSpeed;
	private final float particleRandom;
	private final float spinAcceleration;

	protected PetalParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
		super(level, x, y, z);
		this.setSprite(spriteSet.get(this.random.nextInt(4), 4));
		this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
		this.particleRandom = this.random.nextFloat();
		this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
		this.lifetime = INITIAL_LIFETIME;
		this.gravity = 7.5E-4F;
		float size = this.random.nextBoolean() ? 0.075F : 0.1F;
		this.quadSize = size;
		this.setSize(size, size);
		this.friction = 1.0F;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.lifetime-- <= 0) {
			this.remove();
		}

		if (!this.removed) {
			float time = CURVE_ENDPOINT_TIME - this.lifetime;
			float progress = Math.min(time / CURVE_ENDPOINT_TIME, 1.0F);
			double xWind = Math.cos(Math.toRadians(this.particleRandom * 60.0F)) * WIND_BIG * Math.pow(progress, 1.25);
			double zWind = Math.sin(Math.toRadians(this.particleRandom * 60.0F)) * WIND_BIG * Math.pow(progress, 1.25);
			this.xd += xWind * ACCELERATION_SCALE;
			this.zd += zWind * ACCELERATION_SCALE;
			this.yd -= this.gravity;
			this.rotSpeed += this.spinAcceleration / 20.0F;
			this.oRoll = this.roll;
			this.roll += this.rotSpeed / 20.0F;
			this.move(this.xd, this.yd, this.zd);
			if (this.onGround || this.lifetime < INITIAL_LIFETIME - 1 && (this.xd == 0.0 || this.zd == 0.0)) {
				this.remove();
			}

			if (!this.removed) {
				this.xd *= this.friction;
				this.yd *= this.friction;
				this.zd *= this.friction;
			}
		}
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			PetalParticle particle = new PetalParticle(level, x, y + 0.3D, z, spriteSet);
			particle.pickSprite(this.spriteSet);
			particle.setColor(1.0F, 1.0F, 1.0F);
			return particle;
		}
	}
}
