package com.minegusta.mgessentials.listener;


import com.minegusta.mgessentials.data.TempData;
import com.minegusta.mgessentials.util.RainBowStringMaker;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String p = e.getPlayer().getName();
        if (TempData.massMute && !e.getPlayer().hasPermission("minegusta.massmute.exempt")) {
            e.getPlayer().sendMessage(ChatColor.RED + "The server is in muted mode! Only staff can talk.");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRainbowChat(AsyncPlayerChatEvent e) {
        String m = e.getMessage();
        if (m.contains("&!") && e.getPlayer().hasPermission("minegusta.rainbowchat")) {
            try {
                e.setMessage(RainBowStringMaker.rainbowify(m.replace("&!", "")));
            } catch (Exception ignored) {
            }
        }
    }

    @EventHandler
    public void onSpamChat(PlayerCommandPreprocessEvent e) {
        if (TempData.massMute && !e.getPlayer().hasPermission("minegusta.massmute.exempt")) {
            String message = e.getMessage();
            message = message.substring(1);
            String[] args = message.split("\\s+");
            if (args[0].equalsIgnoreCase("hail") || args[0].equalsIgnoreCase("highfive") || args[0].equalsIgnoreCase("nuke") || args[0].equalsIgnoreCase("slap") || args[0].equalsIgnoreCase("me") || args[0].equalsIgnoreCase("bukkit") || args[0].equalsIgnoreCase("name") || args[0].equalsIgnoreCase("create")) {
                e.getPlayer().sendMessage(ChatColor.RED + "The server is in muted mode! Only staff can talk.");
                e.setCancelled(true);
            }
        }
    }
}
