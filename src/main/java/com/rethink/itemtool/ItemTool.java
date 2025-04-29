package com.rethink.itemtool;

import com.rethink.itemtool.config.ItemToolConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemTool implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("itemtool");

    public static final String MOD_ID = "itemtool";
    public static final String MOD_NAME = "Item Tool";
    public static final String VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();

    @Override
    public void onInitialize() {
        updateConfig();
        LOGGER.info(MOD_NAME + " has been initialized!");
    }

    public static void updateConfig() {
        var instance = ItemToolConfig.CONFIG_HANDLER;
        instance.load();
    }
}
