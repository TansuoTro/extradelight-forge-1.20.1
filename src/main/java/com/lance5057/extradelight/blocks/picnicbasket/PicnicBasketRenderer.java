package com.lance5057.extradelight.blocks.picnicbasket;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import org.joml.Quaternionf;

public class PicnicBasketRenderer implements BlockEntityRenderer<PicnicBasketBlockEntity> {
	public PicnicBasketRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(PicnicBasketBlockEntity blockEntity, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		if (!blockEntity.hasLevel()) {
			return;
		}

		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		IItemHandler itemHandler = blockEntity.getItems();
		Direction dir = blockEntity.getBlockState().getValue(PicnicBasketBlock.FACING);

		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(0), 0.4f, 0.2f, 0.3f, dir, 180);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(1), 0.4f, 0.21f, 0.7f, dir, 15);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(2), 0.8f, 0.2f, 0.3f, dir, 150);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(3), 0.8f, 0.21f, 0.7f, dir, 0);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(4), 0.2f, 0.25f, 0.5f, dir, 180);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(5), 0.2f, 0.231f, 0.7f, dir, 15);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(6), 1f, 0.25f, 0.3f, dir, 10);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(7), 0.9f, 0.251f, 0.7f, dir, 60);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(8), 0.4f, 0.3f, 0.3f, dir, 180);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(9), 0.4f, 0.31f, 0.7f, dir, 15);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(10), 0.8f, 0.3f, 0.3f, dir, 150);
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(11), 0.8f, 0.31f, 0.7f, dir, 0);
	}

	void renderItem(PicnicBasketBlockEntity blockEntity, ItemRenderer itemRenderer, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight, int packedOverlay, ItemStack stack, float x, float y,
			float z, Direction dir, float angle) {
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			BakedModel bakedModel = itemRenderer.getModel(stack, blockEntity.getLevel(), null, 0);
			poseStack.scale(0.5f, 0.5f, 0.5f);
			poseStack.translate(0.5f, 0, 0.5f);
			poseStack.translate(x, y, z);
			poseStack.mulPose(new Quaternionf().rotateXYZ((float) Math.toRadians(90), 0,
					(float) Math.toRadians(dir.toYRot() + angle)));
			itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight,
					packedOverlay, bakedModel);
			poseStack.popPose();
		}
	}

	@Override
	public boolean shouldRenderOffScreen(PicnicBasketBlockEntity blockEntity) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return 64;
	}

	@Override
	public boolean shouldRender(PicnicBasketBlockEntity blockEntity, Vec3 cameraPos) {
		return true;
	}
}
