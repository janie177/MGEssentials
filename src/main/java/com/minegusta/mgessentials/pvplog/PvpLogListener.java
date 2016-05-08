package com.minegusta.mgessentials.pvplog;

import com.google.common.collect.Maps;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.util.WGUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class PvpLogListener implements Listener {
    public static ConcurrentMap<String, Integer> inCombat = Maps.newConcurrentMap();
    public static int coolDownSeconds = 10;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTag(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && !e.isCancelled() && e.getDamage() != 0) {
            if (!inCombat.containsKey(e.getDamager().getUniqueId().toString())) {
                ((Player) e.getDamager()).sendMessage(ChatColor.RED + "You are now in combat! Do not log out!");
            }
            if (!inCombat.containsKey(e.getEntity().getUniqueId().toString())) {
                ((Player) e.getEntity()).sendMessage(ChatColor.RED + "You are now in combat ! Do not log out!");
            }
            inCombat.put(e.getEntity().getUniqueId().toString(), coolDownSeconds);
            inCombat.put(e.getDamager().getUniqueId().toString(), coolDownSeconds);
            setBar((Player) e.getEntity());
            setBar((Player) e.getDamager());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie && LogData.bots.containsKey(e.getEntity().getUniqueId().toString())) {
            Zombie z = (Zombie) e.getEntity();
            z.getEquipment().setArmorContents(new ItemStack[]{null, null, null, null});
            e.getDrops().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerQuitEvent e) {
        if (inCombat.containsKey(e.getPlayer().getUniqueId().toString())) {
            if (WGUtil.canPVP(e.getPlayer())) {
                LogData.add(e.getPlayer(), new PVPBot(e.getPlayer()));
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerLoginEvent e) {
        if (LogData.contains(e.getPlayer())) {
            int remaining = LogData.remainingTime(e.getPlayer());
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You combat logged! Please wait " + LogData.remainingTime(e.getPlayer()) + " seconds.");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerJoinEvent e) {
        if (!LogData.contains(e.getPlayer()) && LogoutManager.getIfDead(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage(ChatColor.RED + "You died after you PVP logged.");

            e.getPlayer().getInventory().clear();
            e.getPlayer().getEquipment().clear();
            e.getPlayer().getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
            e.getPlayer().setHealth(0);
            LogoutManager.reset(e.getPlayer().getUniqueId());
        }
    }

    //Check for unloading chunks;
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        for (List<Chunk> chunks : LogData.chunkMap.values()) {
            if (chunks.contains(e.getChunk())) e.setCancelled(true);
        }
    }

    private void setBar(Player p) {
        if (Main.isBossbarEnabled()) {
            BossBarAPI.removeAllBars(p);
            BossBar bar = BossBarAPI.addBar(p, new TextComponent(ChatColor.RED + "You are in combat!"), BossBarAPI.Color.RED, BossBarAPI.Style.NOTCHED_10, 1, coolDownSeconds, 20);
        }
    }

    public static boolean isInCombat(Player p) {
        return inCombat.containsKey(p.getUniqueId().toString());
    }
}
