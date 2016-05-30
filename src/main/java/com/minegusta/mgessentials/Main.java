package com.minegusta.mgessentials;

import com.minegusta.mgessentials.command.WorldCommand;
import com.minegusta.mgessentials.data.MainConfig;
import com.minegusta.mgessentials.joinsound.JoinSoundManager;
import com.minegusta.mgessentials.pvplog.LogData;
import com.minegusta.mgessentials.pvplog.LogoutManager;
import com.minegusta.mgessentials.pvplog.PVPBot;
import com.minegusta.mgessentials.pvplog.PvpLogListener;
import com.minegusta.mgessentials.tasks.CombatTask;
import com.minegusta.mgessentials.tasks.ParticleTask;
import com.minegusta.mgessentials.tasks.SaveTask;
import com.minegusta.mgessentials.votepoints.VotePointsDataManager;
import com.minegusta.mgessentials.votepoints.VoteRanks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static boolean BLOCK_CHORUS_FRUIT = false;
    public static boolean BLOCK_ALL_EXPLOSION_TERRAIN_DAMAGE = false;
    public static boolean USESQL = false;
    public static Plugin PLUGIN;
    private static int SAVETASK, PARTICLETASK, COMBATTASK;
    private static boolean WG_ENABLED = false;
    private static boolean BOSSBAR_ENABLED = false;

    public void onEnable() {
        //Setting the Plugin
        PLUGIN = this;

        //Load the vote points file.
        VotePointsDataManager.createOrLoadPointsFile(PLUGIN);

        //Load the config file.
        MainConfig.loadConfig();

        //Load the pvp log file
        LogoutManager.createFile(PLUGIN);

        //Load the joinsound file
        JoinSoundManager.createOrLoadPointsFile(PLUGIN);

        //set chorus fruit
        BLOCK_CHORUS_FRUIT = getConfig().getBoolean("block-chorus-fruit", true);

        USESQL = getConfig().getBoolean("use-sql", false);

        if (USESQL) {
            USESQL = VotePointsDataManager.initSQL();
        }

        if (!USESQL) {
            getLogger().info(ChatColor.RED + "- - - - - - - - - - - - - - - - - - - - - - - -");
            getLogger().info(ChatColor.RED + "No MYSQL database connection could be made for MGEssentials.");
            getLogger().info(ChatColor.RED + "Defaulting to flat file. BEWARE! INCONSISTENT DATA CROSS SERVER.");
            getLogger().info(ChatColor.RED + "- - - - - - - - - - - - - - - - - - - - - - - -");
        }

        BLOCK_ALL_EXPLOSION_TERRAIN_DAMAGE = getConfig().getBoolean("block-explosion-block-damage", true);

        //Start the save task
        SAVETASK = SaveTask.startTask();

        //Start the particle task
        PARTICLETASK = ParticleTask.start();

        //Register all the commands.
        for (Commands command : Commands.values()) {
            getCommand(command.getCommandName().toLowerCase()).setExecutor(command.getExecutor());
        }

        //Register all the listeners
        for (Listeners listener : Listeners.values()) {
            Bukkit.getPluginManager().registerEvents(listener.getListener(), this);
        }

        //Register the PVPlog listener by hand IF enabled.
        if (MainConfig.getPvpBotEnabled()) {
            Bukkit.getPluginManager().registerEvents(new PvpLogListener(), this);

            //Start the combat task
            COMBATTASK = CombatTask.start();
        }

        //Init the worlds from the config
        WorldCommand.init();

        WG_ENABLED = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");

        BOSSBAR_ENABLED = Bukkit.getPluginManager().isPluginEnabled("BossBarAPI");

        if (MainConfig.voteRanksEnabled()) VoteRanks.init();
    }

    public static boolean isWGEnabled() {
        return WG_ENABLED;
    }

    public void onDisable() {

        //Remove all bots just in case
        if (MainConfig.getPvpBotEnabled()) {
            for (PVPBot bot : LogData.logMap.values()) {
                bot.removeBot();
                bot.stop();
            }
        }

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

    public static boolean isBossbarEnabled() {
        return BOSSBAR_ENABLED;
    }
}
