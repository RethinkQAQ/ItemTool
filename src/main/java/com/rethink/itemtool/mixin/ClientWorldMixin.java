package com.rethink.itemtool.mixin;

import com.rethink.itemtool.AbstractItemEntityAccess;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Redirect(
            method = "method_32124",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/tick/TickManager;shouldSkipTick(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean shouldSkipTick(TickManager tickManager, Entity entity) {
        if (entity instanceof AbstractItemEntityAccess) {
            ((AbstractItemEntityAccess) entity).updateDisplayInfo();
        }
        return tickManager.shouldSkipTick(entity);
    }
}
