package com.rethink.itemtool.coustomapi;

import com.rethink.itemtool.ItemTool;
import com.rethink.itemtool.config.ItemToolConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.text.Text;

import static com.rethink.itemtool.ItemTool.updateConfig;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        var instance = ItemToolConfig.CONFIG_HANDLER;
        return screen -> YetAnotherConfigLib.createBuilder()
                .title(Text.of(ItemTool.MOD_NAME))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("itemtool.settings.category"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("itemtool.settings.render"))
                                .description(OptionDescription.of(Text.translatable("itemtool.settings.render.desc")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.enable.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.enable.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemToolEnabled, newVal ->  ItemToolConfig.ItemToolEnabled = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.debug.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.debug.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemToolDebug, newVal ->  ItemToolConfig.ItemToolDebug = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.translatable("itemtool.settings.precision.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.precision.desc")))
                                        .binding(4, () -> ItemToolConfig.ItemDisplayPrecision, newVal ->  ItemToolConfig.ItemDisplayPrecision = newVal)
                                        .controller(opt -> IntegerFieldControllerBuilder.create(opt).range(0,8))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("itemtool.settings.render_range.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.render_range.desc")))
                                        .binding(10.0f, () -> ItemToolConfig.ItemToolRenderRange, newVal ->  ItemToolConfig.ItemToolRenderRange = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f,128.0f).step(0.5f))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("itemtool.settings.render_range.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.render_range.desc")))
                                        .binding(10.0f, () -> ItemToolConfig.ItemToolRenderRange, newVal ->  ItemToolConfig.ItemToolRenderRange = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f,128.0f).step(0.5f))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_velocity.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_velocity.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemVelocity, newVal ->  ItemToolConfig.ItemVelocity = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_speed.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_speed.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemSpeed, newVal ->  ItemToolConfig.ItemSpeed = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_position.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_position.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemPosition, newVal ->  ItemToolConfig.ItemPosition = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_count.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_count.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemCount, newVal ->  ItemToolConfig.ItemCount = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_on_ground.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_on_ground.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemOnGround, newVal ->  ItemToolConfig.ItemOnGround = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_age.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_age.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemAge, newVal ->  ItemToolConfig.ItemAge = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_life_span.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_life_span.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemLifeSpan, newVal ->  ItemToolConfig.ItemLifeSpan = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_pick_delay.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_pick_delay.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemPickDelay, newVal ->  ItemToolConfig.ItemPickDelay= newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_portal_cooldown.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_portal_cooldown.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemPortalCooldown, newVal ->  ItemToolConfig.ItemPortalCooldown = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build()
                        ).build()
                ).save(() -> {
                            instance.save();
                            updateConfig();
                        })
                .build()
                .generateScreen(screen);
    }
}
