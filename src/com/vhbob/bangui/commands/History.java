package com.vhbob.bangui.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.vhbob.bangui.BanGUI;

public class History implements CommandExecutor {

	private BanGUI plugin;

	public History(BanGUI plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (cmd.getName().equalsIgnoreCase("History")) {
			if (sender.hasPermission("history.view")) {
				if (args.length == 1) {
					File playerFile = new File(plugin.getDataFolder(), args[0] + ".yml");
					if (playerFile.exists()) {
						sender.sendMessage(ChatColor.RED + "Here is the user's punishment history");
						YamlConfiguration historyConfig = new YamlConfiguration();
						try {
							historyConfig.load(playerFile);
						} catch (IOException | InvalidConfigurationException e1) {
							e1.printStackTrace();
						}
						for (String s : historyConfig.getConfigurationSection("").getKeys(false)) {
							sender.sendMessage(s + " - " + historyConfig.getString(s));
						}
					} else {
						sender.sendMessage(ChatColor.RED + args[0] + " has no punishment history!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Usage: /history (player name)");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Missing permission: history.view");
			}
		}
		return false;
	}

}
