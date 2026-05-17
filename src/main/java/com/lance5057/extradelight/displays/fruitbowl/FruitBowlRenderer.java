package com.lance5057.extradelight.displays.fruitbowl;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.joml.Quaternionf;

public class FruitBowlRenderer implements BlockEntityRenderer<FruitBowlBlockEntity> {
	public FruitBowlRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(FruitBowlBlockEntity blockEntity, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		if (!blockEntity.hasLevel()) {
			return;
		}

		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		IItemHandler itemHandler = blockEntity.getItemHandler();

		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(0), -0.0f, 0.25f, 0.23f, new Quaternionf().rotateXYZ(0, 0, 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(1), -0.0f, 0.25f, 0.23f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(90), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(2), -0.0f, 0.25f, 0.23f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(180), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(3), -0.0f, 0.25f, 0.23f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(270), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(4), -0.0f, 0.25f, 0.15f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(45), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(5), -0.0f, 0.25f, 0.15f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(135), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(6), -0.0f, 0.25f, 0.15f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(225), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(7), -0.0f, 0.25f, 0.15f,
				new Quaternionf().rotateXYZ(0, (float) Math.toRadians(315), 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, 0));
		renderItem(blockEntity, itemRenderer, poseStack, bufferSource, packedLight, packedOverlay,
				itemHandler.getStackInSlot(8), -0.0f, 0.25f, -0.05f, new Quaternionf().rotateXYZ(0, 0, 0),
				new Quaternionf().rotateXYZ((float) Math.toRadians(90), 0, 0));
	}

	void renderItem(FruitBowlBlockEntity blockEntity, ItemRenderer itemRenderer, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight, int packedOverlay, ItemStack stack, float x, float y,
			float z, Quaternionf rotation, Quaternionf rotation2) {
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			BakedModel bakedModel = itemRenderer.getModel(stack, blockEntity.getLevel(), null, 0);
			poseStack.translate(0.5f, 0, 0.5f);
			poseStack.mulPose(rotation);
			poseStack.translate(x, y, z);
			poseStack.mulPose(rotation2);
			poseStack.scale(0.75f, 0.75f, 0.75f);
			itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight,
					packedOverlay, bakedModel);
			poseStack.popPose();
		}
	}
}
