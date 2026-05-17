package com.lance5057.extradelight.workstations.juicer;

import com.lance5057.extradelight.ExtraDelightClientEvents;
import com.lance5057.extradelight.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class JuicerRenderer implements BlockEntityRenderer<JuicerBlockEntity> {
	public JuicerRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(JuicerBlockEntity blockEntity, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		float grind = blockEntity.getGrind();
		float height = ((-0.3f) / 4) * (grind - 1) + 0.1f;

		renderModel(ExtraDelightClientEvents.juicerCrankModel, blockEntity, poseStack, bufferSource, packedLight, packedOverlay,
				height, grind, true);
		renderModel(ExtraDelightClientEvents.juicerPlateModel, blockEntity, poseStack, bufferSource, packedLight, packedOverlay,
				height, grind, false);
		renderItem(blockEntity, poseStack, bufferSource, packedLight, packedOverlay, grind);
		renderFluid(blockEntity, poseStack, bufferSource, packedLight, packedOverlay);
	}

	private void renderModel(BakedModel model, JuicerBlockEntity blockEntity, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight, int packedOverlay, float height, float grind, boolean rotates) {
		if (model == null) {
			return;
		}

		poseStack.pushPose();
		if (rotates) {
			poseStack.translate(0.5, height, 0.5);
			poseStack.mulPose(new Quaternionf().rotateY(grind));
			poseStack.translate(-0.5, 0, -0.5);
		} else {
			poseStack.translate(0, 0.875 + height, 0);
		}

		Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(),
				bufferSource.getBuffer(RenderType.entityCutout(model.getParticleIcon().atlasLocation())),
				blockEntity.getBlockState(), model, 1.0f, 1.0f, 1.0f, packedLight, packedOverlay);
		poseStack.popPose();
	}

	private void renderItem(JuicerBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource,
			int packedLight, int packedOverlay, float grind) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		IItemHandlerModifiable inventory = blockEntity.getItemHandler();
		ItemStack item = inventory.getStackInSlot(0);

		if (!item.isEmpty()) {
			for (int i = 0; i < item.getCount(); i++) {
				BakedModel bakedModel = itemRenderer.getModel(item, blockEntity.getLevel(), null, 0);
				poseStack.pushPose();
				poseStack.translate(0.5f, 0.15f, 0.5f);
				poseStack.mulPose(new Quaternionf().rotateXYZ(0, (float) Math.toRadians((90 * i) + (45 * grind)), 0));
				poseStack.mulPose(new Quaternionf().rotateXYZ((float) Math.toRadians(45), 0, (float) Math.toRadians(45)));
				poseStack.translate(0.15f, 0, 0);
				float scale = 1 / (1 + grind);
				poseStack.scale(scale, scale, scale);
				poseStack.scale(0.65f, 0.65f, 0.65f);
				itemRenderer.render(item, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay,
						bakedModel);
				poseStack.popPose();
			}
		}
	}

	private void renderFluid(JuicerBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource,
			int packedLight, int packedOverlay) {
		if (blockEntity.getFluidTank().getFluid().isEmpty()) {
			return;
		}

		VertexConsumer vertexConsumer = bufferSource.getBuffer(Sheets.translucentCullBlockSheet());
		Matrix4f mat = poseStack.last().pose();
		Matrix3f normal = poseStack.last().normal();
		poseStack.pushPose();
		FluidStack fluidStack = blockEntity.getFluidTank().getFluid();
		Fluid fluid = fluidStack.getFluid();
		IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid);
		RenderUtil.buildCubeAll(new Vector3f(2f / 16f, 2.8F / 16f, 2f / 16f),
				new Vector3f(12f / 16f, blockEntity.getFullness() * (11f / 16f), 12f / 16f), vertexConsumer,
				mat, normal, fluidTypeExtensions.getTintColor(fluidStack),
				RenderUtil.getUV(fluidTypeExtensions.getStillTexture()), packedLight, packedOverlay, poseStack);
		poseStack.popPose();
	}
}
