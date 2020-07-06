package com.vhbob.bangui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

	public static ItemStack makeWoolItem(Material mat, String name, short data) {
		ItemStack i = new ItemStack(mat, 1, data);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack makeItem(Material mat, String name) {
		ItemStack i = new ItemStack(mat);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public static String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static void logAction(String playerName, String action, BanGUI plugin) {
		File history = new File(plugin.getDataFolder(), playerName + ".yml");
		if (!history.exists()) {
			history.getParentFile().mkdirs();
			try {
				history.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration historyConfig = new YamlConfiguration();
		try {
			historyConfig.load(history);
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		historyConfig.set(Utils.getDate(), action);
		try {
			historyConfig.save(history);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
