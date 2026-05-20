package com.lance5057.extradelight.integration.emi;

import java.util.ArrayList;
import java.util.List;

import com.lance5057.extradelight.ExtraDelight;
import com.lance5057.extradelight.ExtraDelightItems;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.lance5057.extradelight.ExtraDelightTags;
import com.lance5057.extradelight.modules.Fermentation;
import com.lance5057.extradelight.recipe.BottleFluidRegistryRecipe;
import com.lance5057.extradelight.recipe.FeastRecipe;
import com.lance5057.extradelight.recipe.ShapedWithJarRecipe;
import com.lance5057.extradelight.recipe.ToolOnBlockRecipe;
import com.lance5057.extradelight.util.BottleFluidRegistry;
import com.lance5057.extradelight.workstations.chiller.ChillerRecipe;
import com.lance5057.extradelight.workstations.doughshaping.recipes.DoughShapingRecipe;
import com.lance5057.extradelight.workstations.dryingrack.DryingRackRecipe;
import com.lance5057.extradelight.workstations.evaporator.recipes.EvaporatorRecipe;
import com.lance5057.extradelight.workstations.meltingpot.MeltingPotRecipe;
import com.lance5057.extradelight.workstations.mixingbowl.recipes.MixingBowlRecipe;
import com.lance5057.extradelight.workstations.mortar.recipes.MortarRecipe;
import com.lance5057.extradelight.workstations.oven.recipes.OvenRecipe;
import com.lance5057.extradelight.workstations.vat.recipes.VatRecipe;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

@EmiEntrypoint
public class ExtraDelightEmiPlugin implements EmiPlugin {

	private static final ResourceLocation JEI_TEX = ResourceLocation.fromNamespaceAndPath(ExtraDelight.MOD_ID,
			"textures/gui/jei.png");
	private static final ResourceLocation JEI3_TEX = ResourceLocation.fromNamespaceAndPath(ExtraDelight.MOD_ID,
			"textures/gui/jei3.png");

	private static EmiRecipeCategory cat(String name, Object icon) {
		if (icon instanceof net.minecraft.world.level.ItemLike i)
			return new EmiRecipeCategory(id(name), EmiStack.of(i), EmiStack.of(i), EmiRecipeSorting.compareOutputThenInput());
		return new EmiRecipeCategory(id(name), EmiStack.of(Items.GLASS_BOTTLE), EmiStack.of(Items.GLASS_BOTTLE), EmiRecipeSorting.compareOutputThenInput());
	}

