/*
 * This file is part of the ItemTool project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2025  Rethink_QAQ and contributors
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
import com.rethink.itemtool.config.DisplayMode;
import com.rethink.itemtool.config.ItemToolConfig;
import com.rethink.itemtool.handler.ItemDataHandler;
import com.rethink.itemtool.uitl.RayTraceUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#if MC >= 12104
//$$ import net.minecraft.client.render.VertexRendering;
//#endif

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow @Final private EntityRenderDispatcher entityRenderDispatcher;

    @Shadow private @Nullable ClientWorld world;

    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "renderEntity",
            at = @At("HEAD")
    )
    private void renderItemInfo(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (ItemToolConfig.ItemToolDisplayMode == DisplayMode.LOOK_AT) {
            if (entity != RayTraceUtils.getRayTraceFromEntity()) {
                return;
            }
        }
        if (ItemToolConfig.ItemTryMergeBoundingBox && entity instanceof AbstractItemEntityAccess item) {
            if (this.entityRenderDispatcher.getSquaredDistanceToCamera(entity) > ItemToolConfig.ItemToolRenderRange * 10) {
                return;
            }
            ItemDataHandler itemInfo = item.getItemDataInfo();
            if (itemInfo == null) {
                return;
            }
                VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLines());
                matrices.push();
                matrices.translate(-cameraX, -cameraY, -cameraZ);

            if (ItemToolConfig.ItemTryMergeBoundingBox) {
                Box box = itemInfo.tryMergeBox().expand(0.5, 0, 0.5);
                if (box != null) {
                    //#if MC < 12104
                    WorldRenderer
                    //#else
                    //$$ VertexRendering
                    //#endif
                            .drawBox(matrices, buffer, box, 0.7f, 0.3f, 0.3f, 1.0f);
                }
            }
            matrices.pop();
        }
    }
}
