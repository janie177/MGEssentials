package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.data.TempData;
import com.minegusta.mgessentials.ghost.GhostManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GhostCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() || sender.hasPermission("minegusta.ghostify")) {
            if (TempData.ghostMode) {
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "No more spook for you!");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "You are mere mortals once again.");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                TempData.ghostMode = false;
                GhostManager.unGhostify();
            } else {
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Be ready to get spooked!");
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Welcome to the afterlife!");
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                TempData.ghostMode = true;
                GhostManager.ghostify();
            }
        }
        return true;
    }
}
