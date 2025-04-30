package com.rethink.itemtool.mixin;

import it.unimi.dsi.fastutil.ints.Int2ReferenceLinkedOpenHashMap;
import net.minecraft.client.network.DataQueryHandler;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(DataQueryHandler.class)
public class DataQueryHandlerMixin {
    @Shadow private int expectedTransactionId;
    private final Int2ReferenceLinkedOpenHashMap<Consumer<NbtCompound>> callbacks = new Int2ReferenceLinkedOpenHashMap<>();
    private final int MAX_SIZE = 1000;

    @Inject(
                method = "handleQueryResponse",
                at = @At(
                        value = "HEAD"
                ),
            cancellable = true)
    private void handleQueryResponse(int transactionId, NbtCompound nbt, CallbackInfoReturnable<Boolean> cir) {
            Consumer<NbtCompound> consumer = this.callbacks.remove(transactionId);
            if (consumer != null) {
                consumer.accept(nbt);
                cir.setReturnValue(true);
            }
    }

    @Inject(
            method = "nextQuery",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    private void nextQuery(Consumer<NbtCompound> callback, CallbackInfoReturnable<Integer> cir) {
        ++this.expectedTransactionId;
        this.callbacks.put(this.expectedTransactionId, callback);

        if (this.callbacks.size() >= MAX_SIZE) {
            this.callbacks.removeFirst();
        }
        cir.setReturnValue(this.expectedTransactionId);
    }
}
