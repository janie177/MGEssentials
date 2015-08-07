package com.minegusta.mgessentials.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (s instanceof Player && s.hasPermission("minegusta.time")) {

            Player p = (Player) s;

            if (args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "-| Usage: |-");
                p.sendMessage(ChatColor.GREEN + "/MyTime Reset");
                p.sendMessage(ChatColor.GREEN + "/MyTime <0-23000>");
                return true;
            }


            if (args[0].equalsIgnoreCase("Reset")) {
                p.resetPlayerTime();
                p.sendMessage(ChatColor.YELLOW + "Your time has been reset.");
                return true;
            }

            int time = 6000;
            try {
                int i = Integer.parseInt(args[0]);
                time = i;
            } catch (Exception ignored) {
            }

            if (time > 23000) {
                time = time % 23000;
            }

            p.sendMessage(ChatColor.YELLOW + "Your time has been set to " + time + ".");
            p.setPlayerTime(time, false);
        }
        return true;
    }
}
