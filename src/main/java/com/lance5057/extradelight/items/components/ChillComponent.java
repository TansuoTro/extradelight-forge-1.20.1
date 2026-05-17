package com.lance5057.extradelight.items.components;

import com.lance5057.extradelight.ExtraDelight;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;

public record ChillComponent(int time) {


	public static final Codec<ChillComponent> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.INT.fieldOf("chill").forGetter(ChillComponent::time))
			.apply(instance, ChillComponent::new));

	public void writeToBuffer(FriendlyByteBuf buffer) {
		buffer.writeInt(time);
	}

	public static ChillComponent readFromBuffer(FriendlyByteBuf buffer) {
		return new ChillComponent(buffer.readInt());
	}

//	public void addToTooltip(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
//		tooltip.add(Component.translatable(ExtraDelight.MOD_ID + ".tooltip.chill", this.time)
//				.withStyle(ChatFormatting.AQUA));
//	}
}