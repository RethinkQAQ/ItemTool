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

package com.rethink.itemtool.handler;

import com.rethink.itemtool.config.ItemToolConfig;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public record ItemDataHandler(Text name, Vec3d pos, Vec3d velocity, double speed, boolean onGround, int age, int lifeSpan,
                              int pickUpDelay, int portalCooldown, int count, boolean isSimulationTick) {
    public static ItemDataHandler formNBT(ItemEntity entity, NbtCompound nbt) {

        Text name = entity.getName();

        //#if MC < 12105
        NbtList nbtPos = nbt.getList("Pos", 6);
        NbtList nbtVelocity = nbt.getList("Motion", 6);
        Vec3d velocity = new Vec3d(
                nbtVelocity.getDouble(0),
                nbtVelocity.getDouble(1),
                nbtVelocity.getDouble(2)
        );
        //#else
        //$$ Vec3d velocity = nbt.get("Motion",Vec3d.CODEC).orElse(Vec3d.ZERO);
        //$$ Vec3d pos = nbt.get("Pos",Vec3d.CODEC).orElse(Vec3d.ZERO);
        //#endif

        velocity = ItemToolConfig.ItemVelocityMeterPerSecond ? velocity.multiply(20) : velocity;

        double speed = velocity.horizontalLengthSquared();
        //#if MC < 12105
        Vec3d pos = new Vec3d(nbtPos.getDouble(0), nbtPos.getDouble(1), nbtPos.getDouble(2));
        //#endif

        boolean onGround = nbt.getBoolean(
                "OnGround"
                //#if MC >=12105
                //$$ , false
                //#endif
        );
        int age = nbt.getInt(
                "Age"
                //#if MC >= 12105
                //$$ ,0
                //#endif
        );
        int lifeSpan = 6000 - age;
        int pickupDelay = nbt.getInt(
                "PickupDelay"
                //#if MC >=12105
                //$$ , 0
                //#endif
        );
        int portalCooldown = nbt.getInt(
                "PortalCooldown"
                //#if MC >=12105
                //$$ , 0
                //#endif
        );
        int count = nbt
                //#if MC >= 12105
                //$$ .getCompoundOrEmpty("Item")
                //#else
                .getCompound("Item")
                //#endif
                .getInt(
                        "count"
                        //#if MC >= 12105
                        //$$ , 0
                        //#endif
                );

        boolean isSimulationTick = getIsSimulationTick(entity.getId(), age, speed, onGround);

        return new ItemDataHandler(name, pos, velocity, speed, onGround, age, lifeSpan, pickupDelay, portalCooldown, count, isSimulationTick);
    }

    public static boolean getIsSimulationTick(int id, int age, double speed, boolean onGround) {
        return !onGround || speed > 1.0E-5F || (id + age) % 4 == 0;
    }

    public ArrayList<Text> getInfoTexts() {
        ArrayList<Text> infoTexts = new ArrayList<>();
        if (ItemToolConfig.ItemName) {
            infoTexts.add(this.name());
        }
        if (ItemToolConfig.ItemVelocity && this.velocity() != null) {
            infoTexts.add(this.getVelocityText());
        }
        if (ItemToolConfig.ItemSpeed) {
            infoTexts.add(this.getSpeedText());
        }
        if (ItemToolConfig.ItemPosition) {
            infoTexts.add(this.getPosText());
        }
        if (ItemToolConfig.ItemCount && this.count() != 0) {
            infoTexts.add(this.getCountText());
        }
        if (ItemToolConfig.ItemOnGround) {
            infoTexts.add(this.getOnGroundText());
        }
        if (ItemToolConfig.ItemAge && this.age() != 0) {
            infoTexts.add(this.getAgeText());
        }
        if (ItemToolConfig.ItemLifeSpan) {
            infoTexts.add(this.getLifeSpaText());
        }
        if (ItemToolConfig.ItemPickDelay) {
            infoTexts.add(this.getPickDelayText());
        }
        if (ItemToolConfig.ItemPortalCooldown) {
            infoTexts.add(this.getPortalCooldownText());
        }
        if (ItemToolConfig.ItemIsSimulationTick) {
            infoTexts.add(this.getIsSimulationTickText());
        }
        return infoTexts;
    }

    private Text getIsSimulationTickText() {
        return Text.translatable("itemtool.settings.item_is_simulation_tick").append(Text.of(String.valueOf(this.isSimulationTick)));
    }

    private Text getPosText() {
        return Text.translatable("itemtool.settings.item_position").append(formatVec3d(this.pos));
    }

    private Text getSpeedText() {
        return Text.translatable("itemtool.settings.item_speed").append(Text.of(String.format(getDoubleFormatString(), this.speed)));
    }

    private Text getPortalCooldownText() {
        return Text.translatable("itemtool.settings.item_portal_cooldown").append(Text.of(String.valueOf(this.portalCooldown)));
    }

    private Text getPickDelayText() {
        return Text.translatable("itemtool.settings.item_pick_delay").append(Text.of(String.valueOf(this.pickUpDelay)));
    }

    private Text getLifeSpaText() {
        return Text.translatable("itemtool.settings.item_life_span").append(Text.of(String.valueOf(this.lifeSpan)));
    }

    private Text getAgeText() {
        return Text.translatable("itemtool.settings.item_age").append(Text.of(String.valueOf(this.age)));
    }

    private Text getOnGroundText() {
        return Text.translatable("itemtool.settings.item_on_ground").append(Text.of(String.valueOf(this.onGround)));
    }

    private Text getCountText() {
        if (this.count == 0) {
            return Text.translatable("itemtool.settings.item_count").append(Text.translatable("itemtool.unknown"));
        }
        return Text.translatable("itemtool.settings.item_count").append(Text.of(String.valueOf(this.count)));
    }

    private Text getVelocityText() {
        if (this.velocity == null) {
            return Text.translatable("itemtool.settings.item_velocity").append(Text.translatable("itemtool.unknown"));
        }
        return Text.translatable("itemtool.settings.item_velocity").append(formatVec3d(this.velocity));
    }

    private static String formatVec3d(Vec3d vec) {
        return "[" + String.format(getDoubleFormatString(), vec.x) + ", "
                + String.format(getDoubleFormatString(), vec.y) + ", "
                + String.format(getDoubleFormatString(), vec.z) + "]";
    }

    private static String getDoubleFormatString() {
        return "%." + Math.abs(ItemToolConfig.ItemDisplayPrecision) + "f";
    }
}
