package com.minegusta.mgessentials.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RandomSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (!(s instanceof Player)) return false;

        Player p = (Player) s;

        if (!p.hasPermission("minegusta.spawnme")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this!");
            return true;
        }

        if (p.getLocation().getBlock().getBiome() == Biome.SKY || p.getLocation().getBlock().getBiome() == Biome.HELL) {
            p.sendMessage(ChatColor.RED + "You can only use this command in the overworld.");
            return true;
        }

        s.sendMessage(ChatColor.YELLOW + "You have been teleported to a random location!");

        int x = getRandomCoordinate();
        int z = getRandomCoordinate();
        int y = p.getWorld().getHighestBlockYAt(x, z) + 4;

        int glassCheck = glassCheck(p.getWorld(), x, y, z);

        if (glassCheck != 0) {
            y = glassCheck + 4;
        }

        Location l = new Location(p.getWorld(), x, y, z);
        if (!l.getChunk().isLoaded()) l.getChunk().load();

        p.teleport(l);

        return true;
    }

    private int glassCheck(World w, int x, int y, int z) {
        int yblock = 0;
        for (int i = y; i < 256 - y; i++) {
            if (w.getBlockAt(x, i, z).getType() != Material.AIR) {
                yblock = i;
            }
        }
        return yblock;
    }

    private static Random rand = new Random();

    private int getRandomCoordinate() {
        return rand.nextInt(3000) - 1500;
    }
}
