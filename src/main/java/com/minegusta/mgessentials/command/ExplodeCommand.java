package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExplodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.hasPermission("minegusta.explode")) {
            s.sendMessage("Nice try " + s.getName() + ", no permissions for you >:3");
        } else {
            try {
                Player target = Bukkit.getPlayer(args[0]);
                target.getWorld().createExplosion(target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ(), 4F, false, false);
                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + s.getName() + " Exploded " + target.getName() + "! That'll teach them.");
            } catch (Exception e) {
                s.sendMessage("Player is not real or not online. Try again.");
            }
        }
        return true;
    }
}
