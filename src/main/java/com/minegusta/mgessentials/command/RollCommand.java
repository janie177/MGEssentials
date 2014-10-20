package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class RollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("minegusta.roll")) {
            commandSender.sendMessage("You do not have permissions to roll. They won't see you rollin'.");
        } else if (command.getName().equalsIgnoreCase("roll")) {
            Random random = new Random();
            int number = random.nextInt(50) + 1;
            Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "-----------------------------");
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + commandSender.getName() + " rolled " + ChatColor.DARK_RED + number);
            Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "-----------------------------");
        }
        return true;
    }
}
