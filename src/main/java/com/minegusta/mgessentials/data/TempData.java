package com.minegusta.mgessentials.data;

import com.google.common.collect.Maps;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class TempData {
    public static boolean massMute = false;
    public static boolean ghostMode = false;

    public static ConcurrentMap<String, Boolean> popMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Boolean> nukeMap = Maps.newConcurrentMap();
    public static ConcurrentMap<Player, Effect> effectMap = Maps.newConcurrentMap();
    public static ConcurrentMap<UUID, Long> spookMap = Maps.newConcurrentMap();

    public static ConcurrentMap<String, Inventory> pvpLogMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Long> tagMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Boolean> botMap = Maps.newConcurrentMap();
}
