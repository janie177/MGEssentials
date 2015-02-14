package com.minegusta.mgessentials.data;

import com.minegusta.mgessentials.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfig {
    public static void loadConfig() {
        Main.PLUGIN.saveDefaultConfig();

        pvpBotEnabled = getConfig().getBoolean("pvpbot-enabled", false);
    }

    public static FileConfiguration getConfig() {
        return Main.PLUGIN.getConfig();
    }

    //--// ALL THE CONFIG OPTIONS //--//

    private static boolean pvpBotEnabled;

    public static boolean getPvpBotEnabled() {
        return pvpBotEnabled;
    }

}

