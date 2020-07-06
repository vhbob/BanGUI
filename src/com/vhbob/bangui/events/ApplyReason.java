package com.vhbob.bangui.events;

import java.util.ArrayList;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;

import com.vhbob.bangui.BanGUI;
import com.vhbob.bangui.BanInfo;
import com.vhbob.bangui.Utils;

@SuppressWarnings("deprecation")
public class ApplyReason implements Listener {

	private BanGUI plugin;
	private ArrayList<Player> adding;

	public ApplyReason(BanGUI plugin) {
		this.plugin = plugin;
		adding = new ArrayList<Player>();
	}

	@EventHandler
	public void applyReason(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			if (e.getClickedInventory().getTitle().contains("Reason: "))
				e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (plugin.getBanning().containsKey(p)) {
				if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
					String clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
					BanInfo bi = plugin.getBanning().get(p);
					if (clickedName.equalsIgnoreCase("Cheating") || clickedName.equalsIgnoreCase("Advertising")
							|| clickedName.equalsIgnoreCase("Spamming")) {
						bi.setReason(clickedName);
						p.closeInventory();
						doBan(p, bi);
					} else if (clickedName.equalsIgnoreCase("Other")) {
						p.closeInventory();
						p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Please enter the reason for the ban");
						adding.add(p);
					}
				}
			}
		}
	}

	@EventHandler
	public void otherReason(PlayerChatEvent e) {
		if (adding.contains(e.getPlayer())) {
			e.setCancelled(true);
			adding.remove(e.getPlayer());
			BanInfo bi = plugin.getBanning().get(e.getPlayer());
			bi.setReason(e.getMessage());
			doBan(e.getPlayer(), bi);
		}
	}

	public void doBan(Player p, BanInfo bi) {
		plugin.reloadConfig();
		if (Bukkit.getPlayer(bi.getTarget()) != null)
			Bukkit.getPlayer(bi.getTarget()).kickPlayer(bi.getReason() + "\n"
					+ ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("added-message")));
		Bukkit.getBanList(Type.NAME).addBan(bi.getTarget(),
				bi.getReason() + "\n"
						+ ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("added-message")),
				bi.getEnd(), p.getName());
		if (!bi.isSilent()) {
			if (!bi.getDuration().equalsIgnoreCase("Permanent")) {
				Bukkit.getServer()
						.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + bi.getTarget() + ChatColor.WHITE
								+ "" + ChatColor.BOLD + " has been " + ChatColor.DARK_RED + "" + ChatColor.BOLD
								+ "banned " + ChatColor.WHITE + "" + ChatColor.BOLD + "by " + p.getName() + " for "
								+ bi.getDuration() + " due to " + bi.getReason());
			} else {
				Bukkit.getServer()
						.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + bi.getTarget() + ChatColor.WHITE
								+ "" + ChatColor.BOLD + " has been " + ChatColor.DARK_RED + "" + ChatColor.BOLD
								+ "permanently banned" + ChatColor.WHITE + "" + ChatColor.BOLD + " by " + p.getName()
								+ " due to " + bi.getReason());
			}
		} else {
			p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Banned " + bi.getTarget() + " for "
					+ bi.getReason() + ": " + bi.getDuration());
		}
		Utils.logAction(bi.getTarget(), "Banned for " + bi.getReason() + " by " + p.getName() + ": " + bi.getDuration(),
				plugin);
		plugin.getBanning().remove(p);
	}

}
