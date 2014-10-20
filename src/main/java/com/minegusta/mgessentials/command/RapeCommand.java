package com.minegusta.mgessentials.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class RapeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) return false;
        Player p = (Player) sender;

        List<String> help = Lists.newArrayList("/Rape" + ChatColor.GRAY + " - Show this help.", "/Rape <name>" + ChatColor.GRAY + " - Rape a player.", "/Rape <name> [-s] [-v]" + ChatColor.GRAY + " - Rape someone violently, or enable STD's.");

        if (cmd.getName().equalsIgnoreCase("rape") && p.hasPermission("minegusta.rape")) {
            try {

                if (args.length == 0) {
                    sendHelp(p, help);
                } else if (args.length > 0) {
                    Player victim = Bukkit.getPlayer(args[0]);

                    sendText(victim, "You have been raped by " + ChatColor.RED + p.getName() + ChatColor.YELLOW + "!");
                    sendText(p, "You raped " + ChatColor.RED + victim.getName() + ChatColor.YELLOW + "!");

                    sendSound(victim, Sound.SLIME_WALK);
                    sendSound(victim, Sound.SLIME_WALK2);

                    victim.getWorld().spigot().playEffect(victim.getLocation(), Effect.HEART, 0, 0, 1, 1, 1, 1, 25, 25);

                    if (violentCheck(args)) p.damage(0.0);
                    if (sTDCheck(args)) sTDGenerator(victim);


                }
            } catch (Exception ignored) {
                sendText(p, "That is not a valid player!");
            }
        }
        return true;
    }


    private static void sendText(Player p, String s) {
        p.sendMessage(ChatColor.YELLOW + "[" + ChatColor.GOLD + "MG" + ChatColor.YELLOW + "] " + s);
    }

    private static void sendHelp(Player p, List<String> s) {
        p.sendMessage(ChatColor.RED + "- - - - - - - " + ChatColor.GOLD + "Rape Help" + ChatColor.RED + " - - - - - - -");
        for (String string : s) {
            p.sendMessage(ChatColor.YELLOW + "[" + ChatColor.GOLD + "MG" + ChatColor.YELLOW + "] " + string);
        }
    }

    private static void sTDGenerator(Player p) {
        Random rand = new Random();
        int chance = rand.nextInt(13);
        String infection = "You have been infected with ";
        String disease;
        int strength = 1;
        int duration = 180;
        PotionEffectType pe;

        switch (chance) {
            case 1: {
                disease = ChatColor.RED + "Herpes";
                pe = PotionEffectType.SLOW;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }

            case 2: {
                disease = ChatColor.RED + "Aids";
                pe = PotionEffectType.POISON;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 3: {
                disease = ChatColor.RED + "Rabies";
                pe = PotionEffectType.SPEED;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 4: {
                disease = ChatColor.RED + "Syphilis";
                pe = PotionEffectType.CONFUSION;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 5: {
                disease = ChatColor.RED + "SuperAids";
                pe = PotionEffectType.POISON;
                strength = 2;
                duration = 260;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 6: {
                disease = ChatColor.RED + "Common Cold";
                pe = PotionEffectType.SATURATION;
                duration = 120;
                strength = 0;
                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 7: {
                disease = ChatColor.RED + "Feminism";
                pe = PotionEffectType.DAMAGE_RESISTANCE;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 8: {
                disease = ChatColor.RED + "Anal Bleeding";
                pe = PotionEffectType.HARM;
                duration = 20;
                strength = 0;
                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 9: {
                disease = ChatColor.RED + "Capitalism";
                pe = PotionEffectType.NIGHT_VISION;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 10: {
                disease = ChatColor.RED + "Obesity";
                pe = PotionEffectType.HUNGER;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 11: {
                disease = ChatColor.RED + "Chicken Pox";
                pe = PotionEffectType.WEAKNESS;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 12: {
                disease = ChatColor.RED + "Diarrhea";
                pe = PotionEffectType.SPEED;
                strength = 4;
                duration = 60;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            case 13: {
                disease = ChatColor.RED + "Owl-Syndrome";
                pe = PotionEffectType.NIGHT_VISION;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
            default: {
                disease = ChatColor.RED + "Herpes";
                pe = PotionEffectType.SLOW;

                sendText(p, infection + disease);
                infect(p, pe, strength, duration);
                break;
            }
        }

    }

    private static boolean sTDCheck(String[] list) {
        for (String s : list) {
            if (s.equalsIgnoreCase("-s")) {
                return true;
            }
        }
        return false;
    }

    private static boolean violentCheck(String[] list) {
        for (String s : list) {
            if (s.equalsIgnoreCase("-v")) {
                return true;
            }
        }
        return false;
    }

    private static void sendSound(Player p, Sound s) {
        p.playSound(p.getLocation(), s, 1, 1);
    }

    private static void infect(Player p, PotionEffectType pe, int strength, int duration) {
        p.addPotionEffect(new PotionEffect(pe, duration, strength, false));
    }
}