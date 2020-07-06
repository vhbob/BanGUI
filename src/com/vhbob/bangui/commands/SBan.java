package com.vhbob.bangui.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.vhbob.bangui.BanGUI;
import com.vhbob.bangui.BanInfo;
import com.vhbob.bangui.Utils;

public class SBan implements CommandExecutor {
	private BanGUI plugin;

	public SBan(BanGUI plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (cmd.getName().equalsIgnoreCase("SBan")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("sban.gui")) {
					if (args.length == 1) {
						if (Bukkit.getOfflinePlayer(args[0]) != null) {
							if (!Bukkit.getOfflinePlayer(args[0]).isBanned()) {
								Inventory i = Bukkit.createInventory(null, 27, "Ban length: " + args[0]);
								i.setItem(9,
										Utils.makeWoolItem(Material.WOOL, ChatColor.DARK_RED + "Permanent", (byte) 14));
								i.setItem(11,
										Utils.makeWoolItem(Material.WOOL, ChatColor.DARK_RED + "6 Months", (byte) 14));
								i.setItem(13, Utils.makeWoolItem(Material.WOOL, ChatColor.GOLD + "3 Months", (byte) 1));
								i.setItem(15, Utils.makeWoolItem(Material.WOOL, ChatColor.YELLOW + "1 Week", (byte) 4));
								i.setItem(17, Utils.makeWoolItem(Material.WOOL, ChatColor.YELLOW + "1 Hour", (byte) 4));
								Player banning = (Player) sender;
								plugin.getBanning().put(banning, new BanInfo(args[0], true));
								banning.openInventory(i);
							} else {
								sender.sendMessage(ChatColor.RED + "Player already banned!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Player not found!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Usage: /SBan (name)");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Missing permission: sban.gui");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to do this");
			}
		}
		return false;
	}

}
