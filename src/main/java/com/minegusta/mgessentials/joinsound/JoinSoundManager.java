package com.minegusta.mgessentials.joinsound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class JoinSoundManager {
    static File file;
    static FileConfiguration conf;

    public static void createOrLoadPointsFile(Plugin p) {
        try {

            file = new File(p.getDataFolder(), "joinsounds.yml");

            if (!file.exists()) {
                p.saveResource("joinsounds.yml", false);
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

    public static boolean hasJoinSound(UUID p) {
        return conf.isSet(p.toString()) && conf.get(p.toString()) != null;
    }

    public static void setSound(UUID p, String message, Sound sound, float volume, float pitch) {
        conf.set(p.toString(), message + "%and%" + sound.toString() + "%and%" + Float.toString(volume) + "%and%" + Float.toString(pitch));
    }

    public static String getMessage(UUID p) {

        String[] parts = conf.getString(p.toString()).split("%and%");
        return ChatColor.translateAlternateColorCodes('&', parts[0]);

    }

    public static void playSound(UUID p) {
        String[] parts = conf.getString(p.toString()).split("%and%");
        if (Sound.valueOf(parts[1]) != null) {
            Sound sound = Sound.valueOf(parts[1]);
            float volume = Float.parseFloat(parts[2]);
            float pitch = Float.parseFloat(parts[3]);

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), sound, pitch, volume);
            }
        } else removeSound(p);
    }

    public static void removeSound(UUID p) {
        if (conf.isSet(p.toString())) {
            conf.set(p.toString(), null);
        }
    }
}
