package com.minegusta.mgessentials.command;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class PotionCommands implements CommandExecutor {


    private int duration = 1200;
    private int strength = 1;
    private int coolDownTime = 180;

    private static Map<String, Long> potionCooldowns = new HashMap<String, Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("This command cannot be ran from the console, silly.");
            return false;
        }
        // define variables
        Player player = (Player) sender;
        PotionEffectType effect = null;

        // get effect
        if (cmd.getName().equalsIgnoreCase("speedy") && sender.hasPermission("potioncommand.speed"))
            effect = PotionEffectType.SPEED;
        else if (cmd.getName().equalsIgnoreCase("haste") && sender.hasPermission("potioncommand.haste"))
            effect = PotionEffectType.FAST_DIGGING;
        else if (cmd.getName().equalsIgnoreCase("strength") && sender.hasPermission("potioncommand.strength"))
            effect = PotionEffectType.INCREASE_DAMAGE;
        else if (cmd.getName().equalsIgnoreCase("fireresistance") && sender.hasPermission("potioncommand.fireresistance"))
            effect = PotionEffectType.FIRE_RESISTANCE;
        else if (cmd.getName().equalsIgnoreCase("regen") && sender.hasPermission("potioncommand.regen"))
            effect = PotionEffectType.REGENERATION;
        else if (cmd.getName().equalsIgnoreCase("invisibility") && sender.hasPermission("potioncommand.invisibility"))
            effect = PotionEffectType.INVISIBILITY;
        else if (cmd.getName().equalsIgnoreCase("jump") && sender.hasPermission("potioncommand.jump"))
            effect = PotionEffectType.JUMP;
        else if (cmd.getName().equalsIgnoreCase("nightvision") && sender.hasPermission("potioncommand.nightvision"))
            effect = PotionEffectType.NIGHT_VISION;
        else {
            sender.sendMessage(ChatColor.MAGIC + "You do not have permissions to use this command. Consider donating to get access.");
            return false;
        }
        return addEffect(player, effect, duration, strength, coolDownTime);
    }

    private boolean addEffect(Player player, PotionEffectType effect, int duration, int strength, int coolDownTime) {
        if (effect == null) return false;
        String key = player.getName() + "_" + effect.getName();
        if (potionCooldowns.containsKey(key)) {
            long timeLeft = ((potionCooldowns.get(key) / 1000) + coolDownTime) - (System.currentTimeMillis() / 1000);
            if (timeLeft > 0) {
                player.sendMessage(ChatColor.GOLD + "You have to wait another " + timeLeft + " seconds before you can use that command again.");
                return true;
            }
        }
        player.addPotionEffect(new PotionEffect(effect, duration, strength));
        potionCooldowns.put(key, System.currentTimeMillis());
        return true;
    }
}
