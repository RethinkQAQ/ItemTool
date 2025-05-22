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

package com.rethink.itemtool.config;

import com.rethink.itemtool.ItemTool;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static com.rethink.itemtool.ItemTool.updateConfig;

public class SettingScreen {
    public static Screen Menu(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
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
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.meter_per_second.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.meter_per_second.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemVelocityMeterPerSecond, newVal ->  ItemToolConfig.ItemVelocityMeterPerSecond = newVal)
                                        .controller(TickBoxControllerBuilder::create)
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
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("itemtool.settings.item_is_simulation_tick.name"))
                                        .description(OptionDescription.of(Text.translatable("itemtool.settings.item_is_simulation_tick.desc")))
                                        .binding(false, () -> ItemToolConfig.ItemIsSimulationTick, newVal ->  ItemToolConfig.ItemIsSimulationTick = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build()
                        ).build()
                ).save(() -> {
                    ItemToolConfig.CONFIG_HANDLER.save();
                    updateConfig();
                })
                .build()
                .generateScreen(parent);
    }
}
