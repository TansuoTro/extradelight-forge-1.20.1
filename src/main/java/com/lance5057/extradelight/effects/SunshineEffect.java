package com.lance5057.extradelight.effects;

import com.lance5057.extradelight.ExtraDelightMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class SunshineEffect extends MobEffect {
	public SunshineEffect() {
		super(MobEffectCategory.BENEFICIAL, 0xffff00);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		amplifier = breakdownEffect(livingEntity, amplifier, MobEffects.BLINDNESS);
		breakdownEffect(livingEntity, amplifier, MobEffects.DARKNESS);
	}

	private int breakdownEffect(LivingEntity livingEntity, int amplifier, MobEffect effect) {
		if (amplifier >= 0 && livingEntity.hasEffect(effect)) {
			MobEffectInstance current = livingEntity.getEffect(effect);
			int reducedAmplifier = current.getAmplifier() - 1;
			livingEntity.removeEffect(effect);
			if (reducedAmplifier > -1) {
				livingEntity.addEffect(new MobEffectInstance(effect, current.getDuration(), reducedAmplifier));
			}

			amplifier--;
			MobEffectInstance sunshine = livingEntity.getEffect(ExtraDelightMobEffects.SUNSHINE.get());
			if (sunshine != null) {
				int duration = sunshine.getDuration();
				livingEntity.removeEffect(ExtraDelightMobEffects.SUNSHINE.get());
				if (amplifier >= 0) {
					livingEntity.addEffect(new MobEffectInstance(ExtraDelightMobEffects.SUNSHINE.get(), duration, amplifier));
				}
			}
		}

		return amplifier;
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
