package com.rethink.itemtool.mixin;

import com.rethink.itemtool.AbstractItemEntityAccess;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(
            method = "method_32124",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/tick/TickManager;shouldSkipTick(Lnet/minecraft/entity/Entity;)Z")
    )
    public void shouldSkipTick(Entity entity, CallbackInfo ci) {
        if (entity instanceof AbstractItemEntityAccess) {
            ((AbstractItemEntityAccess) entity).updateDisplayInfo();
        }
    }
}
