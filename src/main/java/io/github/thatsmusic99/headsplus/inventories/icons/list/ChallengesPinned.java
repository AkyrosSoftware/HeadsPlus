package io.github.thatsmusic99.headsplus.inventories.icons.list;

import io.github.thatsmusic99.headsplus.inventories.Icon;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChallengesPinned extends Icon {

    public ChallengesPinned(Player player) {
        super(player);
    }

    @Override
    public boolean onClick(Player player, InventoryClickEvent event) {

        return false;
    }

    @Override
    public String getId() {
        return null;
    }
}
