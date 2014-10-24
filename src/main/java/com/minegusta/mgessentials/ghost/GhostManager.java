package com.minegusta.mgessentials.ghost;

import com.minegusta.mgessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class GhostManager {
    private static final ScoreboardManager sm = Bukkit.getScoreboardManager();
    private static final Scoreboard sb = sm.getMainScoreboard();
    private static Team team;
    private static int SPOOKTASK;


    /**
     * Adds a player to the spookboard. Make sure to check for this on login.
     *
     * @param p The player in the event.
     */
    public static void setSpook(Player p) {
        p.setScoreboard(sb);
        for (PotionEffect ef : p.getActivePotionEffects()) {
            if (ef.getType().equals(PotionEffectType.INVISIBILITY))
                p.getPlayer().removePotionEffect(ef.getType());
        }
        p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 20, 0, false));
        team.addPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
    }

    /**
     * Start the spook! Run this when enabling spook.
     */
    public static void ghostify() {
        if (sb.getTeam("ghost") == null) {
            sb.registerNewTeam("ghost");
        }
        team = sb.getTeam("ghost");

        team.setCanSeeFriendlyInvisibles(true);

        for (Player p : Bukkit.getOnlinePlayers()) {
            setSpook(p);
        }

        SPOOKTASK = spookTask;
    }

    /**
     * No more spook? isk.
     */
    public static void unGhostify() {
        Bukkit.getScheduler().cancelTask(SPOOKTASK);

        for (OfflinePlayer p : team.getPlayers()) {
            team.removePlayer(p);
            if (p.getPlayer().isOnline()) {
                for (PotionEffect ef : p.getPlayer().getActivePotionEffects()) {
                    if (ef.getType().equals(PotionEffectType.INVISIBILITY))
                        p.getPlayer().removePotionEffect(ef.getType());
                }
            }
        }
    }


    /**
     * This is the task for the spooking effect. Wooosh!
     */
    private static int spookTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
        @Override
        public void run() {
            for (OfflinePlayer p : team.getPlayers()) {
                if (p.getPlayer() == null || !p.getPlayer().isOnline()) {
                    team.removePlayer(p);
                } else {
                    for (PotionEffect ef : p.getPlayer().getActivePotionEffects()) {
                        if (ef.getType().equals(PotionEffectType.INVISIBILITY))
                            p.getPlayer().removePotionEffect(ef.getType());
                    }
                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 20, 0, false));
                }
            }
        }
    }, 5 * 20, 20 * 15);
}
