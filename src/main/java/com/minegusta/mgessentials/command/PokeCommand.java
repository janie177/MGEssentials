package com.minegusta.mgessentials.command;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PokeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("poke")) {
            List<String> help = Lists.newArrayList("Wrong arguments! Use it like this:", ChatColor.GRAY + "/Poke <Name> <object>");
            List<String> wrongPlayer = Lists.newArrayList(ChatColor.RED + "Player not found!!");
            Player p = (Player) s;

            if (args.length < 2) {
                sendText(p, help);
                return true;
            }
            try {
                Player victim = Bukkit.getPlayer(args[0]);
                Joiner joiner = Joiner.on(" ").skipNulls();
                List<String> objectList = Lists.newArrayList(args);
                objectList.remove(args[0]);
                String object = joiner.join(objectList);

                p.sendMessage(ChatColor.YELLOW + "You poked " + ChatColor.ITALIC + victim.getName() + ChatColor.YELLOW + " with " + ChatColor.RED + object + ChatColor.YELLOW + "!");
                victim.sendMessage(ChatColor.YELLOW + "You were poked by " + ChatColor.ITALIC + p.getName() + ChatColor.YELLOW + " with " + ChatColor.RED + object + ChatColor.YELLOW + "!");

            } catch (Exception e) {
                sendText(p, wrongPlayer);
                return true;
            }

        }
        return true;
    }


    private void sendText(Player p, List<String> l) {
        p.sendMessage(ChatColor.RED + "------------" + ChatColor.GOLD + "Poke" + ChatColor.RED + "------------");
        for (String s : l) {
            p.sendMessage(ChatColor.RED + " - " + ChatColor.YELLOW + s);
        }
    }
}