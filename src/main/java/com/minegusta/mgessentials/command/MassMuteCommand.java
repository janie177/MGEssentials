package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.data.TempData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MassMuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() || sender.hasPermission("minegusta.massmute")) {
            if (!TempData.massMute) {
                TempData.massMute = true;
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "The server is now in muted mode!");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Only staff can talk!");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            } else {
                TempData.massMute = false;
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "The server is now un-muted!");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Everyone can talk!");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Sorry chap, you cannot use this.");
        return true;
    }
}
