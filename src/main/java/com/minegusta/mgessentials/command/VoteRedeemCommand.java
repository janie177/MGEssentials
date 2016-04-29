package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.votepoints.VotePointsDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VoteRedeemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vote")) {
            if (s instanceof ConsoleCommandSender) return false;

            Player p = (Player) s;

            if (!(VotePointsDataManager.getPlayerVotes(p.getUniqueId()) > 0)) {
                p.sendMessage(ChatColor.DARK_RED + "You have no redeems left!" + ChatColor.YELLOW + " http://www.minegusta.net/vote.php");
                p.sendMessage(ChatColor.YELLOW + "You voted " + ChatColor.LIGHT_PURPLE + VotePointsDataManager.getTotalVotes(p.getUniqueId().toString()) + ChatColor.YELLOW + " times in total.");
                return true;
            } else {
                VotePointsDataManager.removeUnclaimedVote(p.getUniqueId());
                p.sendMessage(ChatColor.YELLOW + "You redeemed a vote and you have " + ChatColor.AQUA + VotePointsDataManager.getPlayerVotes(p.getUniqueId()) + ChatColor.YELLOW + " redeems left.");
                p.sendMessage(ChatColor.YELLOW + "You voted " + ChatColor.LIGHT_PURPLE + VotePointsDataManager.getTotalVotes(p.getUniqueId().toString()) + ChatColor.YELLOW + " times in total.");
                p.sendMessage(ChatColor.YELLOW + "Earn more redeems at" + ChatColor.BLUE + " http://www.minegusta.net/vote.php");
                p.getInventory().addItem(getBox());
                p.updateInventory();
            }
        }
        return true;
    }

    public ItemStack getBox() {
        ItemStack box = new ItemStack(Material.CHEST, 1);

        ItemMeta meta = box.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Rightclick the air to open!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "Mystery Box");
        box.setItemMeta(meta);
        return box;
    }
}
