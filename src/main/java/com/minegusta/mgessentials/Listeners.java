package com.minegusta.mgessentials;

import com.minegusta.mgessentials.listener.*;
import com.minegusta.mgessentials.pvplog.PvpLogListener;
import org.bukkit.event.Listener;

public enum Listeners {
    L1(new BlockListener()),
    L2(new ChatListener()),
    L3(new EntityListener()),
    L4(new PlayerListener()),
    PVPLOGLISTENER(new PvpLogListener()),
    L5(new ProjectileListener());

    private Listener listener;

    private Listeners(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }
}
