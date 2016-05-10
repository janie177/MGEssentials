package com.minegusta.mgessentials.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (sender.hasPermission("minegusta.hat")) {
				ItemStack i = p.getInventory().getItemInMainHand() != null ? p.getInventory().getItemInMainHand().clone() : new ItemStack(Material.AIR);
				ItemStack head = p.getInventory().getHelmet() != null ? p.getInventory().getHelmet().clone() : new ItemStack(Material.AIR);

				p.getInventory().setItemInMainHand(head);
				p.getInventory().setHelmet(i);
				p.sendMessage(ChatColor.YELLOW + "[MG] " + ChatColor.LIGHT_PURPLE + "You equipped the item in your hand as a hat!");
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use /Hat!");
			}
		}
		return true;
	}
}
