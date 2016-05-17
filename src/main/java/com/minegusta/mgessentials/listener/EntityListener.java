package com.minegusta.mgessentials.listener;

import com.minegusta.mgessentials.data.TempData;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPortalEvent;

import java.util.concurrent.ConcurrentMap;

public class EntityListener implements Listener {

    public static ConcurrentMap<String, Boolean> toggleData;

    static {
        toggleData = TempData.popMap;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        final Entity entity = event.getEntity();
        if (damager instanceof Player && toggleData.containsKey(((Player) damager).getName().toLowerCase()) && toggleData.get(((Player) damager).getName().toLowerCase())) {
            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5F, 1F);
            entity.playEffect(EntityEffect.WOLF_SMOKE);
            Location location = entity.getLocation();
            entity.getWorld().spigot().playEffect(location, Effect.HEART, 1, 1, 0.35F, 1.5F, 0.35F, 0.5F, 15, 5);
            entity.getWorld().spigot().playEffect(location, Effect.FLAME, 1, 1, 0.5F, 2F, 0.5F, 0.5F, 15, 5);
            entity.getWorld().spigot().playEffect(location, Effect.CLOUD, 1, 1, 1F, 2F, 1F, 0.5F, 30, 5);
            entity.getWorld().spigot().playEffect(location, Effect.MAGIC_CRIT, 1, 1, 0.5F, 1F, 0.5F, 0.5F, 20, 5);
            entity.getWorld().spigot().playEffect(location, Effect.PARTICLE_SMOKE, 1, 1, 0.5F, 1F, 0.5F, 0.5F, 40, 5);
            event.setCancelled(true);
        }
    }


    //Nerf spawners so they cannot crash the server
    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if ((e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) && e.getEntity().getWorld().getLivingEntities().stream().filter(ent -> ent.getLocation().distance(e.getEntity().getLocation()) < 30).count() > 30) {
            e.setCancelled(true);
        }
    }


    //Stop portal travel.
    @EventHandler
    public void onPortalTravel(EntityPortalEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            e.setCancelled(true);
        }
    }
}