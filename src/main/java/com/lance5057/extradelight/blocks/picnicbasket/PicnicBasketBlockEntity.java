package com.lance5057.extradelight.blocks.picnicbasket;

import com.lance5057.extradelight.ExtraDelightBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class PicnicBasketBlockEntity extends BlockEntity {
	public static final String TAG = "inv";
	public static final int NUM_SLOTS = 12;

	private final PicnicBasketItemStackHandler items = createHandler();
	private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> items);

	public PicnicBasketBlockEntity(BlockPos pos, BlockState state) {
		super(ExtraDelightBlockEntities.PICNIC_BASKET.get(), pos, state);
	}

	private PicnicBasketItemStackHandler createHandler() {
		return new PicnicBasketItemStackHandler(NUM_SLOTS);
	}

	public IItemHandler getItems() {
		return this.itemHandler.get();
	}

	public void updateInventory() {
		requestModelDataUpdate();
		if (this.getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
		this.setChanged();
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
		CompoundTag tag = pkt.getTag();
		if (tag != null) {
			readNBT(tag);
		}
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

	public String getDisplayName() {
		return "screen.picnic_basket.name";
	}
}
