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

package com.rethink.itemtool;

import com.rethink.itemtool.config.ItemToolConfig;
import com.rethink.itemtool.config.SettingScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class ItemTool implements ModInitializer {

    public static KeyBinding keyBinding;

    public static final Logger LOGGER = LogManager.getLogger("itemtool");

    public static final String MOD_ID = "itemtool";
    public static final String MOD_NAME = "Item Tool";
    public static final String VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();

    @Override
    public void onInitialize() {
        keyBinding = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "itemtool.key.category",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_F9,
                        "itemtool.key.menu"
                )
        );
        ClientTickEvents.END_CLIENT_TICK.register( client -> {
            if (keyBinding.isPressed()) {
                MinecraftClient.getInstance().setScreen(
                        SettingScreen.Menu(MinecraftClient.getInstance().currentScreen)
                );
            }
        });
        updateConfig();
        LOGGER.info(MOD_NAME + " has been initialized!");
    }

    public static void updateConfig() {
        var instance = ItemToolConfig.CONFIG_HANDLER;
        instance.load();
    }
}
