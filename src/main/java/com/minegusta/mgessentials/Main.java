package com.minegusta.mgessentials;

import com.minegusta.mgessentials.joinsound.JoinSoundManager;
import com.minegusta.mgessentials.pvplog.LogoutManager;
import com.minegusta.mgessentials.tasks.CombatTask;
import com.minegusta.mgessentials.tasks.ParticleTask;
import com.minegusta.mgessentials.tasks.SaveTask;
import com.minegusta.mgessentials.votepoints.VotePointsDataManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin PLUGIN;
    private static int SAVETASK, PARTICLETASK, COMBATTASK;

    public void onEnable() {
        //Setting the Plugin
        PLUGIN = this;

        //Register all the commands.
        for (Commands command : Commands.values()) {
            getCommand(command.getCommandName().toLowerCase()).setExecutor(command.getExecutor());
        }

        //Register all the listeners
        for (Listeners listener : Listeners.values()) {
            Bukkit.getPluginManager().registerEvents(listener.getListener(), this);
        }

        //Load the vote points file.
        VotePointsDataManager.createOrLoadPointsFile(PLUGIN);

        //Load the pvp log file
        LogoutManager.createFile(PLUGIN);

        //Load the joinsound file
        JoinSoundManager.createOrLoadPointsFile(PLUGIN);

        //Start the save task
        SAVETASK = SaveTask.startTask();

        //Start the combat task
        COMBATTASK = CombatTask.start();

        //Start the particle task
        PARTICLETASK = ParticleTask.start();
    }

    public void onDisable() {
        //Save the votepoints file
        VotePointsDataManager.save();

        //Save the joinsound file
        JoinSoundManager.save();

        //Stop the save task
        Bukkit.getScheduler().cancelTask(SAVETASK);

        //Stop the particle task
        Bukkit.getScheduler().cancelTask(PARTICLETASK);

        //Stop the combat task
        Bukkit.getScheduler().cancelTask(COMBATTASK);

    }
}
