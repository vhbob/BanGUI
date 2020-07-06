package com.vhbob.bangui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.vhbob.bangui.commands.GUIBan;
import com.vhbob.bangui.commands.History;
import com.vhbob.bangui.commands.SBan;
import com.vhbob.bangui.events.ApplyDuration;
import com.vhbob.bangui.events.ApplyReason;
import com.vhbob.bangui.events.KickAndMuteHistory;

public class BanGUI extends JavaPlugin {

	/*
	 * Create HashMap to hold info of bans being processed The first Hashmap will
	 * hold a Player who is doing the banning, and an object holding the ban info
	 */
	private HashMap<Player, BanInfo> banning;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Registering Ban GUI commands");
		getCommand("GUIBan").setExecutor(new GUIBan(this));
		getCommand("SBan").setExecutor(new SBan(this));
		getCommand("History").setExecutor(new History(this));
		banning = new HashMap<>();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Registering Ban GUI events");
		Bukkit.getPluginManager().registerEvents(new ApplyDuration(this), this);
		Bukkit.getPluginManager().registerEvents(new ApplyReason(this), this);
		Bukkit.getPluginManager().registerEvents(new KickAndMuteHistory(this), this);
		saveDefaultConfig();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Ban GUI has been enabled!");
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Ban GUI has been successfully disabled!");
	}

	public HashMap<Player, BanInfo> getBanning() {
		return banning;
	}

}
