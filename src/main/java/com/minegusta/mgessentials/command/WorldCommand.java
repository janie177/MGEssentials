package com.minegusta.mgessentials.command;

import com.google.common.collect.Maps;
import com.minegusta.mgessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class WorldCommand implements CommandExecutor {

	private static ConcurrentMap<String, String> worlds = Maps.newConcurrentMap();

	public static void init() {
		FileConfiguration conf = Main.PLUGIN.getConfig();
		if (!conf.isSet("worlds")) return;
		for (String s : conf.getConfigurationSection("worlds").getKeys(false)) {
			if (Bukkit.getWorld(s) != null) {
				worlds.put(conf.getString("worlds." + s, "null").toLowerCase(), s);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length > 0 && !args[0].equalsIgnoreCase("list")) {
				String world = args[0].toLowerCase();
				{
					try {
						World w = Bukkit.getWorld(worlds.get(world));
						if (p.hasPermission("minegusta.world." + args[0].toLowerCase())) {
							p.teleport(w.getSpawnLocation());
							return true;
						} else {
							p.sendMessage(ChatColor.RED + "You have no permission to teleport to that world.");
							return true;
						}
					} catch (Exception ignored) {
						p.sendMessage(ChatColor.RED + "That world could not be found.");
						return true;
					}
				}
			}

			//send help
			p.sendMessage(ChatColor.YELLOW + "[MG] " + ChatColor.GREEN + "Use /World <name> to teleport to that world!");
			p.sendMessage(ChatColor.YELLOW + "[MG] " + ChatColor.GREEN + "Here is a list of worlds you can teleport to:");
			for (String s : worlds.keySet()) {
				if (p.hasPermission("minegusta.world." + s)) {
					p.sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + s);
				}
			}

		}
		return true;
	}
}
