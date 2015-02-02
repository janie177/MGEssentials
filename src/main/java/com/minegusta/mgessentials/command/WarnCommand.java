package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if ((!s.isOp() && !s.hasPermission("minegusta.warn")) || args.length < 1) {
            s.sendMessage(ChatColor.RED + "Usage: " + ChatColor.DARK_RED + "/Warn <Name> [Reason]");
        } else {

            String warning = "";

            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    warning = warning + " " + args[i];
                }
            } else warning = "No reason specified. But you probably did something bad...";

            if (args[0].equals("*")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    warnPlayer(p, warning);
                }
                s.sendMessage(ChatColor.YELLOW + "You warned everyone.");
                return true;
            }
            try {
                Player victim = Bukkit.getPlayer(args[0]);

                warnPlayer(victim, warning);

                s.sendMessage(ChatColor.YELLOW + "You warned " + victim.getName() + ".");
            } catch (Exception ignored) {
                s.sendMessage(ChatColor.RED + "That is not an online player!");
            }
        }
        return true;
    }

    private void warnPlayer(Player p, String message) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + p.getName() + " title " + "{text:\"Warning!\",color:red,bold:true}");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + p.getName() + " subtitle " + "{text:\"" + message + "\",color:gray,italic:true}");
    }
}
