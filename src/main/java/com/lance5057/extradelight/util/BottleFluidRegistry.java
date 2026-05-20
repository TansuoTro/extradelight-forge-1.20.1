package com.lance5057.extradelight.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.lance5057.extradelight.ExtraDelightFluids;
import com.lance5057.extradelight.ExtraDelightItems;
import com.lance5057.extradelight.data.recipebuilders.BottleFluidRegistryRecipeBuilder;
import com.lance5057.extradelight.modules.Fermentation;
import com.lance5057.extradelight.modules.SummerCitrus;



import com.lance5057.extradelight.util.FluidIngredientCompat;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;
//import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;

public class BottleFluidRegistry {
	private static int bottleMB = 250;

	public static List<BottleFluid> registry = new ArrayList<BottleFluid>();

	public static void register(Ingredient bottle, FluidIngredientCompat fluid) {
		registry.add(new BottleFluid(bottle, fluid));
	}

	public static ItemStack getBottleFromFluid(FluidStack f) {
		if (f.getAmount() >= bottleMB) {
			Optional<BottleFluid> b = registry.stream().filter(bf -> bf.fluid.test(f)).findFirst();
			if (b.isPresent()) {
				return b.get().bottle.getItems()[0].copy();
			}
		}
		return ItemStack.EMPTY;
	}

	public static FluidStack getFluidFromBottle(ItemStack i) {
		Optional<BottleFluid> b = registry.stream().filter(bf -> bf.bottle.test(i)).findFirst();
		if (b.isPresent()) {
			return b.get().fluid.getMatchingFluidStacks().get(0);
		}
		return FluidStack.EMPTY;
	}

	public static void createRecipesForJEI(Consumer<FinishedRecipe> consumer) {
		registry.forEach(bf -> {
			new BottleFluidRegistryRecipeBuilder(bf.bottle, bf.fluid).save(consumer, bf.fluid.getMatchingFluidStacks().get(0).getTranslationKey());
		});
	}

	public static class BottleFluid {
		public Ingredient bottle;
		public FluidIngredientCompat fluid;

		public BottleFluid(Ingredient b, FluidIngredientCompat f) {
			this.bottle = b;
			this.fluid = f;
		}

	}

	static {
				
		register(Ingredient.of(ModItems.APPLE_CIDER.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.APPLE_CIDER.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.BBQ_SAUCE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.BBQ.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.BLOOD_CHOCOLATE_SYRUP.FLUID.get(), bottleMB));
		register(Ingredient.of(ModItems.BONE_BROTH.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.BROTH.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.CACTUS_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.CACTUS_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.CARAMEL_SAUCE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.CARAMEL_SAUCE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.COCOA_BUTTER_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.COCOA_BUTTER.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.COFFEE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.COFFEE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.COOKING_OIL.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.OIL.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.DARK_CHOCOLATE_SYRUP_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.DARK_CHOCOLATE_SYRUP.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.EGG_MIX.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.EGG_MIX.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.GLOW_BERRY_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.GLOW_BERRY_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.GLOW_BERRY_JAM.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.GLOW_JAM.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.GOLDEN_APPLE_JAM.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.GOLDEN_JAM.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.GRAVY.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.GRAVY.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.HAZELNUT_SPREAD_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.COCOA_NUT_BUTTER_SPREAD.FLUID.get(), bottleMB));
		register(Ingredient.of(ModItems.HOT_COCOA.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.HOT_COCOA.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.JAM.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.JAM.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.KETCHUP.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.KETCHUP.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.MARSHMALLOW_FLUFF_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.MARSHMALLOW_FLUFF.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.MAYO.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.MAYO.FLUID.get(), bottleMB));
		register(Ingredient.of(ModItems.MELON_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.MELON_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(ModItems.MILK_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ForgeMod.MILK.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.MILK_CHOCOLATE_SYRUP_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.MILK_CHOCOLATE_SYRUP.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.MILKSHAKE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.MILKSHAKE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.PEANUT_BUTTER_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.NUT_BUTTER.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.SWEET_BERRY_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.SWEET_BERRY_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(SummerCitrus.LEMON_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.LEMON_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(SummerCitrus.LIME_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.LIME_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(SummerCitrus.ORANGE_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.ORANGE_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(SummerCitrus.GRAPEFRUIT_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.GRAPEFRUIT_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.TEA.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.TEA.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.TOMATO_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.TOMATO_JUICE.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.VINEGAR.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.VINEGAR.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.WHIPPED_CREAM.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.WHIPPED_CREAM.FLUID.get(), bottleMB));
		register(Ingredient.of(ExtraDelightItems.WHITE_CHOCOLATE_SYRUP_BOTTLE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.WHITE_CHOCOLATE_SYRUP.FLUID.get(), bottleMB));
		register(Ingredient.of(Fermentation.PICKLE_JUICE.get()),
				FluidIngredientCompat.fromFluid(ExtraDelightFluids.PICKLE_JUICE.FLUID.get(), bottleMB));

		// If we just use Items.POTION we get an item called Uncraftable Potion instead of Water Bottle
		register(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION),Potions.WATER)),FluidIngredientCompat.fromFluid(Fluids.WATER, bottleMB));
				//.createItemStack(Items.POTION, Potions.WATER)),

	}
}
