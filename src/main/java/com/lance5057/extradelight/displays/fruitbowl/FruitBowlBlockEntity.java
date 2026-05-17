package com.lance5057.extradelight.displays.fruitbowl;

import com.lance5057.extradelight.ExtraDelightBlockEntities;
import com.lance5057.extradelight.util.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class FruitBowlBlockEntity extends BlockEntity implements ICapabilityProvider {
	public static final String TAG = "inv";
	public static final int NUM_SLOTS = 9;

	private final ItemStackHandler items = createHandler();
	private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> items);

	public FruitBowlBlockEntity(BlockPos pos, BlockState state) {
		super(ExtraDelightBlockEntities.FRUIT_BOWL.get(), pos, state);
	}

	public int getNumSlots() {
		return NUM_SLOTS;
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(NUM_SLOTS) {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
		};
	}

	public void insertItem(ItemStack stack) {
		BlockEntityUtils.Inventory.insertItem(items, stack, NUM_SLOTS);
		this.updateInventory();
	}

	public void extractItem(Player player) {
		BlockEntityUtils.Inventory.extractItem(player, items, NUM_SLOTS);
		this.updateInventory();
	}

	public void updateInventory() {
		requestModelDataUpdate();
		if (this.getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
		this.setChanged();
	}

	public IItemHandler getItemHandler() {
		return itemHandler.get();
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
		if (tag.contains(TAG)) {
			items.deserializeNBT(tag.getCompound(TAG));
		}
	}

	CompoundTag writeNBT(CompoundTag tag) {
		tag.put(TAG, items.serializeNBT());
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
}
