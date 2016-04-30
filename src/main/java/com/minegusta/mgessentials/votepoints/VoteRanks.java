package com.minegusta.mgessentials.votepoints;

import com.google.common.collect.Maps;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class VoteRanks {

	private static ConcurrentMap<String, Integer> ranks = Maps.newConcurrentMap();

	public static void init() {
		ranks = MainConfig.getVoteRanks();
	}

	public static void checkRankUp(Player p, int oldVotePoints, int newVotePoints) {
		for (String s : ranks.keySet()) {
			int value = ranks.get(s);
			if (oldVotePoints < value && newVotePoints >= value) {
				//Derank all other ranks
				for (String s2 : ranks.keySet()) {
					Main.PLUGIN.getServer().dispatchCommand(Main.PLUGIN.getServer().getConsoleSender(), "/pex user " + p.getName() + " group remove " + s2);
				}
				//Add the new rank
				Main.PLUGIN.getServer().dispatchCommand(Main.PLUGIN.getServer().getConsoleSender(), "/pex user " + p.getName() + " group add " + s);
				p.sendMessage(ChatColor.YELLOW + "Congratulations! You earned the rank " + s.toUpperCase());
				Bukkit.broadcastMessage(ChatColor.YELLOW + p.getDisplayName() + ChatColor.DARK_PURPLE + " just earned the rank " + ChatColor.GOLD + s.toUpperCase());
				break;
			}
		}
	}

}
