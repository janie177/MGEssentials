package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.data.TempData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NukeArrowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String playerName = sender.getName();
        if (sender.hasPermission("minegusta.nukearrow") && !TempData.nukeMap.containsKey(playerName)) {
            TempData.nukeMap.put(playerName, true);
            sender.sendMessage(ChatColor.YELLOW + "You have enabled arrow nukes. Firing an arrow will cost you 30 tnt.");
            return true;
        }
        if (TempData.nukeMap.containsKey(playerName)) TempData.nukeMap.remove(playerName);
        sender.sendMessage(ChatColor.YELLOW + "You have disabled arrow nukes.");
        return true;
    }
}
