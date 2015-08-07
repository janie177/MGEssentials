package com.minegusta.mgessentials.tasks;

import com.minegusta.mgessentials.Main;
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
            }
        }, 0, 20 * 180);
    }
}
