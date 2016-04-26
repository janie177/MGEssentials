package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KissCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("kiss") && s.hasPermission("minegusta.kiss")) {
            try {
                Player p = Bukkit.getPlayer(args[0]);
                if (s.getName().equalsIgnoreCase(args[0])) {
                    s.sendMessage(ChatColor.LIGHT_PURPLE + "You awkwardly fold your tongue in a failed attempt to kiss yourself.");
                    return true;
                } else if (args[0].equalsIgnoreCase("Me") || args[0].equalsIgnoreCase("Myself") || args[0].equalsIgnoreCase("Animals") || args[0].equalsIgnoreCase("kiss") || args[0].equalsIgnoreCase("ladygaga")) {
                    s.sendMessage(ChatColor.DARK_RED + "No. Just no. I won't do that. Creep.");
                } else {
                    Player sender = (Player) s;
                    s.sendMessage(ChatColor.LIGHT_PURPLE + "You kissed " + ChatColor.YELLOW + p.getName());
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "You were kissed by " + ChatColor.YELLOW + s.getName());
                    p.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART);
                    sender.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART);
                }

            } catch (Exception e) {
                s.sendMessage(ChatColor.RED + "Player not found.");
            }
            return true;
        }
        return true;
    }
}
