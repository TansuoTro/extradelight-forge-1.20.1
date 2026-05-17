package com.lance5057.extradelight.modules;

import com.lance5057.extradelight.ExtraDelightBlocks;
import com.lance5057.extradelight.ExtraDelightFluids;
import com.lance5057.extradelight.ExtraDelightItems;
import com.lance5057.extradelight.blocks.FruitLeafBlock;
import com.lance5057.extradelight.blocks.fluids.VinegarFluidBlock;
import com.lance5057.extradelight.food.EDFoods;
import com.lance5057.extradelight.items.SourJuiceItem;
import com.lance5057.extradelight.items.ToolTipConsumableItem;
import com.lance5057.extradelight.util.EDItemGenerator;
import com.lance5057.extradelight.worldgen.features.trees.ExtraDelightTreeGrowers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class SummerCitrus {
	public static final RegistryObject<Item> LEMON = EDItemGenerator
			.register("lemon", () -> new ToolTipConsumableItem(new Item.Properties().food(EDFoods.LEMON), true))
			.advancementIngredients().finish();
	public static final RegistryObject<Block> LEMON_PETAL_LITTER = ExtraDelightBlocks.BLOCKS.register(
			"lemon_petal_litter", () -> new CarpetBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES)));
	public static final RegistryObject<FruitLeafBlock> LEMON_LEAVES = ExtraDelightBlocks.BLOCKS.register("lemon_leaves",
			() -> new FruitLeafBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES), LEMON));
	public static final RegistryObject<Item> LEMON_PETAL_LITTER_ITEM = ExtraDelightItems.ITEMS.register(
			"lemon_petal_litter_item", () -> new BlockItem(LEMON_PETAL_LITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> LEMON_LEAVES_ITEM = ExtraDelightItems.ITEMS.register("lemon_leaves",
			() -> new BlockItem(LEMON_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<SaplingBlock> LEMON_SAPLING = ExtraDelightBlocks.BLOCKS.register("lemon_sapling",
			() -> new SaplingBlock(ExtraDelightTreeGrowers.LEMON, Block.Properties.copy(Blocks.DARK_OAK_SAPLING)));
	public static final RegistryObject<Item> LEMON_SAPLING_ITEM = ExtraDelightItems.ITEMS.register("lemon_sapling",
			() -> new BlockItem(LEMON_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Block> POTTED_LEMON_SAPLING = ExtraDelightBlocks.BLOCKS.register(
			"potted_lemon_sapling", () -> new Block(Block.Properties.copy(Blocks.POTTED_ACACIA_SAPLING).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> LEMON_JUICE = EDItemGenerator
			.register("lemon_juice", () -> new SourJuiceItem(ExtraDelightItems.drinkItem(), 3, 100))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> LEMON_JUICE_FLUID_BUCKET = ExtraDelightItems.ITEMS.register(
			"lemon_juice_fluid_bucket", () -> ExtraDelightItems.stack1bucketItem(ExtraDelightFluids.LEMON_JUICE));
	public static final RegistryObject<VinegarFluidBlock> LEMON_JUICE_FLUID_BLOCK = ExtraDelightBlocks.BLOCKS.register(
			"lemon_juice_fluid_block", () -> new VinegarFluidBlock(ExtraDelightFluids.LEMON_JUICE.FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> LEMON_CRATE = ExtraDelightBlocks.BLOCKS.register("lemon_crate",
			() -> new Block(Block.Properties.copy(ModBlocks.BEETROOT_CRATE.get()).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> LEMON_CRATE_ITEM = ExtraDelightItems.ITEMS.register("lemon_crate_item",
			() -> new BlockItem(LEMON_CRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SLICED_LEMON = EDItemGenerator
			.register("sliced_lemon", () -> new Item(new Item.Properties().food(EDFoods.LEMON)))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> LEMON_ZEST = EDItemGenerator
			.register("lemon_zest", () -> new Item(new Item.Properties())).advancementIngredients().finish();

	public static final RegistryObject<Item> LIME = EDItemGenerator
			.register("lime", () -> new ToolTipConsumableItem(new Item.Properties().food(EDFoods.LIME), true))
			.advancementIngredients().finish();
	public static final RegistryObject<Block> LIME_PETAL_LITTER = ExtraDelightBlocks.BLOCKS.register(
			"lime_petal_litter", () -> new CarpetBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES)));
	public static final RegistryObject<FruitLeafBlock> LIME_LEAVES = ExtraDelightBlocks.BLOCKS.register("lime_leaves",
			() -> new FruitLeafBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES), LIME));
	public static final RegistryObject<Item> LIME_PETAL_LITTER_ITEM = ExtraDelightItems.ITEMS.register(
			"lime_petal_litter_item", () -> new BlockItem(LIME_PETAL_LITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> LIME_LEAVES_ITEM = ExtraDelightItems.ITEMS.register("lime_leaves",
			() -> new BlockItem(LIME_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<SaplingBlock> LIME_SAPLING = ExtraDelightBlocks.BLOCKS.register("lime_sapling",
			() -> new SaplingBlock(ExtraDelightTreeGrowers.LIME, Block.Properties.copy(Blocks.DARK_OAK_SAPLING)));
	public static final RegistryObject<Item> LIME_SAPLING_ITEM = ExtraDelightItems.ITEMS.register("lime_sapling",
			() -> new BlockItem(LIME_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Block> POTTED_LIME_SAPLING = ExtraDelightBlocks.BLOCKS.register(
			"potted_lime_sapling", () -> new Block(Block.Properties.copy(Blocks.POTTED_ACACIA_SAPLING).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> LIME_JUICE = EDItemGenerator
			.register("lime_juice", () -> new SourJuiceItem(ExtraDelightItems.drinkItem(), 4, 100))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> LIME_JUICE_FLUID_BUCKET = ExtraDelightItems.ITEMS.register(
			"lime_juice_fluid_bucket", () -> ExtraDelightItems.stack1bucketItem(ExtraDelightFluids.LIME_JUICE));
	public static final RegistryObject<VinegarFluidBlock> LIME_JUICE_FLUID_BLOCK = ExtraDelightBlocks.BLOCKS.register(
			"lime_juice_fluid_block", () -> new VinegarFluidBlock(ExtraDelightFluids.LIME_JUICE.FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> LIME_CRATE = ExtraDelightBlocks.BLOCKS.register("lime_crate",
			() -> new Block(Block.Properties.copy(ModBlocks.BEETROOT_CRATE.get()).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> LIME_CRATE_ITEM = ExtraDelightItems.ITEMS.register("lime_crate_item",
			() -> new BlockItem(LIME_CRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SLICED_LIME = EDItemGenerator
			.register("sliced_lime", () -> new Item(new Item.Properties().food(EDFoods.LIME)))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> LIME_ZEST = EDItemGenerator
			.register("lime_zest", () -> new Item(new Item.Properties())).advancementIngredients().finish();

	public static final RegistryObject<Item> ORANGE = EDItemGenerator
			.register("orange", () -> new ToolTipConsumableItem(new Item.Properties().food(EDFoods.ORANGE), true))
			.advancementIngredients().finish();
	public static final RegistryObject<Block> ORANGE_PETAL_LITTER = ExtraDelightBlocks.BLOCKS.register(
			"orange_petal_litter", () -> new CarpetBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES)));
	public static final RegistryObject<FruitLeafBlock> ORANGE_LEAVES = ExtraDelightBlocks.BLOCKS.register("orange_leaves",
			() -> new FruitLeafBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES), ORANGE));
	public static final RegistryObject<Item> ORANGE_PETAL_LITTER_ITEM = ExtraDelightItems.ITEMS.register(
			"orange_petal_litter_item", () -> new BlockItem(ORANGE_PETAL_LITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> ORANGE_LEAVES_ITEM = ExtraDelightItems.ITEMS.register("orange_leaves",
			() -> new BlockItem(ORANGE_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<SaplingBlock> ORANGE_SAPLING = ExtraDelightBlocks.BLOCKS.register("orange_sapling",
			() -> new SaplingBlock(ExtraDelightTreeGrowers.ORANGE, Block.Properties.copy(Blocks.DARK_OAK_SAPLING)));
	public static final RegistryObject<Item> ORANGE_SAPLING_ITEM = ExtraDelightItems.ITEMS.register("orange_sapling",
			() -> new BlockItem(ORANGE_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Block> POTTED_ORANGE_SAPLING = ExtraDelightBlocks.BLOCKS.register(
			"potted_orange_sapling", () -> new Block(Block.Properties.copy(Blocks.POTTED_ACACIA_SAPLING).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> ORANGE_JUICE = EDItemGenerator
			.register("orange_juice", () -> new SourJuiceItem(ExtraDelightItems.drinkItem(), 1, 50))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> ORANGE_JUICE_FLUID_BUCKET = ExtraDelightItems.ITEMS.register(
			"orange_juice_fluid_bucket", () -> ExtraDelightItems.stack1bucketItem(ExtraDelightFluids.ORANGE_JUICE));
	public static final RegistryObject<VinegarFluidBlock> ORANGE_JUICE_FLUID_BLOCK = ExtraDelightBlocks.BLOCKS.register(
			"orange_juice_fluid_block", () -> new VinegarFluidBlock(ExtraDelightFluids.ORANGE_JUICE.FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> ORANGE_CRATE = ExtraDelightBlocks.BLOCKS.register("orange_crate",
			() -> new Block(Block.Properties.copy(ModBlocks.BEETROOT_CRATE.get()).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> ORANGE_CRATE_ITEM = ExtraDelightItems.ITEMS.register("orange_crate_item",
			() -> new BlockItem(ORANGE_CRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SLICED_ORANGE = EDItemGenerator
			.register("sliced_orange", () -> new Item(new Item.Properties().food(EDFoods.ORANGE)))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> ORANGE_ZEST = EDItemGenerator
			.register("orange_zest", () -> new Item(new Item.Properties())).advancementIngredients().finish();

	public static final RegistryObject<Item> GRAPEFRUIT = EDItemGenerator
			.register("grapefruit", () -> new ToolTipConsumableItem(new Item.Properties().food(EDFoods.GRAPEFRUIT), true))
			.advancementIngredients().finish();
	public static final RegistryObject<Block> GRAPEFRUIT_PETAL_LITTER = ExtraDelightBlocks.BLOCKS.register(
			"grapefruit_petal_litter", () -> new CarpetBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES)));
	public static final RegistryObject<FruitLeafBlock> GRAPEFRUIT_LEAVES = ExtraDelightBlocks.BLOCKS.register(
			"grapefruit_leaves", () -> new FruitLeafBlock(Block.Properties.copy(Blocks.ACACIA_LEAVES), GRAPEFRUIT));
	public static final RegistryObject<Item> GRAPEFRUIT_PETAL_LITTER_ITEM = ExtraDelightItems.ITEMS.register(
			"grapefruit_petal_litter_item", () -> new BlockItem(GRAPEFRUIT_PETAL_LITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> GRAPEFRUIT_LEAVES_ITEM = ExtraDelightItems.ITEMS.register(
			"grapefruit_leaves", () -> new BlockItem(GRAPEFRUIT_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<SaplingBlock> GRAPEFRUIT_SAPLING = ExtraDelightBlocks.BLOCKS.register(
			"grapefruit_sapling", () -> new SaplingBlock(ExtraDelightTreeGrowers.GRAPEFRUIT, Block.Properties.copy(Blocks.DARK_OAK_SAPLING)));
	public static final RegistryObject<Item> GRAPEFRUIT_SAPLING_ITEM = ExtraDelightItems.ITEMS.register(
			"grapefruit_sapling", () -> new BlockItem(GRAPEFRUIT_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Block> POTTED_GRAPEFRUIT_SAPLING = ExtraDelightBlocks.BLOCKS.register(
			"potted_grapefruit_sapling", () -> new Block(Block.Properties.copy(Blocks.POTTED_ACACIA_SAPLING).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> GRAPEFRUIT_JUICE = EDItemGenerator
			.register("grapefruit_juice", () -> new SourJuiceItem(ExtraDelightItems.drinkItem(), 1, 50))
			.advancementIngredients().finish();
	public static final RegistryObject<Item> GRAPEFRUIT_JUICE_FLUID_BUCKET = ExtraDelightItems.ITEMS.register(
			"grapefruit_juice_fluid_bucket", () -> ExtraDelightItems.stack1bucketItem(ExtraDelightFluids.GRAPEFRUIT_JUICE));
	public static final RegistryObject<VinegarFluidBlock> GRAPEFRUIT_JUICE_FLUID_BLOCK = ExtraDelightBlocks.BLOCKS.register(
			"grapefruit_juice_fluid_block", () -> new VinegarFluidBlock(ExtraDelightFluids.GRAPEFRUIT_JUICE.FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
	public static final RegistryObject<Block> GRAPEFRUIT_CRATE = ExtraDelightBlocks.BLOCKS.register("grapefruit_crate",
			() -> new Block(Block.Properties.copy(ModBlocks.BEETROOT_CRATE.get()).mapColor(MapColor.PLANT)));
	public static final RegistryObject<Item> GRAPEFRUIT_CRATE_ITEM = ExtraDelightItems.ITEMS.register(
			"grapefruit_crate_item", () -> new BlockItem(GRAPEFRUIT_CRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SLICED_GRAPEFRUIT = EDItemGenerator
			.register("sliced_grapefruit", () -> new Item(new Item.Properties().food(EDFoods.GRAPEFRUIT)))
			.advancementIngredients().finish();

	public static void setup() {
		FireBlock fire = (FireBlock) Blocks.FIRE;
		fire.setFlammable(LEMON_LEAVES.get(), 30, 60);
		fire.setFlammable(LIME_LEAVES.get(), 30, 60);
		fire.setFlammable(ORANGE_LEAVES.get(), 30, 60);
		fire.setFlammable(GRAPEFRUIT_LEAVES.get(), 30, 60);
		fire.setFlammable(LEMON_CRATE.get(), 5, 20);
		fire.setFlammable(LIME_CRATE.get(), 5, 20);
		fire.setFlammable(ORANGE_CRATE.get(), 5, 20);
		fire.setFlammable(GRAPEFRUIT_CRATE.get(), 5, 20);
	}
}
