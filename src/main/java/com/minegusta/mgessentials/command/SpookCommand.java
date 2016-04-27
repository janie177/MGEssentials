package com.minegusta.mgessentials.command;

import com.google.common.collect.Lists;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.TempData;
import com.minegusta.mgessentials.util.CoolDown;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SpookCommand implements CommandExecutor {

    private List<LivingEntity> bats = Lists.newArrayList();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (TempData.ghostMode && sender instanceof Player) {
            Player p = (Player) sender;
            if (CoolDown.cooledDown(p.getUniqueId(), TempData.spookMap, 60)) {
                Bat bat;
                for (int i = 0; i < 10; i++) {
                    bat = (Bat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
                    bats.add(bat);
                }

                createSkeletal(p);

                p.getWorld().spigot().playEffect(p.getLocation(), Effect.PORTAL, 0, 0, 1, 1, 1, 1, 25, 15);
                p.getWorld().spigot().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 0, 1, 1, 1, 1, 25, 15);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 1);

                //Create a new cooldown.
                CoolDown.newCooldown(p.getUniqueId(), TempData.spookMap);
                createTask();
            } else {
                p.sendMessage(ChatColor.RED + "You need to wait another " + CoolDown.getRemainingTime(p.getUniqueId(), TempData.spookMap, 60) + " seconds to spook.");
            }
        }
        return true;
    }


    private int createTask() {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                for (LivingEntity bat : bats) {
                    if (bat != null && !bat.isDead()) {
                        bat.getWorld().spigot().playEffect(bat.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 0, 1, 1, 1, 1, 12, 25);
                        bat.getWorld().playSound(bat.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 2);
                        bat.remove();
                    }
                }
            }
        }, 20 * 4);
    }

    private void createSkeletal(Player p) {
        Skeleton skeleton = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
        skeleton.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN, 1));
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName(ChatColor.WHITE + "Mr Skeletal");
        skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.WOOD_HOE, 1));

        bats.add(skeleton);
    }
}
