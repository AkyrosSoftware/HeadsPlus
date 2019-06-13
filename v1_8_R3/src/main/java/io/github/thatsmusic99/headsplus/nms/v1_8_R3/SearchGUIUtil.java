package io.github.thatsmusic99.headsplus.nms.v1_8_R3;

import io.github.thatsmusic99.headsplus.nms.SearchGUI;
import io.github.thatsmusic99.headsplus.util.AnvilSlot;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SearchGUIUtil extends SearchGUI {

    public SearchGUIUtil(Player player, SearchGUI.AnvilClickEventHandler handler) {
        super(player, handler);
    }

    private class AnvilContainer extends ContainerAnvil {

        public AnvilContainer(EntityHuman entity) {
            super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
        }

        @Override
        public boolean a(EntityHuman entityhuman) {
            return true;
        }

        @Override
        public void e() {
            super.e();
            this.a = 0;
        }
    }

    @Override
    public void open() {
        EntityPlayer p = ((CraftPlayer) getPlayer()).getHandle();

        AnvilContainer container = new AnvilContainer(p);

        //Set the items to the items from the inventory given
        inv = container.getBukkitView().getTopInventory();

        for (AnvilSlot slot : items.keySet()) {
            inv.setItem(slot.getSlot(), items.get(slot));
        }

        //Counter stuff that the game uses to keep track of inventories
        int c = p.nextContainerCounter();

        //Send the packet
        p.playerConnection.sendPacket(new PacketPlayOutOpenWindow(c, "minecraft:anvil", new ChatMessage("Repairing"), 0));
        //Set their active container to the container
        p.activeContainer = container;

        //Set their active container window id to that counter stuff
        p.activeContainer.windowId = c;

        //Add the slot listener
        p.activeContainer.addSlotListener(p);
    }
}
