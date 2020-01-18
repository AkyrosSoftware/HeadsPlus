package io.github.thatsmusic99.headsplus.commands.maincommand.lists;

import io.github.thatsmusic99.headsplus.commands.CommandInfo;
import io.github.thatsmusic99.headsplus.commands.maincommand.DebugPrint;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMainConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public abstract class AbstractListToggle extends AbstractListCommand {

    @Override
    public String isCorrectUsage(String[] args, CommandSender sender) {
        return "";
    }

    public abstract HeadsPlusMainConfig.SelectorList getSelList();

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        try {
            if (args.length == 1) {
                config.getConfig().set(getPath(), getSelList().enabled = !getSelList().enabled);
                config.save();
                sender.sendMessage(hpc.getString("commands." + getFullName() + "." + (getSelList().enabled ? getListType() + "-on" : getListType() + "-off"), sender));
            } else {
                if (args[1].equalsIgnoreCase("on")) {
                    if (!getSelList().enabled) {
                        config.getConfig().set(getPath(), getSelList().enabled = true);
                        config.save();
                        sender.sendMessage(hpc.getString("commands." + getFullName() + "." + getListType() + "-on", sender));
                    } else {
                        sender.sendMessage(hpc.getString("commands." + getFullName() + "." + getListType() + "-a-on", sender));
                    }

                } else if (args[1].equalsIgnoreCase("off")) {
                    if (getSelList().enabled) {
                        config.getConfig().set(getPath(), getSelList().enabled = false);
                        config.save();
                        sender.sendMessage(hpc.getString("commands." + getFullName() + "." + getListType() + "-off", sender));
                    } else {
                        sender.sendMessage(hpc.getString("commands." + getFullName() + "." + getListType() + "-a-off", sender));
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + getClass().getAnnotation(CommandInfo.class).usage());
                }
            }
        } catch (Exception e) {
            DebugPrint.createReport(e, "Subcommand (" + getClass().getAnnotation(CommandInfo.class).commandname() + ")", true, sender);
        }
        return true;
    }
}
