package com.minegusta.mgessentials.listener;

import com.google.common.collect.Lists;
import com.minegusta.mgessentials.util.RainBowStringMaker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class BlockListener implements Listener {
    @EventHandler
    public void onSignsCreateEvent(SignChangeEvent event) {
        String text1 = event.getLine(0);
        String text2 = event.getLine(1);
        String text3 = event.getLine(2);
        String text4 = event.getLine(3);
        if (event.getLine(0) != null) {
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', text1));
            if (text1.contains("&!")) {
                event.setLine(0, RainBowStringMaker.rainbowify(text1.replace("&!", "")));
            }
        }
        if (event.getLine(1) != null) {
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', text2));
            if (text2.contains("&!")) {
                event.setLine(1, RainBowStringMaker.rainbowify(text2.replace("&!", "")));
            }
        }
        if (event.getLine(2) != null) {
            event.setLine(2, ChatColor.translateAlternateColorCodes('&', text3));
            if (text3.contains("&!")) {
                event.setLine(2, RainBowStringMaker.rainbowify(text3.replace("&!", "")));
            }
        }
        if (event.getLine(3) != null) {
            event.setLine(3, ChatColor.translateAlternateColorCodes('&', text4));
            if (text4.contains("&!")) {
                event.setLine(3, RainBowStringMaker.rainbowify(text4.replace("&!", "")));
            }
        }
        if (text2 != null && (text2.equalsIgnoreCase("[command]") || text2.equalsIgnoreCase("[cmd]"))) {
            event.setLine(1, ChatColor.RED + "[Command]");
        }

        if (text2 != null && text2.equalsIgnoreCase("[kit]")) {
            event.setLine(1, ChatColor.GREEN + "[Kit]");
        }
        if (text2 != null && text2.equalsIgnoreCase("[Join]") && event.getPlayer().isOp()) {
            event.setLine(1, ChatColor.GREEN + "[Join]");
        }
        if (text2 != null && text2.equalsIgnoreCase("[Leave]") && event.getPlayer().isOp()) {
            event.setLine(1, ChatColor.RED + "[Leave]");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMineStuff(BlockBreakEvent e) {
        if (!(e.isCancelled()) && e.getPlayer().hasPermission("minegusta.silkspawner")) {
            Material block = e.getBlock().getType();
            if (block.equals(Material.MOB_SPAWNER)) {
                if (e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    e.getPlayer().sendMessage(ChatColor.DARK_RED + "You cannot mine spawners with the fortune enchantment.");
                    e.setCancelled(true);
                } else {


                    CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
                    EntityType entity = spawner.getSpawnedType();


                    ItemStack mobSpawner = new ItemStack(Material.MOB_SPAWNER, 1);
                    ArrayList<String> spawnerType = Lists.newArrayList();
                    spawnerType.add(ChatColor.YELLOW + entity.name() + " Spawner");
                    ItemMeta meta = mobSpawner.getItemMeta();
                    meta.setLore(spawnerType);
                    mobSpawner.setItemMeta(meta);


                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), mobSpawner);
                    e.setExpToDrop(0);

                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        ItemStack spawner = e.getItemInHand();
        if (spawner.getType().equals(Material.MOB_SPAWNER) && !e.isCancelled()) {
            if (spawner.getItemMeta().hasLore()) {
                String lore = ChatColor.stripColor(spawner.getItemMeta().getLore().get(0));
                if (lore.toLowerCase().contains(" spawner")) {
                    EntityType type;
                    String typeName = lore.toLowerCase().replace(" spawner", "").replace(".", "").toUpperCase();
                    try {
                        type = EntityType.valueOf(typeName);
                    } catch (Exception ignored) {
                        type = EntityType.PIG;
                    }

                    CreatureSpawner placedSpawner = (CreatureSpawner) block.getState();

                    placedSpawner.setSpawnedType(type);

                    placedSpawner.update();
                }
            }
        }
    }

    //Speed boost pads
    @EventHandler(priority = EventPriority.LOWEST)
    public void pressurepadEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType().equals(Material.STONE_PLATE) && e.getPlayer().hasPermission("minegusta.launch") && e.getClickedBlock().getRelative(BlockFace.DOWN).getType().equals(Material.LAPIS_BLOCK)) {
            //It's a pressure plate and player has the perms
            Player p = e.getPlayer();
            p.teleport(p.getLocation().clone().add(0, 0.1, 0));
            p.setVelocity(p.getLocation().getDirection().normalize().add(new Vector(0, 0.5, 0)).multiply(2.5));
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
        }
    }

    //Wool bounce.

    @EventHandler
    public void onWoolBounce(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (e.getEntity().getType().equals(EntityType.PLAYER)) {
                Player player = (Player) e.getEntity();
                if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL)
                        && player.getLocation().getBlock()
                        .getRelative(BlockFace.DOWN, 2).getType().equals(Material.WOOL) && !(player.isSneaking())) {
                    e.setCancelled(true);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 0.4F, 0.2F);
                    Vector direction = player.getLocation().getDirection();
                    player.setVelocity(direction.setY(1).multiply(1.1));
                } else if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL)
                        && player.getLocation().getBlock()
                        .getRelative(BlockFace.DOWN, 2).getType().equals(Material.WOOL) && player.isSneaking()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}