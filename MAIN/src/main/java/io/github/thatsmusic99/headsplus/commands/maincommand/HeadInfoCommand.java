package io.github.thatsmusic99.headsplus.commands.maincommand;

import io.github.thatsmusic99.headsplus.HeadsPlus;
import io.github.thatsmusic99.headsplus.commands.CommandInfo;
import io.github.thatsmusic99.headsplus.commands.IHeadsPlusCommand;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigHeads;
import io.github.thatsmusic99.headsplus.config.HeadsPlusConfigTextMenu;
import io.github.thatsmusic99.headsplus.config.HeadsPlusMessagesManager;
import org.bukkit.DyeColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

@CommandInfo(
        commandname = "headinfo",
        permission = "headsplus.maincommand.headinfo",
        subcommand = "Headinfo",
        maincommand = true,
        usage = "/hp headinfo <view> <Entity Type> [Name|Mask|Lore] [Page]"
)
public class HeadInfoCommand implements IHeadsPlusCommand {

    // A
    private final HeadsPlusMessagesManager hpc = HeadsPlus.getInstance().getMessagesConfig();

    @Override
    public String getCmdDescription(CommandSender sender) {
        return hpc.getString("descriptions.hp.headinfo", sender);
    }

    @Override
    public String[] advancedUsages() {
        String[] s = new String[4];
        s[0] = "/hp headinfo view <Entity Type> [Name|Mask|Lore] [Page]";
        s[1] = "/hp headinfo set <Entity Type> <Chance|Price|Display-name|Interact-name> <Value>";
        s[2] = "/hp headinfo add <Entity Type> <Name|Mask|Lore> <Value|Effect> [Colour|Amplifier]";
        s[3] = "/hp headinfo remove <Entity Type> <Name|Mask|Lore> <Index> [Colour]";
        return s;
    }

