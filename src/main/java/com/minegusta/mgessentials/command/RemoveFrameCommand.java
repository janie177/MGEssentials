package com.minegusta.mgessentials.command;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveFrameCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player) || !sender.isOp()) return true;
		Player p = (Player) sender;

		List<Entity> entities = Lists.newArrayList();
		p.getWorld().getEntitiesByClass(ItemFrame.class).stream().filter(frame -> frame.getLocation().distance(p.getLocation()) < 5).forEach(entities::add);

		p.sendMessage(ChatColor.GREEN + "Removing frames in a radius of 4 around you...");

		entities.stream().forEach(Entity::remove);
		entities.stream().filter(Entity::isValid).forEach(ent -> ent.teleport(p.getLocation()));
		entities.stream().filter(Entity::isValid).forEach(Entity::remove);
		int amount = entities.size();
		p.sendMessage(ChatColor.GREEN + "Removed " + amount + " item frames.");
		entities.clear();
		p.getWorld().getEntitiesByClass(ItemFrame.class).stream().filter(frame -> frame.getLocation().distance(p.getLocation()) < 5).forEach(ent ->
		{
			ent.setCustomName(ChatColor.RED + "STUCK");
			ent.setCustomNameVisible(true);
		});


		return true;
	}
}
