package com.minegusta.mgessentials.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.TempData;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class ProjectileListener implements Listener {
    public static ConcurrentMap<String, Boolean> toggleNuke = TempData.nukeMap;
    public static Set<Arrow> arrows = Sets.newHashSet();

    @EventHandler
    public void onArrowLaunchEvent(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        if (event.getEntityType().equals(EntityType.ARROW) && toggleNuke.containsKey(player.getName()))
            if (checkAndRemoveItemCount(player.getInventory(), Material.TNT, 30)) arrows.add((Arrow) event.getEntity());
            else {
                if (toggleNuke.containsKey(player.getName())) toggleNuke.remove(player.getName());
                player.sendMessage(ChatColor.RED + "You don't have enough TNT");
                player.sendMessage(ChatColor.YELLOW + "Nuke arrows have been disabled.");
            }
    }

    private static final List<Material> blackBlockList = Lists.newArrayList(Material.IRON_DOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.WOODEN_DOOR, Material.WOOD_DOOR, Material.TRAP_DOOR, Material.IRON_FENCE, Material.FENCE, Material.STAINED_GLASS_PANE, Material.THIN_GLASS);

    @EventHandler
    public void onArrowImpactEvent(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();

        if (event.getEntity() instanceof EnderPearl) {
            Material b1 = event.getEntity().getLocation().getBlock().getType();
            Material b2 = event.getEntity().getLocation().getBlock().getRelative(BlockFace.UP).getType();

            if (blackBlockList.contains(b1) || blackBlockList.contains(b2)) {
                player.sendMessage(ChatColor.RED + "You cannot throw a pearl there because of glitching.");
                player.teleport(player.getLocation());
            }
        }

        if (event.getEntity() instanceof Arrow && arrows.contains(event.getEntity())) {
            arrows.remove(event.getEntity());
            player.sendMessage(ChatColor.YELLOW + "Boom!");
            final Location target = event.getEntity().getLocation();
            for (int i = 0; i < 60; i++) {
                final int k = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
                    @Override
                    public void run() {
                        nukeEffect(target, 110 + k, 30 * k, k / 4);
                    }
                }, i);
            }
        }
    }

    private static void nukeEffect(Location target, int range, int particles, int offSetY) {
        target.getWorld().createExplosion(target.getX(), target.getY() + 3 + offSetY, target.getZ(), 6F, false, false);
        target.getWorld().playSound(target, Sound.AMBIENCE_CAVE, 1F, 1F);
        target.getWorld().spigot().playEffect(target, Effect.CLOUD, 1, 1, 0F, 3F + offSetY, 3F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.LAVA_POP, 1, 1, 0F, 3F, 0F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.SMOKE, 1, 1, 0F, 3F + offSetY, 0F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.FLAME, 1, 1, 0F, 3F + offSetY, 0F + offSetY, 1F, particles, range);
    }

    private static boolean checkAndRemoveItemCount(Inventory inventory, Material material, int need) {
        int count = 0;
        Set<Integer> counted = Sets.newHashSet();
        for (Integer slot : inventory.all(material).keySet()) {
            ItemStack item = inventory.getItem(slot);
            count = item.getAmount() + count;
            counted.add(slot);

            if (count >= need) {
                for (Integer remove : counted)
                    inventory.clear(remove);
                if (count - need != 0) inventory.addItem(new ItemStack(material, count - need));
                return true;
            }
        }
        return false;
    }
}

