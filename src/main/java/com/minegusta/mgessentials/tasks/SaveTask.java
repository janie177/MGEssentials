package com.minegusta.mgessentials.tasks;

import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.command.CraftCommand;
import com.minegusta.mgessentials.joinsound.JoinSoundManager;
import com.minegusta.mgessentials.votepoints.VotePointsDataManager;
import org.bukkit.Bukkit;

public class SaveTask {
    public static int startTask() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.PLUGIN, new Runnable() {
            @Override
            public void run() {
                VotePointsDataManager.save();
                JoinSoundManager.save();
                CraftCommand.craftingInstances.keySet().stream().forEach(inv ->
                {
                    if (inv.getViewers().size() == 0) {
                        CraftCommand.craftingInstances.remove(inv);
                    }
                });
            }
        }, 0, 20 * 180);
    }
}
