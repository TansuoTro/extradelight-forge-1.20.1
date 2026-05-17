package com.lance5057.extradelight.blocks.picnicbasket;

import com.lance5057.extradelight.ExtraDelightContainers;
import com.lance5057.extradelight.gui.HideableSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class PicnicBasketMenu extends AbstractContainerMenu {
	public final PicnicBasketBlockEntity tileEntity;
	private final ContainerLevelAccess canInteractWithCallable;
	protected final Level level;
	private final Player player;

	public PicnicBasketMenu(final int windowId, final Inventory playerInventory,
			final PicnicBasketBlockEntity tileEntity) {
		super(ExtraDelightContainers.PICNIC_BASKET_MENU.get(), windowId);
		this.tileEntity = tileEntity;
		this.player = playerInventory.player;
		this.level = playerInventory.player.level();
		this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

		int startX = 8;
		int startY = 8;
		int inputStartX = 8 + 45;
		int inputStartY = 8;
		int borderSlotSize = 18;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 4; ++column) {
				this.addSlot(new HideableSlot(tileEntity.getItems(), (row * 4) + column,
						inputStartX + (column * borderSlotSize), inputStartY + (row * borderSlotSize), true) {
					@Override
					public boolean mayPlace(ItemStack stack) {
						if (stack.getItem() instanceof BlockItem blockItem) {
							if (blockItem.getBlock() instanceof PicnicBasketBlock) {
								return false;
							}
							if (blockItem.getBlock() instanceof ShulkerBoxBlock) {
								return false;
							}
						}
						return true;
					}
				});
			}
		}

		int startPlayerInvY = startY * 4 + 36;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * borderSlotSize),
						startPlayerInvY + (row * borderSlotSize)));
			}
		}

		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, startX + (column * borderSlotSize), 126));
		}
	}

	private static PicnicBasketBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof PicnicBasketBlockEntity basket) {
			return basket;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public PicnicBasketMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		int basketSlots = 12;
		int startPlayerInv = basketSlots;
		int endPlayerInv = startPlayerInv + 36;
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			itemstack = slotStack.copy();
			if (index < startPlayerInv) {
				if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(slotStack, 0, basketSlots, false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (slotStack.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, slotStack);
		}
		return itemstack;
	}

	@Override
	public boolean stillValid(Player player) {
		return canInteractWithCallable.evaluate((level, pos) -> level.getBlockState(pos).getBlock() instanceof PicnicBasketBlock
				&& player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D, true);
	}
}
