package com.minegusta.mgessentials.command;

import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class RenameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rename") && sender.hasPermission("minegusta.donator") && sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/Rename " + ChatColor.YELLOW + "<New Name> " + ChatColor.DARK_GRAY + ": Lets you rename the held item. Color codes supported!");
                sender.sendMessage(ChatColor.GOLD + "/desc " + ChatColor.YELLOW + "<New Description> " + ChatColor.DARK_GRAY + ": Lets you rename the held item's info. Color codes supported!");
                return true;

            } else {
                if (p.getItemInHand().getType().equals(Material.AIR)) {
                    p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "I'm not going to rename your hand!");
                    return true;
                } else {

                    Joiner joiner = Joiner.on(" ").skipNulls();
                    String newName = joiner.join(args);
                    if (newName.length() > 62) {
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "That name is too long! It would crash players.");
                        return true;
                    } else {

                        ItemStack item = p.getItemInHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', newName));
                        item.setItemMeta(meta);
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.AQUA + "Successfully renamed item name!");
                        return true;
                    }
                }
            }

        }
        if (command.getName().equalsIgnoreCase("desc") && sender.hasPermission("minegusta.donator") && sender instanceof Player && sender.isOp()) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/desc " + ChatColor.YELLOW + "<New Description> " + ChatColor.DARK_GRAY + ": Lets you rename the held item's info. Color codes supported!");
                sender.sendMessage(ChatColor.GOLD + "/Rename " + ChatColor.YELLOW + "<New Name> " + ChatColor.DARK_GRAY + ": Lets you rename the held item. Color codes supported!");
                return true;
            } else {
                if (p.getItemInHand().getType().equals(Material.AIR)) {
                    p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "I'm not going to rename your hand!");
                    return true;
                } else {
                    Joiner joiner = Joiner.on(" ").skipNulls();
                    String newName = joiner.join(args);
                    if (newName.length() > 64) {
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "That description is too long! It would crash players.");
                        return true;
                    } else if (newName.contains("Mystery Box")) return false;
                    else if (newName.contains("Helmet Of The Fat Man")) return false;
                    else if (newName.contains("Pants Of Life")) return false;
                    else if (newName.contains("Blade Of The Slayer")) return false;
                    else if (newName.contains("Boots Of The Swift")) return false;
                    else if (newName.contains("God Apple")) return false;
                    else if (newName.contains("Platebody Of Darkness")) return false;
                    else if (p.getItemInHand().getType().equals(Material.MOB_SPAWNER)) {
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "You cannot change a mob spawner's meta!");
                    } else {

                        ArrayList<String> lore = new ArrayList<String>();
                        lore.clear();
                        lore.add(ChatColor.translateAlternateColorCodes('&', newName));


                        ItemStack item = p.getItemInHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.AQUA + "Successfully renamed item description!");
                        return true;
                    }
                }
            }
        }
        return true;
    }

}
