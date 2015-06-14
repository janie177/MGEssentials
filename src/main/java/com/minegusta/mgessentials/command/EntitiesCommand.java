package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntitiesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender || !s.isOp()) {
            s.sendMessage(ChatColor.RED + "You need to be an OPed player to use this!");
            return true;
        }

        if (args.length < 1) {
            s.sendMessage(ChatColor.RED + "Usage: /Entities <world>");
            return true;
        }

        Player p = (Player) s;

        String world = args[0];
        World w = null;
        try {
            w = Bukkit.getWorld(world);
        } catch (Exception ignored) {
            s.sendMessage(ChatColor.DARK_GRAY + "That world does not exist!");
            s.sendMessage(ChatColor.RED + "Usage: /Entities <world>");
            return true;
        }

        s.sendMessage(ChatColor.RED + "- - - " + ChatColor.DARK_RED + "Entities in world: " + w.getName() + ChatColor.RED + " - - -");
        for (EntityType type : EntityType.values()) {

            int amount = 0;
            String name = type.name();

            for (Entity ent : w.getEntities()) {
                if (ent.getType() == type) {
                    amount++;
                }
            }

            p.sendMessage(ChatColor.YELLOW + "Number of " + name + ": " + getColor(amount) + amount);
        }
        return true;
    }

    private ChatColor getColor(int i) {
        if (i < 100) {
            return ChatColor.GREEN;
        }
        if (i < 500) {
            return ChatColor.GOLD;
        }

        if (i < 1000) {
            return ChatColor.RED;
        }

        return ChatColor.DARK_RED;

    }
}
