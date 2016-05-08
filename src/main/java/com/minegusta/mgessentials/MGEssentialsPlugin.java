package com.minegusta.mgessentials;

import com.minegusta.mgessentials.pvplog.PvpLogListener;
import org.bukkit.entity.Player;

public class MGEssentialsPlugin {

	public static boolean inCombat(Player p) {
		return PvpLogListener.isInCombat(p);
	}
}
