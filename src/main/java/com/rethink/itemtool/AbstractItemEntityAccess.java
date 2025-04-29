package com.rethink.itemtool;

import com.rethink.itemtool.handler.ItemDataHandler;

public interface AbstractItemEntityAccess {
    void updateDisplayInfo();

    ItemDataHandler getItemDataInfo();
}
