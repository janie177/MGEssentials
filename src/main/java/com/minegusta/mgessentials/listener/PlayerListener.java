package com.minegusta.mgessentials.listener;

import com.minegusta.mgessentials.data.TempData;
import com.minegusta.mgessentials.ghost.GhostManager;
import com.minegusta.mgessentials.joinsound.JoinSoundManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.UUID;

public class PlayerListener implements Listener {
    //JoinSounds
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogIn(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();

        if (e.getPlayer().hasPermission("minegusta.jointitle") && JoinSoundManager.hasJoinSound(uuid)) {
            JoinSoundManager.playSound(uuid);
            e.setJoinMessage(JoinSoundManager.getMessage(uuid));
        }
        if (TempData.massMute) e.setJoinMessage("");

        //Spook check (Warning, really spooky)
        if (TempData.ghostMode) {
            GhostManager.setSpook(e.getPlayer());
        }
    }

    //Cancel placing Mystery Boxes

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBoxPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (item.getType().equals(Material.CHEST) && item.getItemMeta().hasLore() && item.getItemMeta().getLore().toString().contains("Rightclick the air to open!")) {
            e.setCancelled(true);
        }
    }

    //Open Mystery Box and Sign commands
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMysteryBoxOpen(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        //Spook
        if (TempData.ghostMode && e.getAction() == Action.LEFT_CLICK_AIR) {
            e.getPlayer().performCommand("spook");
        }

        //Sign commands and blocking mob spawner egs
        if (e.hasBlock() && (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            Material mat = e.getMaterial();
            //Spawner blocking
            if (e.getClickedBlock().getType() == Material.MOB_SPAWNER && (mat == Material.MONSTER_EGG || mat == Material.MONSTER_EGGS)) {
                if (!player.isOp()) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "No, you cannot change the spawner type like that.");
                    return;
                }
            }

            //Sign commands
            if (e.getClickedBlock().getType().equals(Material.SIGN) || e.getClickedBlock().getType().equals(Material.WALL_SIGN) || e.getClickedBlock().getType().equals(Material.SIGN_POST)) {
                if (!(e.getClickedBlock().getState() instanceof Sign)) return;
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "[Command]") && sign.getLine(2) != null) {
                    player.chat("/" + sign.getLine(2));
                }
                if (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "[Kit]") && sign.getLine(2) != null) {
                    player.chat("/kit " + sign.getLine(2));
                }
                if (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "[Join]") && sign.getLine(2) != null) {
                    player.chat("/pa " + ChatColor.stripColor(sign.getLine(2)));
                }
                if (sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "[Leave]")) {
                    player.chat("/pa leave");
                }
            }
        }

        //Mystery boxes
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && e.getMaterial().equals(Material.CHEST)) {
            if (!player.getInventory().getItemInMainHand().getItemMeta().hasLore()) return;
            if (player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Rightclick the air to open!")) {
                player.sendMessage(ChatColor.DARK_PURPLE + "[Mystery Box]" + ChatColor.AQUA + " You start opening the box....");
                int oldAmount = player.getInventory().getItemInMainHand().getAmount();
                int newAmount = oldAmount - 1;
                if (player.getInventory().firstEmpty() == -1 && newAmount != 0) {
                    player.sendMessage(ChatColor.DARK_PURPLE + "[Mystery Box]" + ChatColor.RED + "You do not have enough space in your inventory.");
                    return;
                }
                if (newAmount == 0) {
                    player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                } else {
                    player.getInventory().getItemInMainHand().setAmount(newAmount);
                }
                Random rand = new Random();
                int number = rand.nextInt(55);
                ItemStack itemReward = new ItemStack(Material.ARROW, 1);

                switch (number) {

                    case 1:
                        itemReward = new ItemStack(Material.DIAMOND, 2);
                        break;
                    case 2:
                        itemReward = new ItemStack(Material.DIRT, 3);
                        break;
                    case 3:
                        itemReward = new ItemStack(Material.EMERALD, 3);
                        break;
                    case 4:
                        itemReward = new ItemStack(Material.IRON_ORE, 3);
                        break;
                    case 5:
                        itemReward = new ItemStack(Material.GOLD_INGOT, 25);
                        break;
                    case 6:
                        itemReward = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                        break;
                    case 7:
                        itemReward = new ItemStack(Material.DIAMOND_SWORD, 1);
                        break;
                    case 8:
                        itemReward = new ItemStack(Material.DIAMOND_BOOTS, 1);
                        break;
                    case 9:
                        itemReward = new ItemStack(Material.DIAMOND_HELMET, 1);
                        break;
                    case 10:
                        itemReward = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                        break;
                    case 11:
                        itemReward = new ItemStack(Material.DIAMOND_AXE, 1);
                        break;
                    case 12:
                        itemReward = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                        break;
                    case 13:
                        itemReward = new ItemStack(Material.WOOD_BUTTON, 1);
                        break;
                    case 14:
                        itemReward = new ItemStack(Material.MOB_SPAWNER, 1);
                        break;
                    case 15:
                        itemReward = new ItemStack(Material.STONE, 25);
                        break;
                    case 16:
                        itemReward = new ItemStack(Material.COOKIE, 20);
                        break;
                    case 17:
                        itemReward = new ItemStack(Material.REDSTONE, 64);
                        break;
                    case 18:
                        itemReward = new ItemStack(Material.STICK, 20);
                        break;
                    case 19:
                        itemReward = new ItemStack(Material.IRON_SPADE, 1);
                        break;
                    case 20:
                        itemReward = new ItemStack(Material.DIAMOND_SPADE, 1);
                        break;
                    case 21:
                        itemReward = new ItemStack(Material.GREEN_RECORD, 1);
                        break;
                    case 22:
                        itemReward = new ItemStack(Material.POISONOUS_POTATO, 3);
                        break;
                    case 23:
                        itemReward = new ItemStack(Material.DIAMOND_BLOCK, 1);
                        break;
                    case 24:
                        itemReward = new ItemStack(Material.BLAZE_POWDER, 5);
                        break;
                    case 25:
                        itemReward = new ItemStack(Material.EGG, 3);
                        break;
                    case 26:
                        itemReward = new ItemStack(Material.EXP_BOTTLE, 15);
                        break;
                    case 27:
                        itemReward = new ItemStack(Material.BEACON, 1);
                        break;
                    case 28:
                        itemReward = new ItemStack(Material.SKULL, 1);
                        break;
                    case 29:
                        itemReward = new ItemStack(Material.SADDLE, 1);
                        break;
                    case 30:
                        itemReward = new ItemStack(Material.WHEAT, 15);
                        break;
                    case 31:
                        itemReward = new ItemStack(Material.COAL, 9);
                        break;
                    case 32:
                        itemReward = new ItemStack(Material.MELON, 3);
                        break;
                    case 33:
                        itemReward = new ItemStack(Material.CAKE, 1);
                        break;
                    case 34:
                        itemReward = new ItemStack(Material.ARROW, 25);
                        break;
                    case 35:
                        itemReward = new ItemStack(Material.IRON_ORE, 10);
                        break;
                    case 36:
                        itemReward = new ItemStack(Material.WATCH, 1);
                        break;
                    case 37:
                        itemReward = new ItemStack(Material.CACTUS, 1);
                        break;
                    case 38:
                        itemReward = new ItemStack(Material.SNOW, 1);
                        break;
                    case 39:
                        itemReward = new ItemStack(Material.RECORD_8, 1);
                        break;
                    case 40:
                        itemReward = new ItemStack(Material.LEATHER, 1);
                        break;
                    case 41:
                        itemReward = new ItemStack(Material.COBBLESTONE, 1);
                        break;
                    case 42:
                        itemReward = new ItemStack(Material.COBBLE_WALL, 1);
                        break;
                    case 43:
                        itemReward = new ItemStack(Material.COAL_BLOCK, 1);
                        break;
                    case 44:
                        itemReward = new ItemStack(Material.MOSSY_COBBLESTONE, 1);
                        break;
                    case 45:
                        itemReward = new ItemStack(Material.DIAMOND, 3);
                        break;
                    case 46:
                        itemReward = new ItemStack(Material.MAGMA_CREAM, 1);
                        break;
                    case 47:
                        itemReward = new ItemStack(Material.MAP, 1);
                        break;
                    case 48:
                        itemReward = new ItemStack(Material.MINECART, 1);
                        break;
                    case 49:
                        itemReward = new ItemStack(Material.WOOD, 1);
                        break;
                    case 50:
                        itemReward = new ItemStack(Material.WOOD_SWORD, 1);
                        break;
                    case 51:
                        itemReward = new ItemStack(Material.WOOD_AXE, 1);
                        break;
                    case 52:
                        itemReward = new ItemStack(Material.CARROT, 1);
                        break;
                    case 53:
                        itemReward = new ItemStack(Material.FISHING_ROD, 1);
                        break;
                    case 54:
                        itemReward = new ItemStack(Material.FLINT, 1);
                        break;
                    case 55:
                        itemReward = new ItemStack(Material.FLINT_AND_STEEL, 1);
                        break;
                }
                player.getInventory().addItem(itemReward);
                player.updateInventory();
                player.sendMessage(ChatColor.DARK_PURPLE + "[Mystery Box]" + ChatColor.AQUA + " And receive a random item!");


            }
        }
    }
}
