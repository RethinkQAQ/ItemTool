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
        if (ItemToolConfig.ItemToolEnabled && MinecraftClient.getInstance().player != null
        && MinecraftClient.getInstance().player.hasPermissionLevel(2)
        && MinecraftClient.getInstance().player.getPos().squaredDistanceTo(this.getPos()) < ItemToolConfig.ItemToolRenderRange * 10) {
            MinecraftClient.getInstance().player.networkHandler.getDataQueryHandler().queryEntityNbt(this.getId(), nbt -> {
                try {
                    this.itemDisplayData = ItemDataHandler.formNBT((ItemEntity) (Object)this, nbt);
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
