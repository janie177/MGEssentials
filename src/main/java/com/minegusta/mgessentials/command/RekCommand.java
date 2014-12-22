package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RekCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            s.sendMessage(ChatColor.RED + "Usage: " + ChatColor.DARK_RED + "/Rek <Name>");
        } else {
            if (args[0].equals("*")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    rekPlayer(p, s.getName());
                }
                s.sendMessage(ChatColor.YELLOW + "You rekt everyone.");
                return true;
            }
            try {
                Player victim = Bukkit.getPlayer(args[0]);

                rekPlayer(victim, s.getName());

                s.sendMessage(ChatColor.YELLOW + "You rekt " + victim.getName() + ".");
            } catch (Exception ignored) {
                s.sendMessage(ChatColor.RED + "That is not an online player!");
            }
        }
        return true;
    }

    private void rekPlayer(Player p, String sender) {
        //Wait for 1.8 title support. I don't wanna add a lib.
    }
}
