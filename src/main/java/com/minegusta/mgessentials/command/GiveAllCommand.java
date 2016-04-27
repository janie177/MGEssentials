package com.minegusta.mgessentials.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || !sender.hasPermission("minegusta.giveall")) return true;

        Player p = (Player) sender;

        //In case of air
        if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "That is not an item you silly goat!");
            return true;
        }
        ItemStack is = p.getInventory().getItemInMainHand();

        //The loop
        for (Player victim : Bukkit.getOnlinePlayers()) {
            victim.getInventory().addItem(is);
        }

        p.sendMessage(ChatColor.GREEN + "You gave everyone the itemstack in your hand!");
        return true;
    }
}
