package com.minegusta.mgessentials.tasks;

import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.pvplog.PvpLogListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.UUID;

public class CombatTask {
    public static int start() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                for (String s : PvpLogListener.inCombat.keySet()) {
                    if (PvpLogListener.inCombat.get(s) < 2) {
                        UUID uuid = UUID.fromString(s);
                        PvpLogListener.inCombat.remove(s);
                        if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                            Player p = Bukkit.getPlayer(uuid);
                            p.sendMessage(ChatColor.GREEN + "You are no longer in combat and can now safely logout!");
                            removeBar(p);
                        }
                    } else {
                        PvpLogListener.inCombat.put(s, PvpLogListener.inCombat.get(s) - 1);
                    }
                }
            }
        }, 20, 20);
    }

    private static void removeBar(Player p) {
        if (Main.isBossbarEnabled()) BossBarAPI.removeAllBars(p);
    }
}
