package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.data.TempData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.hasPermission("minegusta.pop") || s instanceof ConsoleCommandSender) {
            if (s instanceof Player && TempData.popMap.containsKey(s.getName())) TempData.popMap.remove(s.getName());
            s.sendMessage("Nope, No pop for you.");
        } else {
            if (TempData.popMap.containsKey(s.getName().toLowerCase()) && TempData.popMap.get(s.getName().toLowerCase())) {
                TempData.popMap.put(s.getName().toLowerCase(), false);
                s.sendMessage(ChatColor.GOLD + "Pop disabled. Please come again.");
            } else {
                TempData.popMap.put(s.getName().toLowerCase(), true);
                s.sendMessage(ChatColor.GOLD + "Pop enabled. Let the games begin!");
            }
            return true;
        }
        return true;
    }
}
