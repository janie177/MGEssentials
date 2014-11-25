package com.minegusta.mgessentials.tasks;


import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.data.TempData;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ParticleTask
{
    public static int start()
    {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run()
            {
                for (String p : TempData.effectMap.keySet())
                {
                    Effect theEffect = TempData.effectMap.get(p);
                    if (theEffect != null && Bukkit.getPlayer(UUID.fromString(p)) != null) {
                        Player pl = Bukkit.getPlayer(UUID.fromString(p));
                        if (!pl.isOnline()) {
                            TempData.effectMap.remove(p);
                            return;
                        }
                        pl.getWorld().spigot().playEffect(pl.getLocation(), theEffect, 0, 0, 1F, 0.1F, 1F, 0.5F, 9, 30);
                    }
                    else TempData.effectMap.remove(p);
                }
            }
        }, 0, 10);
    }
}
