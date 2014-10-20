package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.votepoints.VotePointsDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class VotePointsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("votepoints")) {
            if (args.length < 2) ;
            try {

                UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();

                if (args[0].equalsIgnoreCase("add")) {
                    VotePointsDataManager.addPoint(uuid);

                } else if (args[0].equalsIgnoreCase("reset")) {
                    VotePointsDataManager.resetPoint(uuid);
                }


            } catch (Exception e) {

            }
        }
        return true;
    }
}