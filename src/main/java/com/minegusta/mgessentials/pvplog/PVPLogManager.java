package com.minegusta.mgessentials.pvplog;

import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.TempData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PVPLogManager {
    private static int coolDownTime = 8; //Time in seconds it takes for the tag to wear off.

    /**
     * Is the player tagged as in-pvp on the moment of writing?
     *
     * @param p The player in the event.
     * @return Whether the player PVP logged.
     */
    public static boolean isTagged(Player p) {
        String name = p.getName();
        if (TempData.tagMap.containsKey(name)) {
            if (TimeUnit.NANOSECONDS.toSeconds(System.currentTimeMillis() - TempData.tagMap.remove(name)) < coolDownTime) {
                return true;
            }
        }
        return false;
    }

    /**
     * Spawn a new PVP bot.
     *
     * @param p The player that PVP logged.
     */
    public static void spawnBot(Player p) {
        PVPBot bot = new PVPBot(p, p.getInventory(), p.getLocation(), p.getWorld());
        bot.create();
    }

    /**
     * Set this player as in PVP
     *
     * @param p The player tagged.
     */
    public static void tag(Player p) {
        if (!TempData.tagMap.containsKey(p.getName())) {
            TempData.tagMap.put(p.getName(), System.currentTimeMillis());
            sendMessage(p, ChatColor.DARK_RED + "You are now tagged as in PVP!");
            unTag(p);
        }
    }


    /**
     * Send a simple message to a player.
     *
     * @param p The player that receives the message.
     * @param s The message.
     */
    private static void sendMessage(Player p, String s) {
        p.sendMessage(ChatColor.RED + "[" + ChatColor.DARK_RED + "TAG" + ChatColor.RED + "] " + s);
    }

    private static void unTag(final Player pl) {
        //Removes the tag after x seconds.
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                if (TempData.tagMap.containsKey(pl.getName())) {
                    TempData.tagMap.remove(pl.getName());
                    sendMessage(pl, ChatColor.GREEN + "You are no longer in pvp and can safely log out!");
                }
            }
        }, 20 * coolDownTime);
    }
}
