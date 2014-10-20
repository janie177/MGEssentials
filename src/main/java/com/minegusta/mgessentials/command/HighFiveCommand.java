package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HighFiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("highfive") && sender.hasPermission("minegusta.highfive") && !(sender instanceof ConsoleCommandSender)) {
            try {
                Player player1 = (Player) sender;
                Player player2 = Bukkit.getPlayer(args[0]);
                Location loc1 = player1.getLocation();
                Location loc2 = player2.getLocation();
                if (loc1.distance(loc2) > 2.2) {
                    sender.sendMessage(ChatColor.GOLD + "You are too far away to give a High Five!");
                } else {
                    Bukkit.broadcastMessage(ChatColor.BLUE + player1.getName() + " and " + player2.getName() + " High Fived!");
                    player2.getWorld().spigot().playEffect(loc2, Effect.CRIT, 1, 1, 0F, 1F, 0F, 1F, 60, 10);
                    player1.getWorld().spigot().playEffect(loc1, Effect.CRIT, 1, 1, 0F, 1F, 0F, 1F, 60, 10);
                    return true;

                }
            } catch (Exception e) {
                sender.sendMessage("Player not found. Try harder! And even if you fail again, atleast you tried!");
            }
        }
        return false;
    }
}