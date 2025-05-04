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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements AbstractItemEntityAccess {

    private ItemDataHandler itemDisplayData;

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void updateDisplayInfo() {
        if (!this.getWorld().isClient()) {
            return;
        }
        if (ItemToolConfig.ItemToolEnabled
                && MinecraftClient.getInstance().player != null
                && MinecraftClient.getInstance().player.hasPermissionLevel(2)
                && MinecraftClient.getInstance().player.getPos().squaredDistanceTo(this.getPos()) < ItemToolConfig.ItemToolRenderRange * 10) {
            MinecraftClient.getInstance().player.networkHandler.getDataQueryHandler().queryEntityNbt(this.getId(), nbt -> {
                try {
                    this.itemDisplayData = ItemDataHandler.formNBT((ItemEntity) (Object) this, nbt);
                } catch (Exception e) {
                    this.itemDisplayData = null;
                    e.printStackTrace();
                }
            });
        } else {
            this.itemDisplayData = null;
        }
    }

    @Override
    public ItemDataHandler getItemDataInfo() {
        return itemDisplayData;
    }
}
