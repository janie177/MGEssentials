package com.minegusta.mgessentials.command;

import com.minegusta.mgessentials.joinsound.JoinSoundManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinSoundCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.isOp() && !s.hasPermission("minegusta.joinsound")) return false;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("set") && args.length >= 6) {
                Player p;
                float volume;
                float pitch;
                String message = "";
                Sound sound;
                try {
                    for (int i = 2; i < args.length - 3; i++) {
                        message = message + args[i] + " ";
                    }
                    message = message.replace("%and%", "||").trim();
                    p = Bukkit.getPlayer(args[1]);

                    if (!s.hasPermission("minegusta.joinsound.other") && s instanceof Player && !p.getUniqueId().toString().equalsIgnoreCase(((Player) s).getUniqueId().toString())) {
                        s.sendMessage(ChatColor.RED + "You can only change your own Join Sound!");
                        return true;
                    }

                    pitch = Float.parseFloat(args[args.length - 1]);
                    if (pitch < 0 || pitch > 10) {
                        pitch = 1;
                    }
                    volume = Float.parseFloat(args[args.length - 2]);
                    if (volume < 0 || volume > 10) {
                        pitch = 1;
                    }
                    sound = Sound.valueOf(args[args.length - 3]);

                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.DARK_RED + "Something was wrong with your input!");
                    return true;
                }
                if (sound == null) {
                    s.sendMessage(ChatColor.RED + "That sound is not real!");
                    return true;
                }
                if (p == null) {
                    s.sendMessage(ChatColor.RED + "That player is not real!");
                    return true;
                }
                JoinSoundManager.setSound(p.getUniqueId(), message, sound, volume, pitch);
                s.sendMessage(ChatColor.GREEN + "You set " + p.getName() + "'s JoinSound!");
                return true;

            }
            if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
                Player p;
                try {
                    p = Bukkit.getPlayer(args[1]);

                    if (!s.hasPermission("minegusta.joinsound.other") && s instanceof Player && !p.getUniqueId().toString().equalsIgnoreCase(((Player) s).getUniqueId().toString())) {
                        s.sendMessage(ChatColor.RED + "You can only change your own Join Sound!");
                        return true;
                    }

                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.DARK_RED + "That player appears not to exist!");
                    return true;
                }
                JoinSoundManager.removeSound(p.getUniqueId());
                s.sendMessage(ChatColor.GREEN + "You removed a JoinSound!");
                return true;
            }
        }
        sendHelp(s);
        return true;
    }

    private String[] help = {"To set a JoinSound, use the following format:",
            ChatColor.RED + "/JS Set <Player> <Message> <Sound> <Volume> <Pitch>",
            "Ex: " + ChatColor.RED + "/JS Set janie177 &2Jan Is Blind PIG_IDLE 1 1",
            ChatColor.GRAY + "The player has to be online for this to work.",
            "To remove a join sound use:",
            ChatColor.RED + "/JS Remove <Player>",
            "A list of sounds can be found here:",
            ChatColor.LIGHT_PURPLE + "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html"};

    private void sendHelp(CommandSender s) {
        s.sendMessage(ChatColor.DARK_RED + " - - - " + ChatColor.LIGHT_PURPLE + "Join Sound" + ChatColor.DARK_RED + " - - - ");

        for (String string : help) {
            s.sendMessage(ChatColor.YELLOW + "[" + ChatColor.LIGHT_PURPLE + "MG" + ChatColor.YELLOW + "] " + ChatColor.DARK_PURPLE + string);
        }
    }
}
