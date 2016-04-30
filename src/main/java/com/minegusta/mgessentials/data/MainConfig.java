package com.minegusta.mgessentials.data;

import com.google.common.collect.Maps;
import com.minegusta.mgessentials.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ConcurrentMap;

public class MainConfig {
    public static void loadConfig() {
        Main.PLUGIN.saveDefaultConfig();

        pvpBotEnabled = getConfig().getBoolean("pvpbot-enabled", false);
        giveMysteryBoxOnVote = getConfig().getBoolean("give-mystery-box-on-vote", false);
        enableVoteRanks = getConfig().getBoolean("enable-vote-ranks", false);
    }

    public static FileConfiguration getConfig() {
        return Main.PLUGIN.getConfig();
    }

    //--// ALL THE CONFIG OPTIONS //--//

    private static boolean pvpBotEnabled;

    private static boolean giveMysteryBoxOnVote;

    private static boolean enableVoteRanks;

    public static boolean getPvpBotEnabled() {
        return pvpBotEnabled;
    }

    public static boolean getIfMysteryBoxEnabled() {
        return giveMysteryBoxOnVote;
    }

    public static boolean voteRanksEnabled() {
        return enableVoteRanks;
    }


    public static ConcurrentMap<String, Integer> getVoteRanks() {
        ConcurrentMap<String, Integer> ranks = Maps.newConcurrentMap();

        for (String s : getConfig().getConfigurationSection("voteranks").getKeys(false)) {
            int amount = getConfig().getInt("voteranks." + s, 10000);

            ranks.put(s, amount);
        }

        return ranks;
    }

}