	private static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ExtraDelight.MOD_ID, path);
	}

	public static final EmiRecipeCategory OVEN = cat("oven", ExtraDelightItems.OVEN.get());
	public static final EmiRecipeCategory MIXING_BOWL = cat("mixingbowl", ExtraDelightItems.MIXING_BOWL.get());
	public static final EmiRecipeCategory MORTAR = cat("mortar", ExtraDelightItems.MORTAR_STONE.get());
	public static final EmiRecipeCategory DRYING_RACK = cat("dryingrack", ExtraDelightItems.DRYING_RACK.get());
	public static final EmiRecipeCategory DOUGH_SHAPING = cat("doughshaping", ExtraDelightItems.DOUGH_SHAPING.get());
	public static final EmiRecipeCategory FEAST = cat("feast", ExtraDelightItems.CURRY_FEAST.get());
	public static final EmiRecipeCategory TOOL_ON_BLOCK = cat("tool_on_block", ExtraDelightItems.GINGERBREAD_COOKIE_BLOCK.get());
	public static final EmiRecipeCategory MELTING_POT = cat("meltingpot", ExtraDelightItems.MELTING_POT.get());
	public static final EmiRecipeCategory CHILLER = cat("chiller", ExtraDelightItems.CHILLER.get());
	public static final EmiRecipeCategory VAT = cat("vat", ExtraDelightItems.VAT.get());
	public static final EmiRecipeCategory EVAPORATOR = cat("evaporator", ExtraDelightItems.EVAPORATOR.get());
	public static final EmiRecipeCategory SHAPED_WITH_JAR = cat("shaped_with_jar", ExtraDelightItems.JAR.get());
	public static final EmiRecipeCategory BOTTLE_FLUID_REGISTRY = new EmiRecipeCategory(
			id("bottle_registry"), EmiStack.of(Items.GLASS_BOTTLE), EmiStack.of(Items.GLASS_BOTTLE),
			EmiRecipeSorting.none());

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void register(EmiRegistry registry) {
		RecipeManager rm = registry.getRecipeManager();

		add(registry, OVEN, ExtraDelightItems.OVEN.get());
		add(registry, MIXING_BOWL, ExtraDelightItems.MIXING_BOWL.get());
		add(registry, MORTAR, ExtraDelightItems.MORTAR_STONE.get());
		for (ItemStack p : new ItemStack[] { new ItemStack(ExtraDelightItems.PESTLE_AMETHYST.get()),
				new ItemStack(ExtraDelightItems.PESTLE_ANDESITE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_BASALT.get()),
				new ItemStack(ExtraDelightItems.PESTLE_BLACKSTONE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_DEEPSLATE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_DIORITE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_ENDSTONE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_GILDED_BLACKSTONE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_GRANITE.get()),
				new ItemStack(ExtraDelightItems.PESTLE_STONE.get()) }) {
			ws(registry, MORTAR, p);
		}
		add(registry, DRYING_RACK, ExtraDelightItems.DRYING_RACK.get());
		add(registry, DOUGH_SHAPING, ExtraDelightItems.DOUGH_SHAPING.get());
		add(registry, FEAST, ExtraDelightItems.CURRY_FEAST.get());
		add(registry, TOOL_ON_BLOCK, ExtraDelightItems.GINGERBREAD_COOKIE_BLOCK.get());
		add(registry, MELTING_POT, ExtraDelightItems.MELTING_POT.get());
		add(registry, CHILLER, ExtraDelightItems.CHILLER.get());
		add(registry, VAT, ExtraDelightItems.VAT.get());
		ws(registry, VAT, ExtraDelightItems.LID.get());
		add(registry, EVAPORATOR, ExtraDelightItems.EVAPORATOR.get());
		add(registry, SHAPED_WITH_JAR, ExtraDelightItems.JAR.get());
		add(registry, BOTTLE_FLUID_REGISTRY, Items.GLASS_BOTTLE);

		List list;
		list = rm.getAllRecipesFor(ExtraDelightRecipes.OVEN.get());
		for (Object r : list) registry.addRecipe(new OvenEmiRecipe((OvenRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.MIXING_BOWL.get());
		for (Object r : list) registry.addRecipe(new MixingBowlEmiRecipe((MixingBowlRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.MORTAR.get());
		for (Object r : list) registry.addRecipe(new MortarEmiRecipe((MortarRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.DRYING_RACK.get());
		for (Object r : list) registry.addRecipe(new DryingRackEmiRecipe((DryingRackRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.DOUGH_SHAPING.get());
		for (Object r : list) registry.addRecipe(new DoughShapingEmiRecipe((DoughShapingRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.FEAST.get());
		for (Object r : list) registry.addRecipe(new FeastEmiRecipe((FeastRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.TOOL_ON_BLOCK.get());
		for (Object r : list) registry.addRecipe(new ToolOnBlockEmiRecipe((ToolOnBlockRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.MELTING_POT.get());
		for (Object r : list) registry.addRecipe(new MeltingPotEmiRecipe((MeltingPotRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.CHILLER.get());
		for (Object r : list) registry.addRecipe(new ChillerEmiRecipe((ChillerRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.VAT.get());
		for (Object r : list) registry.addRecipe(new VatEmiRecipe((VatRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.EVAPORATOR.get());
		for (Object r : list) registry.addRecipe(new EvaporatorEmiRecipe((EvaporatorRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.SHAPED_JAR.get());
		for (Object r : list) registry.addRecipe(new ShapedWithJarEmiRecipe((ShapedWithJarRecipe) r));
		list = rm.getAllRecipesFor(ExtraDelightRecipes.BOTTLE_FLUID_REGISTRY.get());
		for (Object r : list) registry.addRecipe(new BottleFluidRegistryEmiRecipe((BottleFluidRegistryRecipe) r));

		info(registry, "mint", ExtraDelightItems.MINT.get());
		info(registry, "cinnamon", ExtraDelightItems.CINNAMON_SAPLING.get(), ExtraDelightItems.CINNAMON_LOG.get(),
				ExtraDelightItems.CINNAMON_BARK.get());
		info(registry, "ginger", ExtraDelightItems.WILD_GINGER.get(), ExtraDelightItems.GINGER.get());
		info(registry, "corn", ExtraDelightItems.CORN_SEEDS.get(), ExtraDelightItems.UNSHUCKED_CORN.get());
		info(registry, "chili", ExtraDelightItems.WILD_CHILI_BLOCK.get(), ExtraDelightItems.CHILI.get(),
				ExtraDelightItems.CHILI_SEEDS.get());
		info(registry, "mallow", ExtraDelightItems.WILD_MALLOW_ROOT_BLOCK.get(), ExtraDelightItems.MALLOW_ROOT.get());
		info(registry, "peanut", ExtraDelightItems.WILD_PEANUT_BLOCK.get(), ExtraDelightItems.PEANUTS_IN_SHELL.get());
		info(registry, "coffee", ExtraDelightItems.COFFEE_CHERRIES.get());
		info(registry, "hazelnut", ExtraDelightItems.HAZELNUT_SAPLING.get(), ExtraDelightItems.HAZELNUTS_IN_SHELL.get(),
				ExtraDelightItems.HAZELNUT_LEAVES.get());
		info(registry, "apple", ExtraDelightItems.APPLE_SAPLING.get(), Items.APPLE,
				ExtraDelightItems.APPLE_LEAVES.get());
		info(registry, "garlic", ExtraDelightItems.WILD_GARLIC_BLOCK.get(), ExtraDelightItems.GARLIC.get());
		info(registry, "cucumber", Fermentation.WILD_CUCUMBER.get(), Fermentation.CUCUMBER.get(),
				Fermentation.CUCUMBER_SEED.get());
		info(registry, "soybean", Fermentation.WILD_SOYBEAN.get(), Fermentation.SOYBEAN_POD.get());
		info(registry, "pickle_juice", Fermentation.PICKLE_JUICE.get());
		info(registry, "yeast", ExtraDelightItems.YEAST.get(), ExtraDelightItems.YEAST_POT.get());
	}

	private static void ws(EmiRegistry registry, EmiRecipeCategory cat, Object item) {
		if (item instanceof ItemStack s)
			registry.addWorkstation(cat, EmiStack.of(s));
		else if (item instanceof net.minecraft.world.level.ItemLike i)
			registry.addWorkstation(cat, EmiStack.of(i));
	}

	private static void add(EmiRegistry registry, EmiRecipeCategory cat, Object item) {
		registry.addCategory(cat);
		ws(registry, cat, item);
	}

	private static void info(EmiRegistry registry, String key, Object... items) {
		List<EmiIngredient> stacks = new ArrayList<>();
		for (Object obj : items) {
			if (obj instanceof net.minecraft.world.level.ItemLike i)
				stacks.add(EmiStack.of(i));
		}
		List<Component> text = new ArrayList<>();
		text.add(Component.translatable(ExtraDelight.MOD_ID + ".jei.info." + key));
		registry.addRecipe(new EmiInfoRecipe(stacks, text, id("/info/" + key)));
	}

	// ========================================================================
	// Base recipe class
	// ========================================================================

	private abstract static class BaseEmiRecipe implements EmiRecipe {
		protected final EmiRecipeCategory category;
		protected final ResourceLocation id;
		protected final List<EmiIngredient> inputs = new ArrayList<>();
		protected final List<EmiIngredient> catalysts = new ArrayList<>();
		protected final List<EmiStack> outputs = new ArrayList<>();
		protected final int width, height;

		BaseEmiRecipe(EmiRecipeCategory category, ResourceLocation id, int width, int height) {
			this.category = category;
			this.id = id;
			this.width = width;
			this.height = height;
		}

		@Override
		public EmiRecipeCategory getCategory() {
			return category;
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public List<EmiIngredient> getInputs() {
			return inputs;
		}

		@Override
		public List<EmiIngredient> getCatalysts() {
			return catalysts;
		}

		@Override
		public List<EmiStack> getOutputs() {
			return outputs;
		}

		@Override
		public int getDisplayWidth() {
			return width;
		}

		@Override
		public int getDisplayHeight() {
			return height;
		}
	}

	// ========================================================================
	// Oven
	// ========================================================================

	private static class OvenEmiRecipe extends BaseEmiRecipe {
		OvenEmiRecipe(OvenRecipe recipe) {
			super(OVEN, recipe.getId(), 121, 72);
			recipe.getIngredients().forEach(ing -> inputs.add(EmiIngredient.of(ing)));
			ItemStack pan = recipe.getOutputContainer();
			if (!pan.isEmpty())
				catalysts.add(EmiStack.of(pan));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 121, 72, 0, 53);
			for (int i = 0; i < inputs.size(); i++)
				w.addSlot(inputs.get(i), 1 + (i % 3 * 18), 1 + (i / 3 * 18));
			if (!catalysts.isEmpty())
				w.addSlot(catalysts.get(0), 121 / 2 + 3, 47);
			w.addSlot(outputs.get(0), 121 / 2 + 35, 20).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Mixing Bowl
	// ========================================================================

	private static class MixingBowlEmiRecipe extends BaseEmiRecipe {
		final int stirs;
		final ItemStack usedItem;
		final List<FluidStack> fluids;

		MixingBowlEmiRecipe(MixingBowlRecipe recipe) {
			super(MIXING_BOWL, recipe.getId(), 147, 74);
			recipe.getIngredients().forEach(ing -> inputs.add(EmiIngredient.of(ing)));
			catalysts.add(EmiIngredient.of(Ingredient.of(ExtraDelightTags.SPOONS)));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
			this.stirs = recipe.getStirs();
			this.usedItem = recipe.getUsedItem().copy();
			this.fluids = recipe.getFluids();
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 147, 74, 109, 182);
			int x = 0, y = 0;
			for (EmiIngredient ing : inputs) {
				w.addSlot(ing, x * 18 + 47, y * 18 + 11);
				if (++x >= 3) { x = 0; y++; }
			}
			if (!catalysts.isEmpty())
				w.addSlot(catalysts.get(0), 147 / 2 + 28, 12);
			if (!usedItem.isEmpty())
				w.addSlot(EmiStack.of(usedItem), 147 / 2 + 32, 52);
			w.addSlot(outputs.get(0), 147 / 2 + 57, 29).large(true).recipeContext(this);
			for (int i = 0; i < fluids.size(); i++) {
				FluidStack fs = fluids.get(i);
				w.addTank(EmiStack.of(fs.getFluid(), fs.getAmount()), 147 / 2 - 49, 61 - (i * 12), 16, 12,
						(int) fs.getAmount());
			}
			if (!fluids.isEmpty()) {
				FluidStack fs = fluids.get(0);
				w.addSlot(EmiIngredient.of(Ingredient.of(BottleFluidRegistry.getBottleFromFluid(fs),
						new ItemStack(fs.getFluid().getBucket()))), 1, 1);
			}
			w.addText(Component.literal("x" + stirs), 147 / 2 + 44, 20, 0xffffff, true);
		}
	}

	// ========================================================================
	// Mortar
	// ========================================================================

	private static class MortarEmiRecipe extends BaseEmiRecipe {
		final int grinds;
		final FluidStack fluidOut;

		MortarEmiRecipe(MortarRecipe recipe) {
			super(MORTAR, recipe.getId(), 84, 52);
			inputs.add(EmiIngredient.of(recipe.getIngredient()));
			catalysts.add(EmiIngredient.of(Ingredient.of(ExtraDelightTags.PESTLES)));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
			this.grinds = recipe.getGrinds();
			this.fluidOut = recipe.getFluidOut();
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 84, 52, 0, 0);
			w.addSlot(inputs.get(0), 84 / 2 - 39 + 6, 18);
			w.addSlot(catalysts.get(0), 84 / 2 - 39 + 26, 1);
			w.addSlot(outputs.get(0), 84 / 2 + 13 + 6, 26).large(true).recipeContext(this);
			if (fluidOut != null && !fluidOut.isEmpty())
				w.addTank(EmiStack.of(fluidOut.getFluid(), fluidOut.getAmount()), 84 / 2 + 13 + 6, 8, 16, 16,
						(int) fluidOut.getAmount());
			w.addText(Component.literal("x" + grinds), 84 / 2 - 24 + 24, 8, 0xffffff, true);
		}
	}

	// ========================================================================
	// Drying Rack
	// ========================================================================

	private static class DryingRackEmiRecipe extends BaseEmiRecipe {
		DryingRackEmiRecipe(DryingRackRecipe recipe) {
			super(DRYING_RACK, recipe.getId(), 85, 47);
			inputs.add(EmiIngredient.of(recipe.getInput()));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 85, 47, 0, 125);
			w.addSlot(inputs.get(0), 85 / 2 - 38, 22);
			w.addSlot(outputs.get(0), 85 / 2 + 13 + 9, 22).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Dough Shaping
	// ========================================================================

	private static class DoughShapingEmiRecipe extends BaseEmiRecipe {
		DoughShapingEmiRecipe(DoughShapingRecipe recipe) {
			super(DOUGH_SHAPING, recipe.getId(), 78, 18);
			inputs.add(EmiIngredient.of(recipe.getIngredients().get(0)));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 78, 18, 0, 238);
			w.addSlot(inputs.get(0), 78 / 2 - 38, 1);
			w.addSlot(outputs.get(0), 78 / 2 + 13 + 9, 1).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Feast
	// ========================================================================

	private static class FeastEmiRecipe extends BaseEmiRecipe {
		FeastEmiRecipe(FeastRecipe recipe) {
			super(FEAST, recipe.getId(), 64, 18);
			inputs.add(EmiIngredient.of(recipe.getContainer()));
			catalysts.add(EmiStack.of(recipe.getFeastStack()));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 64, 18, 0, 220);
			w.addSlot(inputs.get(0), 64 / 2 - 31, 1);
			w.addSlot(catalysts.get(0), 64 / 2 - 8, 1);
			w.addSlot(outputs.get(0), 64 / 2 + 15, 1).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Tool On Block
	// ========================================================================

	private static class ToolOnBlockEmiRecipe extends BaseEmiRecipe {
		ToolOnBlockEmiRecipe(ToolOnBlockRecipe recipe) {
			super(TOOL_ON_BLOCK, recipe.getId(), 64, 18);
			inputs.add(EmiStack.of(new ItemStack(recipe.getIn())));
			catalysts.add(EmiIngredient.of(recipe.getTool()));
			outputs.add(EmiStack.of(new ItemStack(recipe.getOut())));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 64, 18, 0, 220);
			w.addSlot(inputs.get(0), 64 / 2 - 8, 1);
			w.addSlot(catalysts.get(0), 64 / 2 - 38, 1);
			w.addSlot(outputs.get(0), 64 / 2 + 15, 1).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Melting Pot
	// ========================================================================

	private static class MeltingPotEmiRecipe extends BaseEmiRecipe {
		MeltingPotEmiRecipe(MeltingPotRecipe recipe) {
			super(MELTING_POT, recipe.getId(), 52, 42);
			inputs.add(EmiIngredient.of(recipe.input));
			outputs.add(EmiStack.of(recipe.result.getFluid(), recipe.result.getAmount()));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 52, 42, 204, 74);
			w.addSlot(inputs.get(0), 6, 7);
			w.addTank(outputs.get(0), 52 / 2 + 3, 7, 16, 16, (int) outputs.get(0).getAmount());
		}
	}

	// ========================================================================
	// Chiller
	// ========================================================================

	private static class ChillerEmiRecipe extends BaseEmiRecipe {
		final ChillerRecipe recipe;

		ChillerEmiRecipe(ChillerRecipe recipe) {
			super(CHILLER, recipe.getId(), 124, 73);
			for (Ingredient ing : recipe.getIngredients())
				inputs.add(EmiIngredient.of(ing));
			if (!recipe.getOutputContainer().isEmpty())
				inputs.add(EmiStack.of(recipe.getOutputContainer()));
			catalysts.add(EmiIngredient.of(Ingredient.of(Items.ICE, Items.SNOWBALL, Items.BLUE_ICE, Items.PACKED_ICE)));
			outputs.add(EmiStack.of(recipe.output));
			this.recipe = recipe;
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 124, 73, 132, 0);
			FluidStack fluid = recipe.getFluid();
			// Render item inputs
			int inputIdx = 0;
			for (int i = 0; i < recipe.getIngredients().size(); i++)
				w.addSlot(inputs.get(inputIdx++), 45 + (i % 2 * 18), 20 + (i / 2 * 18));
			// Container
			if (!recipe.getOutputContainer().isEmpty())
				w.addSlot(inputs.get(inputIdx++), 54, 56);
			// Fluid tank
			w.addTank(EmiStack.of(fluid.getFluid(), fluid.getAmount()), 124 / 2 - 38, 1, 16, 71,
					(int) fluid.getAmount());
			// Fluid bottle + bucket
			w.addSlot(EmiIngredient.of(Ingredient.of(BottleFluidRegistry.getBottleFromFluid(fluid),
					new ItemStack(fluid.getFluid().getBucket()))), 1, 1);
			// Ice catalyst
			if (!catalysts.isEmpty())
				w.addSlot(catalysts.get(0), 107, 10);
			// Output
			w.addSlot(outputs.get(0), 107, 30).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Vat
	// ========================================================================

	private static class VatEmiRecipe extends BaseEmiRecipe {
		final VatRecipe recipe;
		final int stages;

		VatEmiRecipe(VatRecipe recipe) {
			super(VAT, recipe.getId(), 101, 47 + (32 * recipe.getStages()));
			recipe.getIngredients().forEach(ing -> inputs.add(EmiIngredient.of(ing)));
			catalysts.add(EmiStack.of(recipe.getUsedItem()));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
			this.recipe = recipe;
			this.stages = recipe.getStages();
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI3_TEX, 0, 0, 101, 47, 0, 0);
			for (int i = 0; i < stages; i++) {
				w.addTexture(JEI3_TEX, 0, 47 + (i * 31), 101, 31, 0, 47);
				w.addText(Component.translatable("extradelight.jei.info.vat.stage", (i + 1)), 0, 49 + (i * 31),
						0xFFFFFFFF, true);
				VatRecipe.StageIngredient si = recipe.getStageIngredients().get(i);
				w.addText(Component.translatable(si.lid ? "extradelight.jei.info.vat.lid.on"
						: "extradelight.jei.info.vat.lid.off"), 0, 58 + (i * 31), si.lid ? 0xFFFF5555 : 0xFFFFFFFF,
						true);
				float days = (float) si.time / Fermentation.dayTick;
				w.addText(Component.translatable(
						days == 1 ? "extradelight.jei.info.vat.day" : "extradelight.jei.info.vat.days",
						String.format("%.2f", days)), 0, 67 + (i * 31), 0xFFFFFFFF, true);
			}
			w.addTexture(JEI3_TEX, 0, 46 + (stages * 31), 101, 33, 0, 78);
			for (int i = 0; i < inputs.size(); i++) {
				w.addSlot(inputs.get(i), 48 + (i % 3) * 18, 1 + (i / 3) * 18);
			}
			FluidStack fs = recipe.getFluid();
			if (!fs.isEmpty()) {
				w.addTank(EmiStack.of(fs.getFluid(), fs.getAmount()), 101 / 2 - 25, 1, 16, 34, (int) fs.getAmount());
				w.addSlot(EmiIngredient.of(Ingredient.of(BottleFluidRegistry.getBottleFromFluid(fs),
						new ItemStack(fs.getFluid().getBucket()))), 1, 10);
			}
			for (int i = 0; i < stages; i++)
				w.addSlot(EmiIngredient.of(recipe.getStageIngredients().get(i).ingredient), 58, 59 + i * 31);
			w.addSlot(EmiStack.of(recipe.getUsedItem()), 84, (stages * 31) + 62);
			w.addSlot(outputs.get(0), 58, (stages * 31) + 62).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Evaporator
	// ========================================================================

	private static class EvaporatorEmiRecipe extends BaseEmiRecipe {
		final EvaporatorRecipe recipe;

		EvaporatorEmiRecipe(EvaporatorRecipe recipe) {
			super(EVAPORATOR, recipe.getId(), 101, 73);
			this.recipe = recipe;
			catalysts.add(EmiIngredient.of(Ingredient.of(net.minecraft.tags.ItemTags.SHOVELS)));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI3_TEX, 0, 0, 62, 73, 101, 0);
			FluidStack fluid = recipe.getFluid();
			if (!fluid.isEmpty())
				w.addTank(EmiStack.of(fluid.getFluid(), fluid.getAmount()), 101 / 2 - 49, 1, 16, 71,
						(int) fluid.getAmount());
			if (!catalysts.isEmpty())
				w.addSlot(catalysts.get(0), 65, 30);
			if (!outputs.isEmpty())
				w.addSlot(outputs.get(0), 45, 30).large(true).recipeContext(this);
			w.addText(Component.translatable("extradelight.jei.info.evaporator.extra"), 30, 50, 0xffffff, true);
			w.addText(Component.translatable("extradelight.jei.info.evaporator.view"), 27, 4, 0xffffff, true);
			w.addText(Component.translatable("extradelight.jei.info.evaporator.sky"), 44, 14, 0xffffff, true);
		}
	}

	// ========================================================================
	// Shaped With Jar
	// ========================================================================

	private static class ShapedWithJarEmiRecipe extends BaseEmiRecipe {
		ShapedWithJarEmiRecipe(ShapedWithJarRecipe recipe) {
			super(SHAPED_WITH_JAR, recipe.getId(), 121, 72);
			recipe.getIngredients().forEach(ing -> inputs.add(EmiIngredient.of(ing)));
			outputs.add(EmiStack.of(recipe.getResultItem(null)));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 121, 72, 0, 53);
			for (int i = 0; i < inputs.size(); i++)
				w.addSlot(inputs.get(i), 1 + (i % 3 * 18), 1 + (i / 3 * 18));
			w.addSlot(outputs.get(0), 121 / 2 + 35, 20).large(true).recipeContext(this);
		}
	}

	// ========================================================================
	// Bottle Fluid Registry
	// ========================================================================

	private static class BottleFluidRegistryEmiRecipe extends BaseEmiRecipe {
		final FluidStack fluid;

		BottleFluidRegistryEmiRecipe(BottleFluidRegistryRecipe recipe) {
			super(BOTTLE_FLUID_REGISTRY, recipe.getId(), 61, 54);
			this.fluid = recipe.fluid.copy();
			inputs.add(EmiStack.of(fluid.getFluid(), fluid.getAmount()));
			inputs.add(EmiIngredient.of(recipe.bottle));
			inputs.add(EmiStack.of(new ItemStack(fluid.getFluid().getBucket())));
			outputs.add(EmiStack.of(fluid.getFluid(), fluid.getAmount()));
			outputs.add(EmiStack.of(BottleFluidRegistry.getBottleFromFluid(fluid)));
			outputs.add(EmiStack.of(new ItemStack(fluid.getFluid().getBucket())));
		}

		@Override
		public void addWidgets(WidgetHolder w) {
			w.addTexture(JEI_TEX, 0, 0, 61, 54, 84, 125);
			w.addTank(inputs.get(0), 61 / 2 - 6, 1, 16, 16, 250);
			w.addSlot(inputs.get(1), 2, 37);
			w.addSlot(inputs.get(2), 45, 37);
			w.addTank(outputs.get(0), 61 / 2 - 6, 1, 16, 16, (int) outputs.get(0).getAmount());
			w.addSlot(outputs.get(1), 2, 37).large(true).recipeContext(this);
			w.addSlot(outputs.get(2), 45, 37);
		}
	}
}
