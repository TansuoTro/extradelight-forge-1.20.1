package com.lance5057.extradelight.workstations.juicer;

import com.google.gson.JsonObject;
import com.lance5057.extradelight.ExtraDelightBlocks;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.lance5057.extradelight.util.StackUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class JuicerRecipe implements Recipe<Container> {
	private final ResourceLocation id;
	private final String group;
	private final Ingredient ingredient;
	private final ItemStack result;
	private final int percentChance;
	private final FluidStack fluidOut;

	public JuicerRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, int percentChance,
			FluidStack fluidOut) {
		this.id = id;
		this.group = group;
		this.ingredient = ingredient;
		this.result = result;
		this.percentChance = percentChance;
		this.fluidOut = fluidOut;
	}

	@Override
	public boolean matches(Container input, Level level) {
		return this.ingredient.test(input.getItem(0));
	}

	public boolean matches(ItemStack stack) {
		return this.ingredient.test(stack);
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return this.result.copy();
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ExtraDelightRecipes.JUICER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ExtraDelightRecipes.JUICER.get();
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	public Ingredient getInput() {
		return this.ingredient;
	}

	public int getChance() {
		return this.percentChance;
	}

	public FluidStack getFluid() {
		return this.fluidOut;
	}

	public ItemStack getToastSymbol() {
		return new ItemStack(ExtraDelightBlocks.JUICER.get());
	}

	public static class Serializer implements RecipeSerializer<JuicerRecipe> {
		@Override
		public JuicerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String group = GsonHelper.getAsString(json, "group", "");
			JsonObject fluidJson = GsonHelper.getAsJsonObject(json, "fluidOut");
			if (fluidJson.has("id") && !fluidJson.has("fluid")) {
				fluidJson.addProperty("fluid", GsonHelper.getAsString(fluidJson, "id"));
			}
			FluidStack fluidOut = StackUtil.FluidStackfromJson(fluidJson);
			int chance = GsonHelper.getAsInt(json, "chance", 100);
			Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
			JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
			if (resultJson.has("id") && !resultJson.has("item")) {
				resultJson.addProperty("item", GsonHelper.getAsString(resultJson, "id"));
			}
			ItemStack result = StackUtil.ItemStackfromJson(resultJson);
			return new JuicerRecipe(recipeId, group, ingredient, result, chance, fluidOut);
		}

		@Override
		public @Nullable JuicerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			String group = buffer.readUtf();
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			int chance = buffer.readInt();
			FluidStack fluidOut = buffer.readFluidStack();
			return new JuicerRecipe(recipeId, group, ingredient, result, chance, fluidOut);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, JuicerRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeInt(recipe.percentChance);
			buffer.writeFluidStack(recipe.fluidOut);
		}
	}
}
