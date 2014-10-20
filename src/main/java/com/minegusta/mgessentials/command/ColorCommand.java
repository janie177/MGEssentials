package com.minegusta.mgessentials.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ColorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("color")) {
            if (s instanceof ConsoleCommandSender) return false;
            else {
                Player p = (Player) s;
                sendColors(p);
            }
        }
        return true;
    }

    private void sendColors(Player p) {
        p.sendMessage(ChatColor.RED + "---------------" + ChatColor.GOLD + "Colors" + ChatColor.RED + "---------------");
        p.sendMessage("& " + ChatColor.DARK_BLUE + "1" + ChatColor.DARK_GREEN + "2" + ChatColor.DARK_AQUA + "3" + ChatColor.DARK_RED + "4" + ChatColor.DARK_PURPLE + "5" + ChatColor.GOLD + "6" + ChatColor.GRAY + "7" + ChatColor.DARK_GRAY + "8" + ChatColor.BLUE + "9" + ChatColor.BLACK + "0" + ChatColor.GREEN + "a" + ChatColor.AQUA + "b" + ChatColor.RED + "c" + ChatColor.LIGHT_PURPLE + "d" + ChatColor.YELLOW + "e");
        p.sendMessage("& " + ChatColor.ITALIC + "o" + ChatColor.BOLD + "l" + ChatColor.STRIKETHROUGH + "n" + ChatColor.UNDERLINE + "m");

    }

}
