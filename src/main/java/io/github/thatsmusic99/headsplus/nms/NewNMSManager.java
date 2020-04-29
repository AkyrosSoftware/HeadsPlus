package io.github.thatsmusic99.headsplus.nms;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.util.MaterialTranslator;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public interface NewNMSManager extends NMSManager {

    default ItemStack getSkullMaterial(int amount) {
        return new ItemStack(Material.PLAYER_HEAD, amount);
    }

    default ItemStack getColouredBlock(MaterialTranslator.BlockType b, int data) {
        return new ItemStack(MaterialTranslator.toMaterial(b, data));
    }

    default Material getNewItems(MaterialTranslator.ChangedMaterials b) {
        return MaterialTranslator.getItem(b);
    }

    default Material getWallSkull() {
        return Material.PLAYER_WALL_HEAD;
    }

    default ItemStack getSkull(int data) {
        return new ItemStack(HeadTypes.getMaterial(data), 1);

    }

    @Override
    default ShapelessRecipe getRecipe(ItemStack i, String name) {
        return new ShapelessRecipe(new NamespacedKey(HeadsPlus.getInstance(), name), i);
    }

    @Override
    default ShapedRecipe getShapedRecipe(ItemStack i, String name) {
        return new ShapedRecipe(new NamespacedKey(HeadsPlus.getInstance(), name), i);
    }

    enum HeadTypes {
        SKELETON(0, Material.SKELETON_SKULL),
        WITHER(1, Material.WITHER_SKELETON_SKULL),
        ZOMBIE(2, Material.ZOMBIE_HEAD),
        PLAYER(3, Material.PLAYER_HEAD),
        CREEPER(4, Material.CREEPER_HEAD),
        DRAGON(5, Material.DRAGON_HEAD);

        int data;
        Material mat;

        HeadTypes(int d, Material m) {
            data = d;
            mat = m;
        }

        public static Material getMaterial(int data) {
            for (HeadTypes h : HeadTypes.values()) {
                if (h.data == data) {
                    return h.mat;
                }
            }
            return null;
        }
    }
}
