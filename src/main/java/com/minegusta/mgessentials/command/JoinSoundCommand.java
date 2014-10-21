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
            if (args[0].equalsIgnoreCase("set")) {
                Player p;
                float volume;
                float pitch;
                String message;
                Sound sound;
                try {
                    message = ChatColor.translateAlternateColorCodes('&', args[1].replace("_", " "));
                    p = Bukkit.getPlayer(args[2]);
                    pitch = Float.parseFloat(args[5]);
                    volume = Float.parseFloat(args[4]);
                    sound = Sound.valueOf(args[3]);

                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.DARK_RED + "Something was wrong with your input!");
                    sendHelp(s);
                    return true;
                }
                JoinSoundManager.setSound(p.getUniqueId(), message, sound, volume, pitch);
                return true;

            }
            if (args[0].equalsIgnoreCase("remove")) {
                Player p;
                try {
                    p = Bukkit.getPlayer(args[2]);

                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.DARK_RED + "Something was wrong with your input!");
                    sendHelp(s);
                    return true;
                }
                JoinSoundManager.removeSound(p.getUniqueId());
                return true;
            }
        }
        sendHelp(s);
        return true;
    }

    private void sendHelp(CommandSender s) {
        s.sendMessage(ChatColor.YELLOW + "You tried to edit a JoinSound but failed epicly!");
        s.sendMessage(ChatColor.YELLOW + "To set a joinsound, use the following format:");
        s.sendMessage(ChatColor.RED + "/JS Set <Player> <Message_With_Underscores> <Sound> <Volume> <Pitch>");
        s.sendMessage(ChatColor.YELLOW + "Ex:" + ChatColor.RED + "/JS Set janie177 &2Jan_Is_A_Noob PIG_IDLE 1 1");
        s.sendMessage(ChatColor.YELLOW + "To remove a join sound use:");
        s.sendMessage(ChatColor.RED + "/JnS Remove <Player>");
        s.sendMessage(ChatColor.YELLOW + "A list of sounds can be found here:");
        s.sendMessage(ChatColor.LIGHT_PURPLE + "http://jd.bukkit.org/beta/apidocs/org/bukkit/Sound.html");
    }
}
