/*
 * This file is part of the ItemTool project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2025  Fallen_Breath and contributors
 *
 * ItemTool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ItemTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ItemTool.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.rethink.itemtool.mixin;

import com.rethink.itemtool.AbstractItemEntityAccess;
import com.rethink.itemtool.config.ItemToolConfig;
import com.rethink.itemtool.handler.ItemDataHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
//#if MC > 12102
//$$ import net.minecraft.client.render.entity.state.ItemEntityRenderState;
//$$ import org.spongepowered.asm.mixin.Unique;
//#endif
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRenderMixin
        extends EntityRenderer<
        ItemEntity
        //#if MC >= 12102
        //$$ ,ItemEntityRenderState
        //#endif
        > {

    //#if MC > 12102
    //$$ @Unique
    //$$ private ItemEntity entity;
    //#endif

    protected ItemEntityRenderMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(
            //#if MC >= 12102
            //$$ method = "render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            //#else
            method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            //#endif
            at = @At("HEAD")
    )
    private void onRender(
            //#if MC >= 12102
            //$$ ItemEntityRenderState itemEntityRenderState,
            //#else
            ItemEntity itemEntity, float f, float g,
            //#endif
            MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        //#if MC >= 12102
        //$$ if (this.entity instanceof ItemEntity itemEntity) {
        //#endif
        double distance = this.dispatcher.getSquaredDistanceToCamera(itemEntity);
        if (distance < ItemToolConfig.ItemToolRenderRange * 10 && itemEntity instanceof AbstractItemEntityAccess item) {
            ItemDataHandler displayInfo = item.getItemDataInfo();
            if (displayInfo == null) {
                return;
            }

            ArrayList<Text> infoTexts = displayInfo.getInfoTexts();
            if (infoTexts == null) {
                return;
            }

            TextRenderer textRenderer = this.getTextRenderer();

            float yOffset = 10 - (infoTexts.size() * 10);

            float backgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
            int backgroundColor = (int)(backgroundOpacity * 255.0f) << 24;
            for (Text infoText : infoTexts) {
                matrices.push();
                matrices.translate(0.0, 0.7f, 0.0);
                matrices.multiply(this.dispatcher.getRotation());
                //#if MC < 12100
                //$$ matrices.scale(-0.025f, -0.0255f, 0.025f);
                //#else
                matrices.scale(0.025f, -0.0255f, 0.025f);
                //#endif
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                float h = -textRenderer.getWidth(infoText) / 2f;

                textRenderer.draw(infoText, h, yOffset, 0x20FFFFFF, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, backgroundColor, i);
                textRenderer.draw(infoText, h, yOffset, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i);

                matrices.pop();

                yOffset += 10;
            }
            //#if MC >= 12102
            //$$ }
            //#endif
        }
    }

    //#if MC > 12102
    //$$    @Inject(
    //$$            method = "updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V",
    //$$            at = @At("HEAD")
    //$$    )
    //$$    private void getItemData(ItemEntity itemEntity, ItemEntityRenderState itemEntityRenderState, float f, CallbackInfo ci) {
    //$$        this.entity = itemEntity;
    //$$    }
    //#endif
}
