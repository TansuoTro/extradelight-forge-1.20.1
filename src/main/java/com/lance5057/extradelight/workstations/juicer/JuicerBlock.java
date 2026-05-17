package com.lance5057.extradelight.workstations.juicer;

import com.lance5057.extradelight.util.BlockEntityUtils;
import com.lance5057.extradelight.util.BottleFluidRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;

public class JuicerBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty STYLE = IntegerProperty.create("style", 0, Styles.values().length - 1);
	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public enum Styles {
		OAK, SPRUCE
	}

	public JuicerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
				.setValue(WATERLOGGED, false).setValue(STYLE, 0));
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())
				.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new JuicerBlockEntity(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.isEmpty()) {
			if (!level.isClientSide()) {
				BlockEntity blockEntity = level.getBlockEntity(pos);
				if (blockEntity instanceof JuicerBlockEntity juicer && !juicer.getInsertedItem().isEmpty()) {
					juicer.extractItem(player);
					return InteractionResult.SUCCESS;
				}
			}
			return InteractionResult.CONSUME;
		}
		return useItemOn(stack, state, level, pos, player, hand, hit);
	}

	public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof JuicerBlockEntity juicer) {
			ItemStack bottle = BottleFluidRegistry.getBottleFromFluid(juicer.getFluidTank().getFluid());
			ItemStack offhandStack = player.getOffhandItem();

			if (stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().isPresent()) {
				IFluidHandlerItem fluidHandler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
				if (fluidHandler != null) {
					FluidUtil.interactWithFluidHandler(player, hand, juicer.getFluidTank());
					return InteractionResult.SUCCESS;
				}
			} else if (!bottle.isEmpty() && (ItemStack.isSameItem(bottle.getCraftingRemainingItem(), stack)
					|| ItemStack.isSameItem(bottle.getCraftingRemainingItem(), offhandStack))) {
				if (juicer.getFluidTank().drain(250, IFluidHandler.FluidAction.SIMULATE).getAmount() == 250) {
					juicer.getFluidTank().drain(250, IFluidHandler.FluidAction.EXECUTE);
					BlockEntityUtils.Inventory.givePlayerItemStack(bottle, player, level, pos);
					player.getItemInHand(hand).shrink(1);
					return InteractionResult.SUCCESS;
				}
			} else if (player.isCrouching()) {
				juicer.extractItem(player);
				return InteractionResult.SUCCESS;
			} else if (juicer.getInsertedItem().isEmpty()) {
				if (offhandStack.isEmpty()) {
					if (!stack.isEmpty()) {
						juicer.insertItem(stack);
						return InteractionResult.SUCCESS;
					}
					return InteractionResult.PASS;
				}
				juicer.insertItem(offhandStack);
				return InteractionResult.SUCCESS;
			} else if (stack.isEmpty() || offhandStack.isEmpty()) {
				juicer.grind(player);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level,
			BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED, STYLE);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof JuicerBlockEntity juicer) {
				IItemHandler items = juicer.getItemHandler();
				for (int i = 0; i < items.getSlots(); i++) {
					level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), items.getStackInSlot(i)));
				}
				level.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
