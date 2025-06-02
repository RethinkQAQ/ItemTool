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

/*
 *
 */

package com.rethink.itemtool.uitl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;


public class RayTraceUtils {
    public static Entity getRayTraceFromEntity() {
        World worldIn = MinecraftClient.getInstance().world;
        Entity entityIn = MinecraftClient.getInstance().getCameraEntity();
        Vec3d eyeVec = new Vec3d(entityIn.getX(), entityIn.getEyeY(), entityIn.getZ());
        Vec3d rangedLookRot = entityIn.getRotationVec(1f).multiply(5);
        Vec3d lookVec = eyeVec.add(rangedLookRot);

        RaycastContext context = new RaycastContext(eyeVec, lookVec, RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE, entityIn);
        HitResult result = worldIn.raycast(context);

        if (result == null) {
            result = BlockHitResult.createMissed(Vec3d.ZERO, Direction.UP, BlockPos.ORIGIN);
        }

        Box bb = entityIn.getBoundingBox().expand(rangedLookRot.x, rangedLookRot.y, rangedLookRot.z).expand(1d, 1d, 1d);

        List<Entity> list = worldIn.getOtherEntities(entityIn, bb);
        double closest = result.getType() == HitResult.Type.BLOCK ? eyeVec.distanceTo(result.getPos()) : Double.MAX_VALUE;
        Optional<Vec3d> entityTrace = Optional.empty();
        Entity targetEntity = null;

        for (Entity entity : list) {
            bb = entity.getBoundingBox();
            Optional<Vec3d> traceTmp = bb.raycast(lookVec, eyeVec);

            if (traceTmp.isPresent()) {
                double distance = eyeVec.distanceTo(traceTmp.get());

                if (distance <= closest) {
                    targetEntity = entity;
                    entityTrace = traceTmp;
                    closest = distance;
                }
            }
        }
        return targetEntity;
    }
}
