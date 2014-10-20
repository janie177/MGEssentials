package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HailCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hail") && args.length > 0 && sender.isOp()) {
            String hail = "";
            for (String str : args) {
                hail = hail + " " + str;
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.chat(ChatColor.RED + "ALL HAIL" + ChatColor.YELLOW + hail + ChatColor.RED + "!");
            }
            return true;
        }
        return false;
    }
}
