package io.github.thatsmusic99.headsplus.reflection;

import com.mojang.authlib.GameProfile;
import io.github.thatsmusic99.headsplus.managers.AutograbManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class ProfileFetcher {

    public static GameProfile getProfile(Skull skull) throws IllegalAccessException {
        Field profile;
        try {
            profile = skull.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
        profile.setAccessible(true);
        return (GameProfile) profile.get(skull);
    }

    public static GameProfile getProfile(ItemStack item) throws IllegalAccessException {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        return getProfile(meta);
    }

    public static GameProfile getProfile(SkullMeta meta) throws IllegalAccessException {
        Field profile;
        try {
            profile = meta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
        profile.setAccessible(true);
        return (GameProfile) profile.get(meta);
    }

    public static GameProfile getProfile(OfflinePlayer player) {
        try {
            Method method = player.getClass().getMethod("getProfile");
            return (GameProfile) method.invoke(player);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SkullMeta setProfile(SkullMeta meta, String name) {
        UUID uuid;
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
        if (player.getUniqueId().equals(offlineUUID)) {
            String uuidStr = AutograbManager.grabUUID(name, 0, null);
            if (uuidStr == null) {
                uuid = offlineUUID;
            } else {
                uuid = UUID.fromString(uuidStr.substring(0, 8) +
                        "-" + uuidStr.substring(8, 12) +
                        "-" + uuidStr.substring(12, 16) +
                        "-" + uuidStr.substring(16, 20) +
                        "-" + uuidStr.substring(20));
            }
        } else {
            uuid = player.getUniqueId();
        }
        GameProfile profile = new GameProfile(uuid, name);
        return setProfile(meta, profile);
    }

    public static SkullMeta setProfile(SkullMeta meta, GameProfile profile) {
        try {
            Method profileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            profileMethod.setAccessible(true);
            profileMethod.invoke(meta, profile);
        } catch (NoSuchMethodException e) {
            Field profileField;
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalAccessException noSuchFieldException) {
                noSuchFieldException.printStackTrace();
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return meta;
    }

    public static <T> T getHandle(Player player) {
        try {
            Method handle = player.getClass().getDeclaredMethod("getHandle");
            // It'll be fiiiine
            return (T) handle.invoke(player);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
