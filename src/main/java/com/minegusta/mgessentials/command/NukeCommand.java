package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NukeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("explode.nuke")) {
            sender.sendMessage("Nice try " + sender + ", no permissions for you >:3");
        } else if (command.getName().equalsIgnoreCase("nuke") && sender.hasPermission("minegusta.nuke")) {
            try {
                final Location target = Bukkit.getServer().getPlayer(args[0]).getLocation();
                for (int i = 0; i < 60; i++) {
                    final int k = i;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {

                        @Override
                        public void run() {
                            nukeEffect(target, 110 + k, 30 * k, k / 4);

                        }

                    }, i);
                }
                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + sender.getName() + " nuked " + Bukkit.getServer().getPlayer(args[0]).getName() + "! Don't mess with North-Korea.");
            } catch (Exception e) {
                sender.sendMessage("Player is not real or not online. Try again.");
            }
        }
        return true;
    }

    public static void nukeEffect(Location target, int range, int particles, int offSetY) {
        target.getWorld().playSound(target, Sound.AMBIENT_CAVE, 1F, 1F);
        target.getWorld().createExplosion(target.getX(), target.getY() + 3.9 + offSetY, target.getZ(), 6F, false, false);
        target.getWorld().spigot().playEffect(target, Effect.CLOUD, 1, 1, 3F, 0F, 3F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.LAVA_POP, 1, 1, 0.4F, 10F, 0.4F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.SMOKE, 1, 1, 0.4F, 10F, 0.4F, 1F, particles, range);
        target.getWorld().spigot().playEffect(target, Effect.FLAME, 1, 1, 0.4F, 10F, 0.4F, 1F, particles, range);
    }
}
