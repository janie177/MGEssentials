package com.minegusta.mgessentials.pvplog;

import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.TempData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PVPBot {
    private int botLiveTime = 10; // Time in seconds the bot stays alive.
    private Player p;
    private World w;
    private Location l;
    private Inventory i;

    public PVPBot(Player p, Inventory i, Location l, World w) {
        this.i = i;
        this.p = p;
        this.w = w;
        this.l = l;
    }


    public void create() {
        //Create the skeleton
        final Skeleton skeleton = (Skeleton) w.spawnEntity(l, EntityType.SKELETON);

        //Set the map
        TempData.pvpLogMap.put(p.getUniqueId().toString(), i);

        //Define the skeleton
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName(p.getDisplayName());
        skeleton.getEquipment().setArmorContents(i.getContents());
        skeleton.getEquipment().setHelmet(new ItemStack(Material.SKULL, 1) {
            {
                SkullMeta meta = (SkullMeta) getItemMeta();
                meta.setOwner(p.getName());

                setItemMeta(meta);
            }
        });

        //Add the skeleton to a bot map for checking if it's killed.
        TempData.botMap.put(skeleton.getUniqueId().toString(), true);

        //Create a task that removes the entity after x seconds.
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {

                if (!skeleton.isDead()) {
                    if (TempData.botMap.containsKey(skeleton.getUniqueId().toString()))
                        TempData.botMap.remove(skeleton.getUniqueId().toString());
                    skeleton.remove();

                }

            }
        }, botLiveTime);

    }


}
