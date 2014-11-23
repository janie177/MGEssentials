package com.minegusta.mgessentials.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StaffCommand implements CommandExecutor

{

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        List<String> staff = Lists.newArrayList();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("minegusta.staff")) {
                staff.add(p.getDisplayName());
            }
        }
        if (staff.isEmpty())
            s.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Staff online: " + ChatColor.RESET + " None.");
        else {
            s.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Staff online:");
            for (String str : staff) {
                s.sendMessage(str);
            }
        }
        return true;
    }
}
