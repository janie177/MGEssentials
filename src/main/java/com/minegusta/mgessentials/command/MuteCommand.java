package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mausmute")) {
            try {
                Bukkit.dispatchCommand(s, "ccmute " + args[0]);
                return true;
            } catch (Exception ignored) {
            }
        } else if (cmd.getName().equalsIgnoreCase("mausunmute")) {
            try {
                Bukkit.dispatchCommand(s, "ccunmute " + args[0]);
                return true;
            } catch (Exception ignored) {
            }
        }
        return true;
    }
}

