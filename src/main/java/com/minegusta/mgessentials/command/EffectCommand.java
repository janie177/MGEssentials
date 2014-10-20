package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.data.TempData;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EffectCommand implements CommandExecutor {

    

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender.hasPermission("minegusta.particles")) 
        {
            if (sender instanceof ConsoleCommandSender) return false;

            String logo = ChatColor.DARK_RED + "[" + ChatColor.RED + "MineGusta Particles" + ChatColor.DARK_RED + "]";
            String header = ChatColor.AQUA + "=================================================";
            String list = ChatColor.BLUE + "/Particle Flames/Smoke/Hearts/Ender/Rainbow/Snow";
            String list2 = ChatColor.BLUE + "/Particle Magic/Green/Cloud/Note/Glyph/Portal/Off";

            if (args.length == 0) {
                sender.sendMessage(logo);
                sender.sendMessage(header);
                sender.sendMessage(list);
                sender.sendMessage(list2);
                return (true);

            } else {
                
                Player p = (Player) sender;

                String whichEffect = args[0].toLowerCase();

                if (whichEffect.equalsIgnoreCase("magic")) {
                    TempData.effectMap.put(p, Effect.WITCH_MAGIC);
                } else if (whichEffect.equalsIgnoreCase("smoke")) {
                    TempData.effectMap.put(p, Effect.SMOKE);
                } else if (whichEffect.equalsIgnoreCase("ender")) {
                    TempData.effectMap.put(p, Effect.ENDER_SIGNAL);
                } else if (whichEffect.equalsIgnoreCase("hearts")) {
                    TempData.effectMap.put(p, Effect.HEART);
                } else if (whichEffect.equalsIgnoreCase("rainbow")) {
                    TempData.effectMap.put(p, Effect.COLOURED_DUST);
                } else if (whichEffect.equalsIgnoreCase("flames")) {
                    TempData.effectMap.put(p, Effect.MOBSPAWNER_FLAMES);
                } else if (whichEffect.equalsIgnoreCase("green")) {
                    TempData.effectMap.put(p, Effect.HAPPY_VILLAGER);
                } else if (whichEffect.equalsIgnoreCase("cloud")) {
                    TempData.effectMap.put(p, Effect.VILLAGER_THUNDERCLOUD);
                } else if (whichEffect.equalsIgnoreCase("note")) {
                    TempData.effectMap.put(p, Effect.NOTE);
                } else if (whichEffect.equalsIgnoreCase("glyph")) {
                    TempData.effectMap.put(p, Effect.FLYING_GLYPH);
                } else if (whichEffect.equalsIgnoreCase("portal")) {
                    TempData.effectMap.put(p, Effect.PORTAL);
                } else if (whichEffect.equalsIgnoreCase("snow")) {
                    TempData.effectMap.put(p, Effect.SNOWBALL_BREAK);

                } else if (whichEffect.equalsIgnoreCase("off")) {
                    if(TempData.effectMap.containsKey(p))TempData.effectMap.remove(p);
                    sender.sendMessage(logo);
                    sender.sendMessage(header);
                    sender.sendMessage(ChatColor.BLUE + "You now have " + ChatColor.RED + "no" + ChatColor.BLUE + " effect enabled.");
                    return true;
                } else whichEffect = null;
                sender.sendMessage(logo);
                sender.sendMessage(header);
                if (whichEffect != null)
                    sender.sendMessage(ChatColor.BLUE + "You now have the " + ChatColor.RED + whichEffect + ChatColor.BLUE + " effect enabled.");
                else {
                    sender.sendMessage(ChatColor.RED + "That is an unknown type of particle!");
                    sender.sendMessage(list);
                    sender.sendMessage(list2);
                }
            }
        }
        return true;
    }
}
