package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fart") && sender.hasPermission("minegusta.fart")) {
            if (sender instanceof ConsoleCommandSender) return false;
            for (int i = 0; i < 201; i++) {
                final Player player = (Player) sender;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, new Runnable() {
                    @Override
                    public void run() {
                        fartCloud(player.getLocation());
                    }
                }, i);
            }
            return true;
        } else {
            sender.sendMessage("Something went wrong.");
        }
        return true;
    }

    public static void fartCloud(Location location) {
        location.getWorld().playSound(location, Sound.CHICKEN_EGG_POP, 0.5F, 5F);
        location.getWorld().spigot().playEffect(location, Effect.SMOKE, 1, 1, 3F, 1F, 3F, 1F, 500, 20);
    }
}