    @Override
    public String isCorrectUsage(String[] args, CommandSender sender) {
        HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
        Player p = sender instanceof Player ? (Player) sender : null;
        if (args.length > 2) {
            if (args[1].equalsIgnoreCase("view")) {
                if (hpch.mHeads.contains(args[2].toLowerCase())
                        || hpch.uHeads.contains(args[2].toLowerCase())
                        || args[2].equalsIgnoreCase("player")) {
                    if (args.length > 3) {
                        if (args[3].equalsIgnoreCase("name") || args[3].equalsIgnoreCase("lore") || args[3].equalsIgnoreCase("mask")) {
                            if (args.length > 4) {
                                if (args[4].matches("^[0-9]+$")) {
                                    return "";
                                } else {
                                    return hpc.getString("commands.errors.invalid-input-int", p);
                                }
                            } else {
                                return "";
                            }
                        } else {
                            return hpc.getString("commands.errors.invalid-args", p);
                        }
                    } else {
                        return "";
                    }
                } else {
                    return hpc.getString("commands.errors.invalid-args", p);
                }
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("chance")
                                || args[3].equalsIgnoreCase("price")
                                || args[3].equalsIgnoreCase("display-name")
                                || args[3].equalsIgnoreCase("interact-name")) {
                            if (isValueValid(args[3], args[4])) {
                                return "";
                            }
                        }
                    }
                }
                return hpc.getString("commands.errors.invalid-args", p);
            } else if (args[1].equalsIgnoreCase("add")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("name")
                                || args[3].equalsIgnoreCase("lore")
                                || args[3].equalsIgnoreCase("mask")) {
                            if (args[3].equalsIgnoreCase("mask")) {
                                if (PotionEffectType.getByName(args[4]) != null) {
                                    if (args.length > 5) {
                                        if (args[5].matches("^[0-9]+$")) {
                                            return "";
                                        } else {
                                            return hpc.getString("commands.errors.invalid-args", p);
                                        }
                                    } else {
                                        return "";
                                    }
                                } else {
                                    return hpc.getString("commands.errors.invalid-args", p);
                                }
                            } else if (args[2].equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                                if (args.length > 5) {
                                    try {
                                        DyeColor.valueOf(args[5].toUpperCase());
                                        return "";
                                    } catch (Exception e) {
                                        return hpc.getString("commands.errors.invalid-args", p);
                                    }
                                } else {
                                    return "";
                                }
                            } else {
                                return "";
                            }
                        } else {
                            return hpc.getString("commands.errors.invalid-args", p);
                        }
                    } else {
                        return hpc.getString("commands.errors.invalid-args", p);
                    }
                } else {
                    return hpc.getString("commands.errors.invalid-args", p);
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length > 4) {
                    if (hpch.mHeads.contains(args[2].toLowerCase())
                            || hpch.uHeads.contains(args[2].toLowerCase())
                            || args[2].equalsIgnoreCase("player")) {
                        if (args[3].equalsIgnoreCase("name")
                                || args[3].equalsIgnoreCase("lore")
                                || args[3].equalsIgnoreCase("mask")) {
                            if (args[4].matches("^[0-9]+$")) {
                                if (args[2].equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                                    if (args.length > 6) {
                                        try {
                                            DyeColor.valueOf(args[6]);
                                            return "";
                                        } catch (Exception e) {
                                            return hpc.getString("commands.errors.invalid-args", p);
                                        }
                                    } else {
                                        return "";
                                    }
                                } else {
                                    return "";
                                }
                            } else {
                                return hpc.getString("commands.errors.invalid-args", p);
                            }
                        } else {
                            return hpc.getString("commands.errors.invalid-args", p);
                        }
                    } else {
                        return hpc.getString("commands.errors.invalid-args", p);
                    }
                } else {
                    return hpc.getString("commands.errors.invalid-args", p);
                }
            } else {
                return hpc.getString("commands.errors.invalid-args", p);
            }
        } else {
            return hpc.getString("commands.errors.invalid-args", p);
        }
    }

    @Override
    public boolean fire(String[] args, CommandSender sender) {
        try {
            String type = args[2];
            if (args.length > 3) {

                HeadsPlusConfigHeads hpch = HeadsPlus.getInstance().getHeadsConfig();
                if (args[1].equalsIgnoreCase("view")) {
                    if (args[3].equalsIgnoreCase("name")) {
                        if (args.length > 4) {
                            sender.sendMessage(printNameInfo(type, Integer.parseInt(args[4]), sender));
                        } else {
                            sender.sendMessage(printNameInfo(type, 1, sender));
                        }
                    } else if (args[3].equalsIgnoreCase("mask")) {
                        if (args.length > 4) {
                            sender.sendMessage(HeadsPlusConfigTextMenu.HeadInfoTranslator.translateMaskInfo(sender, type, Integer.parseInt(args[4])));
                        } else {
                            sender.sendMessage(HeadsPlusConfigTextMenu.HeadInfoTranslator.translateMaskInfo(sender, type, 1));
                        }
                    } else if (args[3].equalsIgnoreCase("lore")) {
                        if (args.length > 4) {
                            sender.sendMessage(HeadsPlusConfigTextMenu.HeadInfoTranslator.translateLoreInfo(sender, type, Integer.parseInt(args[4])));
                        } else {
                            sender.sendMessage(HeadsPlusConfigTextMenu.HeadInfoTranslator.translateLoreInfo(sender, type, 1));
                        }
                    } else {
                        sender.sendMessage(HeadsPlusConfigTextMenu.HeadInfoTranslator.translateNormal(type));
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("set")) {
                    if (args[3].equalsIgnoreCase("price")) {
                        hpch.getConfig().set(type + "." + args[3], Double.valueOf(args[4]));
                    } else if (args[3].equalsIgnoreCase("chance")) {
                        hpch.getConfig().set(type + "." + args[3], Integer.parseInt(args[4]));
                    } else {
                        hpch.getConfig().set(type + "." + args[3], args[4]);
                    }

                    sender.sendMessage(hpc.getString("commands.headinfo.set-value", sender)
                            .replaceAll("\\{value}", args[4])
                            .replaceAll("\\{entity}", type)
                            .replaceAll("\\{setting}", args[3]));
                } else if (args[1].equalsIgnoreCase("add")) {
                    if (type.equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                        if (args.length > 5) {
                            List<String> s = hpch.getConfig().getStringList(type + ".name." + args[5].toUpperCase());
                            s.add(args[4]);
                            hpch.getConfig().set(type + ".name." + args[5].toUpperCase(), s);

                        } else {
                            List<String> s = hpch.getConfig().getStringList(type + ".name.default");
                            s.add(args[4]);
                            hpch.getConfig().set(type + ".name.default", s);
                        }
                    } else {
                        List<String> s;
                        if (args[3].equalsIgnoreCase("mask")) {
                            List<Integer> st = hpch.getConfig().getIntegerList(type + ".mask-amplifiers");
                            if (args.length > 5) {
                                st.add(Integer.parseInt(args[5]));
                            } else {
                                st.add(1);
                            }
                            hpch.getConfig().set(type + ".mask-amplifiers", st);
                            s = hpch.getConfig().getStringList(type + ".mask-effects");
                            s.add(args[4]);
                            hpch.getConfig().set(type + ".mask-effects", s);
                        } else {
                            s = hpch.getConfig().getStringList(type + "." + args[3]);
                            s.add(args[4]);
                            hpch.getConfig().set(type + "." + args[3], s);
                        }
                    }

                    sender.sendMessage(hpc.getString("commands.headinfo.add-value", sender)
                            .replaceAll("\\{value}", args[4])
                            .replaceAll("\\{entity}", type)
                            .replaceAll("\\{setting}", args[3]));

                } else if (args[1].equalsIgnoreCase("remove")) {
                    List<String> s;
                    int p = Integer.parseInt(args[4]);
                    String value;
                    if (type.equalsIgnoreCase("sheep") && args[3].equalsIgnoreCase("name")) {
                        if (args.length > 5) {
                            s = hpch.getConfig().getStringList(type + ".name." + args[5].toUpperCase());
                            value = s.get(p);
                            s.remove(p);
                            hpch.getConfig().set(type + ".name." + args[5].toUpperCase(), s);
                        } else {
                            s = hpch.getConfig().getStringList(type + ".name.default");
                            value = s.get(p);
                            s.remove(p);
                            hpch.getConfig().set(type + ".name.default", s);
                        }
                    } else {

                        if (args[3].equalsIgnoreCase("mask")) {
                            s = hpch.getConfig().getStringList(type + ".mask-effects");
                            List<Integer> st = hpch.getConfig().getIntegerList(type + ".mask-amplifiers");
                            st.remove(p);
                            hpch.getConfig().set(type + ".mask-amplifiers", st);
                            value = s.get(p);
                            s.remove(p);
                            hpch.getConfig().set(type + ".mask-effects", s);
                        } else {
                            s = hpch.getConfig().getStringList(type + "." + args[3]);
                            value = s.get(p);
                            s.remove(p);
                            hpch.getConfig().set(type + "." + args[3], s);
                        }

                    }

                    sender.sendMessage(hpc.getString("commands.headinfo.remove-value", sender)
                            .replaceAll("\\{value}", value)
                            .replaceAll("\\{entity}", type)
                            .replaceAll("\\{setting}", args[3]));
                }
                hpch.getConfig().options().copyDefaults(true);
                hpch.save();
                return true;
            } else {
                sender.sendMessage(HeadsPlusConfigTextMenu.HeadInfoTranslator.translateNormal(type));
            }
        } catch (IndexOutOfBoundsException ex) {
            sender.sendMessage(hpc.getString("commands.errors.invalid-args", sender));
        } catch (Exception e) {
            DebugPrint.createReport(e, "Head Information command", true, sender);
        }

        return false;
    }

    private boolean isValueValid(String option, String value) {
        if (option.equalsIgnoreCase("chance") || option.equalsIgnoreCase("price")) {
            try {
                Double.valueOf(value);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        } else {
            return true;
        }
    }

    private String printNameInfo(String type, int page, CommandSender p) {
        try {
            if (type.equalsIgnoreCase("sheep")
                    || type.equalsIgnoreCase("parrot")
                    || type.equalsIgnoreCase("llama")
                    || type.equalsIgnoreCase("horse")) {
                return HeadsPlusConfigTextMenu.HeadInfoTranslator.translateColored(p, type, page);
            } else {
                return HeadsPlusConfigTextMenu.HeadInfoTranslator.translateNormal(type);
            }
        } catch (IllegalArgumentException ex) {
            return hpc.getString("commands.errors.invalid-pg-no", p);
        }

    }
}
