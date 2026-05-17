package com.lance5057.extradelight.worldgen.features.trees;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class ExtraDelightTreeGrowers {
	public static final AbstractTreeGrower CINNAMON = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.CINNAMON;
		}
	};

	public static final AbstractTreeGrower HAZELNUT = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.HAZELNUT;
		}
	};

	public static final AbstractTreeGrower APPLE = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.APPLE;
		}
	};

	public static final AbstractTreeGrower LEMON = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.LEMON;
		}
	};

	public static final AbstractTreeGrower LIME = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.LIME;
		}
	};

	public static final AbstractTreeGrower ORANGE = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.ORANGE;
		}
	};

	public static final AbstractTreeGrower GRAPEFRUIT = new AbstractTreeGrower() {
		@Nullable
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return ExtraDelightTreeFeatures.GRAPEFRUIT;
		}
	};
}
