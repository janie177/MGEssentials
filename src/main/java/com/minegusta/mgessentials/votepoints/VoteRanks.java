package com.minegusta.mgessentials.votepoints;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class VoteRanks {

	private static ConcurrentMap<String, Integer> ranks = Maps.newConcurrentMap();

	public static void init() {
		ranks = MainConfig.getVoteRanks();
	}

	public static void checkRankUp(Player p, int oldVotePoints, int newVotePoints, boolean silent) {
		for (String s : ranks.keySet()) {
			int value = ranks.get(s);
			if (oldVotePoints < value && newVotePoints >= value) {
				//Derank all other ranks
				for (String s2 : ranks.keySet()) {
					Main.PLUGIN.getServer().dispatchCommand(Main.PLUGIN.getServer().getConsoleSender(), "pex user " + p.getName() + " group remove " + s2);
				}
				//Add the new rank
				Main.PLUGIN.getServer().dispatchCommand(Main.PLUGIN.getServer().getConsoleSender(), "pex user " + p.getName() + " group add " + s);
				if (!silent) {
					p.sendMessage(ChatColor.YELLOW + "Congratulations! You earned the rank " + s.toUpperCase());
					Bukkit.broadcastMessage(ChatColor.YELLOW + p.getDisplayName() + ChatColor.DARK_PURPLE + " just earned the rank " + ChatColor.GOLD + s.toUpperCase() + ChatColor.DARK_PURPLE + " by voting " + ChatColor.YELLOW + value + ChatColor.DARK_PURPLE + " times!");
				}
				break;
			}
		}
	}


	public static void applyRank(Player p, int votePoints) {
		List<Integer> added = Lists.newArrayList();

		for (String s : ranks.keySet()) {
			int value = ranks.get(s);
			if (votePoints >= value) {

				boolean apply = true;
				for (int i : added) {
					if (i > value) {
						apply = false;
					}
				}

				if (apply) {
					//Derank all other ranks
					for (String s2 : ranks.keySet()) {
						Main.PLUGIN.getServer().dispatchCommand(Main.PLUGIN.getServer().getConsoleSender(), "pex user " + p.getName() + " group remove " + s2);
					}
					//Add the new rank
					Main.PLUGIN.getServer().dispatchCommand(Main.PLUGIN.getServer().getConsoleSender(), "pex user " + p.getName() + " group add " + s);
				}


			}
			added.add(value);
		}
	}

}
