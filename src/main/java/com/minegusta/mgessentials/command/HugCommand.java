package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hug") && sender.hasPermission("minegusta.hug")) {
            try {
                if (args[0].equals("*") && sender.hasPermission("hug.all")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "You are now hugging " + online.getName());
                        online.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " hugged you!");
                        online.getWorld().spigot().playEffect(online.getLocation(), Effect.HEART, 1, 1, 1F, 1F, 1F, 1F, 80, 15);
                    }
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "You are now hugging " + target.getName());
                    target.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " hugged you!");
                    target.getWorld().spigot().playEffect(target.getLocation(), Effect.HEART, 1, 1, 1F, 1F, 1F, 1F, 80, 15);
                }
            } catch (Exception e) {
                sender.sendMessage("Invalid player :c");
            }

        }
        return true;
    }

}
