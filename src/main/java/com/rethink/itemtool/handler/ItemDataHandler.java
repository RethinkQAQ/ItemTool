package com.rethink.itemtool.handler;

import com.rethink.itemtool.config.ItemToolConfig;
import com.rethink.itemtool.AbstractItemEntityAccess;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public record ItemDataHandler(Vec3d pos, Vec3d velocity, double speed, boolean onGround, int age, int lifeSpan,
                              int pickUpDelay, int portalCooldown, int count) {
    public static ItemDataHandler formNBT(AbstractItemEntityAccess entity, NbtCompound nbt) {
        NbtList nbtPos = nbt.getList("Pos", 6);
        NbtList nbtVelocity = nbt.getList("Motion", 6);
        double x = nbtVelocity.getDouble(0);
        double y = nbtVelocity.getDouble(1);
        double z = nbtVelocity.getDouble(2);
        Vec3d velocity = new Vec3d(x, y, z);
        double speed = Math.sqrt(x * x + z * z);
        Vec3d pos = new Vec3d(nbtPos.getDouble(0), nbtPos.getDouble(1), nbtPos.getDouble(2));

        boolean onGround = nbt.getBoolean("OnGround");
        int age = nbt.getInt("Age");
        int lifeSpan = 6000 - age;
        int pickupDelay = nbt.getInt("PickupDelay");
        int portalCooldown = nbt.getInt("PortalCooldown");
        int count = nbt.getCompound("Item").getInt("count");

        return new ItemDataHandler(pos, velocity, speed, onGround, age, lifeSpan, pickupDelay, portalCooldown, count);
    }

    public ArrayList<Text> getInfoTexts() {
        ArrayList<Text> infoTexts = new ArrayList<>();
        if (ItemToolConfig.ItemVelocity && this.velocity() != null) {
            infoTexts.add(this.getVelocityText());
        }
        if (ItemToolConfig.ItemSpeed){
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
        return infoTexts;
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
        return "[" + String.format(getDoubleFormatString(), ItemToolConfig.ItemVelocityMeterPerSecond? vec.x * 20 : vec.x) + ", "
                + String.format(getDoubleFormatString(), ItemToolConfig.ItemVelocityMeterPerSecond? vec.y * 20 : vec.y) + ", "
                + String.format(getDoubleFormatString(), ItemToolConfig.ItemVelocityMeterPerSecond? vec.z * 20 : vec.z) + "]";    }

    private static String getDoubleFormatString() {
        return "%." + Math.abs(ItemToolConfig.ItemDisplayPrecision) + "f";
    }
}
