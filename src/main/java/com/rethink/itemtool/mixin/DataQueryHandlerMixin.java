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
