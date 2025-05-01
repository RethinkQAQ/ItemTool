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

package com.rethink.itemtool.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class ItemToolConfig {
    public static ConfigClassHandler<ItemToolConfig> CONFIG_HANDLER = ConfigClassHandler.createBuilder(ItemToolConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("itemtool.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();
    @SerialEntry
    public static boolean ItemToolEnabled = false;

    @SerialEntry
    public static boolean ItemToolDebug = false;

    @SerialEntry
    public static int ItemDisplayPrecision = 4;

    @SerialEntry
    public static float ItemToolRenderRange = 10.0f;

    @SerialEntry
    public static boolean ItemToolStopWhenTickWarp = true;

    @SerialEntry
    public static boolean ItemVelocityMeterPerSecond = false;

    @SerialEntry
    public static boolean ItemVelocity = false;

    @SerialEntry
    public static boolean ItemSpeed = false;

    @SerialEntry
    public static boolean ItemCount = false;

    @SerialEntry
    public static boolean ItemPosition  = false;

    @SerialEntry
    public static boolean ItemOnGround = false;

    @SerialEntry
    public static boolean ItemAge = false;

    @SerialEntry
    public static boolean ItemLifeSpan = false;

    @SerialEntry
    public static boolean ItemPickDelay = false;

    @SerialEntry
    public static boolean ItemPortalCooldown = false;

    @SerialEntry
    public static boolean ItemIsSimulationTick = false;
}
