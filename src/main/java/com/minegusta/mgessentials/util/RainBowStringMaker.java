package com.minegusta.mgessentials.util;


import org.bukkit.ChatColor;

public class RainBowStringMaker {
    public static String rainbowify(String s) {
        StringBuilder builder = new StringBuilder(s);
        if (s.length() == 0) return s;

        String[] color = {"&4", "&c", "&6", "&e", "&a", "&2", "&b", "&3", "&9", "&1", "&5", "&d"};
        int place = 0;
        for (int index = 0; index < s.length() * 3; index += 3) {
            builder.insert(index, color[place]);
            place = place + 1;
            if (place == 11) {
                place = 0;
            }
        }
        String send = builder.toString();
        send = ChatColor.translateAlternateColorCodes('&', send);
        return send;
    }

}
