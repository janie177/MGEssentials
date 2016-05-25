package com.minegusta.mgessentials.command;

import com.google.common.collect.Lists;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class InquisitionCommand implements CommandExecutor {

	private static final Random random = new Random();
	private static final List<String> messages = Lists.newArrayList("¡Aquí es tu muerte!", "¡Admita a tus delitos!", "¡Ve con Dios!", "¡Es el tiempo para la silla..... de las uñas!", "¡Masón! ¡Que significan los números!", "¡Para el Papa!", "¡Nosotros queremos su dinero para su vida!");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("minegusta.inquisition")) {
			sender.sendMessage(ChatColor.RED + "You are not Spanish enough to use this command.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "/Inquisition <name>");
			return true;
		}

		Player player = null;
		try {
			player = Bukkit.getPlayer(args[0]);
		} catch (Exception ignored) {
			sender.sendMessage(ChatColor.RED + "That player could not be found.");
			return true;
		}

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "That player could not be found.");
			return true;
		}

		//spawn the skeletons
		List<Skeleton> skeletons = Lists.newArrayList();

		for (int i = 0; i < 3; i++) {
			Skeleton skeleton = (Skeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
			skeletons.add(skeleton);

			skeleton.setCustomName(ChatColor.RED + "Spanish Inquisitor");
			skeleton.setCustomNameVisible(true);
			skeleton.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 6000, 4));

			skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
			skeleton.getEquipment().setItemInOffHand(new ItemStack(Material.IRON_SWORD));
			skeleton.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET) {
				{
					LeatherArmorMeta meta = (LeatherArmorMeta) getItemMeta();
					meta.setColor(Color.RED);
					setItemMeta(meta);
				}
			});
			skeleton.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE) {
				{
					LeatherArmorMeta meta = (LeatherArmorMeta) getItemMeta();
					meta.setColor(Color.RED);
					setItemMeta(meta);
				}
			});
			skeleton.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS) {
				{
					LeatherArmorMeta meta = (LeatherArmorMeta) getItemMeta();
					meta.setColor(Color.RED);
					setItemMeta(meta);
				}
			});
			skeleton.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS) {
				{
					LeatherArmorMeta meta = (LeatherArmorMeta) getItemMeta();
					meta.setColor(Color.RED);
					setItemMeta(meta);
				}
			});

			skeleton.getEquipment().setItemInMainHandDropChance(0);
			skeleton.getEquipment().setItemInOffHandDropChance(0);
			skeleton.getEquipment().setHelmetDropChance(0);
			skeleton.getEquipment().setChestplateDropChance(0);
			skeleton.getEquipment().setBootsDropChance(0);
			skeleton.getEquipment().setLeggingsDropChance(0);
		}

		//task
		for (int i = 0; i < 6; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.PLUGIN, () ->
			{
				for (Skeleton s : skeletons) {
					if (s.isValid()) {
						if (random.nextInt(3) == 1) {
							String message = messages.get(random.nextInt(messages.size()));
							s.getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(s.getLocation()) < 15).forEach(p -> p.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "Inquisitor" + ChatColor.YELLOW + "] " + ChatColor.GRAY + message));
						}
					} else {
						skeletons.remove(s);
					}
				}
			}, i * 20);
		}

		//send player message
		final Player finalPlayer = player;

		Title title = new Title(ChatColor.RED + "Nobody expects the Spanish inquisition!");
		title.setFadeInTime(2);
		title.setStayTime(5);
		title.setFadeOutTime(2);

		finalPlayer.getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(finalPlayer.getLocation()) < 15).forEach(title::send);

		//feedback
		sender.sendMessage(ChatColor.YELLOW + "You inquisited " + player.getName() + ".");

		return true;
	}
}
