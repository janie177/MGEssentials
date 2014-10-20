package com.minegusta.mgessentials.votepoints;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class VotePointsDataManager {
    static File file;
    static FileConfiguration conf;

    public static void createOrLoadPointsFile(Plugin p) {
        try {

            file = new File(p.getDataFolder(), "votepoints.yml");

            if (!file.exists()) {
                p.saveResource("votepoints.yml", false);
                Bukkit.getLogger().info("Successfully created " + file.getName() + ".");
            }
            conf = YamlConfiguration.loadConfiguration(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            conf.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getPlayerVotes(UUID p) {
        if (!conf.isSet(p.toString())) return 0;
        else {
            return conf.getInt(p.toString());
        }
    }

    public static void addPoint(UUID p) {
        conf.set(p.toString(), getPlayerVotes(p) + 1);
    }

    public static void resetPoint(UUID p) {
        if (conf.isSet(p.toString())) {
            conf.set(p.toString(), 0);
        }
    }

    public static void removePoints(UUID p) {
        conf.set(p.toString(), getPlayerVotes(p) - 1);
    }
}
