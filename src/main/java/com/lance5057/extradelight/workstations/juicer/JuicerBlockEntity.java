package com.lance5057.extradelight.workstations.juicer;

import com.lance5057.extradelight.ExtraDelightBlockEntities;
import com.lance5057.extradelight.ExtraDelightRecipes;
import com.lance5057.extradelight.util.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

import javax.annotation.Nonnull;
import java.util.Optional;

public class JuicerBlockEntity extends SyncedBlockEntity {
	private int grinds = 0;
	public static final String INV_TAG = "inv";
	public static final int NUM_SLOTS = 1;

	private final ItemStackHandler items = createHandler();
	private final Lazy<IItemHandlerModifiable> itemHandler = Lazy.of(() -> items);
	private final FluidTank tank = createFluidHandler();

	public JuicerBlockEntity(BlockPos pos, BlockState state) {
		super(ExtraDelightBlockEntities.JUICER.get(), pos, state);
	}

	private FluidTank createFluidHandler() {
		return new FluidTank(FluidType.BUCKET_VOLUME * 4) {
			@Override
			protected void onContentsChanged() {
				JuicerBlockEntity.this.requestModelDataUpdate();
				if (JuicerBlockEntity.this.getLevel() != null) {
					JuicerBlockEntity.this.getLevel().sendBlockUpdated(JuicerBlockEntity.this.getBlockPos(),
							JuicerBlockEntity.this.getBlockState(), JuicerBlockEntity.this.getBlockState(), 3);
				}
				JuicerBlockEntity.this.setChanged();
			}
		};
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(1) {
			@Override
			protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
				return 8;
			}

			@Override
			protected void onContentsChanged(int slot) {
				zeroProgress();
				updateInventory();
			}
		};
	}

	public IItemHandlerModifiable getItemHandler() {
		return itemHandler.get();
	}

	public FluidTank getFluidTank() {
		return tank;
	}

	public float getFullness() {
		return (float) tank.getFluidAmount() / (float) tank.getCapacity();
	}

	public void insertItem(ItemStack stack) {
		matchRecipe(stack).ifPresent(recipe -> {
			BlockEntityUtils.Inventory.insertItem(items, stack, NUM_SLOTS);
			this.updateInventory();
		});
	}

	public void extractItem(Player player) {
		BlockEntityUtils.Inventory.extractItem(player, items, NUM_SLOTS);
		this.updateInventory();
	}

	public void zeroProgress() {
		grinds = 0;
	}

	public void updateInventory() {
		requestModelDataUpdate();
		if (this.getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
		this.setChanged();
	}

	public ItemStack getInsertedItem() {
		return items.getStackInSlot(0);
	}

	public int getGrind() {
		return grinds;
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		writeNBT(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		readNBT(tag);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		readNBT(pkt.getTag());
	}

	void readNBT(CompoundTag tag) {
		if (tag.contains(INV_TAG)) {
			items.deserializeNBT(tag.getCompound(INV_TAG));
		}
		tank.readFromNBT(tag);
		this.grinds = tag.getInt("Grinds");
	}

	CompoundTag writeNBT(CompoundTag tag) {
		tag.put(INV_TAG, items.serializeNBT());
		tank.writeToNBT(tag);
		tag.putInt("Grinds", this.grinds);
		return tag;
	}

	@Override
	public void load(@Nonnull CompoundTag tag) {
		super.load(tag);
		readNBT(tag);
	}

	@Override
	public void saveAdditional(@Nonnull CompoundTag tag) {
		super.saveAdditional(tag);
		writeNBT(tag);
	}

	public Optional<JuicerRecipe> matchRecipe(ItemStack stack) {
		if (this.level != null) {
			SimpleContainer container = new SimpleContainer(stack);
			for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
				if (recipe instanceof JuicerRecipe juicerRecipe && juicerRecipe.matches(container, level)) {
					return Optional.of(juicerRecipe);
				}
			}
		}
		return Optional.empty();
	}

	public InteractionResult grind(Player player) {
		matchRecipe(getInsertedItem()).ifPresent(recipe -> {
			FluidStack recipeFluid = recipe.getFluid();
			if ((tank.isEmpty() || recipeFluid.isFluidEqual(tank.getFluid()))
					&& tank.fill(recipeFluid, IFluidHandler.FluidAction.SIMULATE) == recipeFluid.getAmount()) {
				if ((this.grinds + 1) < 8) {
					grinds++;
					for (int i = 0; i < 1 + level.random.nextInt(4); i++) {
						level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, getInsertedItem()),
								worldPosition.getX() + 0.25f + level.random.nextDouble() / 2,
								worldPosition.getY() + 0.5f - level.random.nextDouble(),
								worldPosition.getZ() + 0.25f + level.random.nextDouble() / 2, 0, 0, 0);
					}
					level.playSound(player, worldPosition, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1, 1);
				} else {
					ItemStack in = getInsertedItem();
					for (int i = 0; i < in.getCount(); i++) {
						if (this.level.random.nextInt(100) < recipe.getChance()) {
							BlockEntityUtils.Inventory.dropItemInWorld(recipe.getResultItem(this.level.registryAccess()).copy(), level,
									worldPosition);
						}
						tank.fill(recipeFluid.copy(), IFluidHandler.FluidAction.EXECUTE);
					}
					items.setStackInSlot(0, ItemStack.EMPTY);
				}
				updateInventory();
			}
		});
		return InteractionResult.SUCCESS;
	}
}
