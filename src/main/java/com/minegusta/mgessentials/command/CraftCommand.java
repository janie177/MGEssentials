package com.minegusta.mgessentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (s instanceof Player && s.hasPermission("minegusta.craft")) {
            Player p = (Player) s;
            p.openWorkbench(null, true);
        }
        return true;
    }
}
