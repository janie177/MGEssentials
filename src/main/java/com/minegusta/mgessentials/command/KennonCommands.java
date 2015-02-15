package com.minegusta.mgessentials.command;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KennonCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) return true;
        Player kennon = (Player) s;
        if (!kennon.getUniqueId().toString().equals("3ef8f219-afe8-43a9-93d8-96fdd65198cf")) return true;

        if (args.length == 0) {
            kennon.sendMessage(ChatColor.LIGHT_PURPLE + "You need to supply a player! >:c");
            kennon.sendMessage(ChatColor.LIGHT_PURPLE + "/Lick <Maus>)");
            kennon.sendMessage(ChatColor.LIGHT_PURPLE + "/Pet <Maus>)");
        }
        if (cmd.getName().equalsIgnoreCase("lick")) {
            if (args[0].equalsIgnoreCase("*")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    lickPlayer(p, kennon);
                }
                return true;
            }
            try {
                Player licked = Bukkit.getPlayer(args[0]);

                if (licked.getUniqueId().toString().equalsIgnoreCase("3ef8f219-afe8-43a9-93d8-96fdd65198cf")) {
                    kennon.sendMessage(ChatColor.RED + "I am not going to let you lick yourself. Like ew.");
                    kennon.sendMessage(ChatColor.RED + "I know you are weird, but this goes toooo far.");
                    kennon.sendMessage(ChatColor.RED + "Why would you even want to lick yourself?");
                    kennon.sendMessage(ChatColor.RED + "I mean if you need a lick, I can do that ;D");
                    kennon.sendMessage(ChatColor.RED + "Ask your friendly neighbourhood maus.");
                    kennon.sendMessage(ChatColor.RED + "You'll even get free rabies B).");
                    kennon.sendMessage(ChatColor.RED + "Also yeah I had nothing better to do.");
                    return true;
                }

                lickPlayer(licked, kennon);
                return true;

            } catch (Exception ignored) {
                kennon.sendMessage(ChatColor.LIGHT_PURPLE + "Try harder Kern! That player is not real u nub.");
            }

        } else if (cmd.getName().equalsIgnoreCase("pet")) {
            if (args[0].equalsIgnoreCase("*")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    petPlayer(p, kennon);
                }
                return true;
            }
            try {
                Player petted = Bukkit.getPlayer(args[0]);

                if (petted.getUniqueId().toString().equalsIgnoreCase("3ef8f219-afe8-43a9-93d8-96fdd65198cf")) {
                    kennon.sendMessage(ChatColor.LIGHT_PURPLE + "Soft Kennon, Warm Kennon,");
                    kennon.sendMessage(ChatColor.LIGHT_PURPLE + "little ball of fur.");
                    kennon.sendMessage(ChatColor.LIGHT_PURPLE + "Happy Kennon, Sleepy Kennon,");
                    kennon.sendMessage(ChatColor.LIGHT_PURPLE + "Purr, Purr, Purr.");
                    return true;
                }

                petPlayer(petted, kennon);
                return true;

            } catch (Exception ignored) {
                kennon.sendMessage(ChatColor.LIGHT_PURPLE + "Try harder Kern! That player is not real u nub.");
            }
        }
        return true;
    }

    private void lickPlayer(Player p, Player kennon) {
        kennon.sendMessage(ChatColor.YELLOW + "You licked " + ChatColor.LIGHT_PURPLE + p.getDisplayName() + ChatColor.YELLOW + ". Rekt.");
        p.sendMessage(ChatColor.YELLOW + "Kennon has licked you. You have been washed. You're welcome.");
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.POTION_BREAK, 16389, 1, 1, 1, 1, 1, 1, 20);
    }

    private void petPlayer(Player p, Player kennon) {
        kennon.sendMessage(ChatColor.YELLOW + "You petted " + ChatColor.LIGHT_PURPLE + p.getDisplayName() + ChatColor.YELLOW + ".");
        p.sendMessage(ChatColor.YELLOW + "Kennon is petting you!");
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Petpetpetpetpetpetpetpetpetpetpetpetpetpetpetpetpet");
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Petpetpetpetpetpetpetpetpetpetpetpetpetpetpetpetpet");
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.POTION_BREAK, 16388, 1, 1, 1, 1, 1, 1, 20);
    }
}
