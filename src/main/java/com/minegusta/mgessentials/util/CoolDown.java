package com.minegusta.mgessentials.util;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CoolDown {
    /**
     * Create a new cooldown.
     *
     * @param uuid The uuid of the player.
     * @param map  The map to store the values in.
     */
    public static void newCooldown(UUID uuid, ConcurrentMap<UUID, Long> map) {
        map.put(uuid, System.currentTimeMillis());
    }

    /**
     * Is this cooldown over?
     *
     * @param uuid     The player in the cooldown.
     * @param map      The map the cooldown is stored in.
     * @param coolDown The time period it takes to cool down.
     * @return Wether the cooldown period is over.
     */
    public static boolean cooledDown(UUID uuid, ConcurrentMap<UUID, Long> map, int coolDown) {
        return !map.containsKey(uuid) || TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - map.get(uuid)) >= coolDown;
    }

    /**
     * Method to see the remaining time.
     *
     * @param uuid     The Player UUID.
     * @param map      The map with the data stored in it.
     * @param coolDown The time it takes for this cooldown to end.
     * @return The remaining cooldown time in seconds.
     */
    public static long getRemainingTime(UUID uuid, ConcurrentMap<UUID, Long> map, int coolDown) {
        return coolDown - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - map.get(uuid));
    }
}
