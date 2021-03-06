package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.data.MainConfig;
import com.minegusta.mgessentials.votepoints.VotePointsDataManager;
import com.minegusta.mgessentials.votepoints.VoteRanks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VotePointsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.isOp()) return true;

        if (cmd.getName().equalsIgnoreCase("votepoints")) {
            if (args.length < 2) {
                if (args.length == 0) {
                    sendInfo(s);
                    return true;
                } else if (args[0].equalsIgnoreCase("top")) {
                    s.sendMessage(ChatColor.GOLD + " --- Top Voters ---");
                    VotePointsDataManager.getMostVotes().stream().forEach(string -> s.sendMessage(ChatColor.YELLOW + " - " + Bukkit.getOfflinePlayer(UUID.fromString(string)).getName()));
                    return true;
                }
                sendInfo(s);
                return true;
            }
            try {
                if (args[0].equalsIgnoreCase("add")) {
                    Player p = Bukkit.getPlayer(args[1]);
                    UUID uuid = p.getUniqueId();

                    //Check for rank up
                    if (MainConfig.voteRanksEnabled()) {
                        int votes = VotePointsDataManager.getTotalVotes(uuid.toString());
                        VoteRanks.checkRankUp(p, votes, votes + 1, false);
                    }

                    VotePointsDataManager.addVote(uuid);

                    p.sendMessage(ChatColor.YELLOW + "You voted and earned a vote point!");

                    return true;
                } else if (args[0].equalsIgnoreCase("get")) {
                    int amount = Integer.parseInt(args[1]);
                    s.sendMessage(ChatColor.GOLD + "--Players with more than " + amount + " votes--");
                    VotePointsDataManager.getMoreThan(amount).stream().forEach(uuid -> s.sendMessage(ChatColor.YELLOW + " - " + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()));
                    return true;
                } else if (args[0].equalsIgnoreCase("reset")) {
                    UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();
                    VotePointsDataManager.resetvotes(uuid);
                    return true;
                }
            } catch (Exception e) {

            }
        }
        sendInfo(s);
        return true;
    }

    private void sendInfo(CommandSender s) {
        s.sendMessage(ChatColor.YELLOW + " - - - - -");
        s.sendMessage(ChatColor.GRAY + " /Votepoints top");
        s.sendMessage(ChatColor.GRAY + " /Votepoints add [name]");
        s.sendMessage(ChatColor.GRAY + " /Votepoints get [amount]");
        s.sendMessage(ChatColor.GRAY + " /Votepoints reset [name]");
        s.sendMessage(ChatColor.YELLOW + " - - - - -");
    }
}