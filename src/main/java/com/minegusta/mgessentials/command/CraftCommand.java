package com.minegusta.mgessentials.command;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.ConcurrentMap;

public class CraftCommand implements CommandExecutor {
    public static final ConcurrentMap<Inventory, Boolean> craftingInstances = Maps.newConcurrentMap();

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (s instanceof Player && s.hasPermission("minegusta.craft")) {
            Player p = (Player) s;
            Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH);
            p.openInventory(inv);
            craftingInstances.put(inv, false);
        }
        return true;
    }
}
