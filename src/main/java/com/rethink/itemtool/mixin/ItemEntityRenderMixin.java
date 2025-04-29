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
public abstract class ItemEntityRenderMixin extends EntityRenderer<ItemEntity> {
    protected ItemEntityRenderMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(
            method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("HEAD")
    )
    private void onRender(ItemEntity itemEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
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
                matrices.scale(0.025f, -0.0255f, 0.025f);
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                float h = -textRenderer.getWidth(infoText) / 2f;

                textRenderer.draw(infoText, h, yOffset, 0x20FFFFF, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, backgroundColor, 15728640);
                textRenderer.draw(infoText, h, yOffset, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, 15728640);

                matrices.pop();

                yOffset += 10;
            }
        }
    }
}
