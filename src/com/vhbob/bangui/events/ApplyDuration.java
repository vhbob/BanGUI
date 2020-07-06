package com.vhbob.bangui.events;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.vhbob.bangui.BanGUI;
import com.vhbob.bangui.Utils;

public class ApplyDuration implements Listener {

	private BanGUI plugin;

	public ApplyDuration(BanGUI plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void applyDuration(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			if (e.getClickedInventory().getTitle().contains("Ban length: "))
				e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (plugin.getBanning().containsKey(p)) {
				if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
					String clickedName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
					boolean addedTime = true;
					if (clickedName.equalsIgnoreCase("Permanent"))
						plugin.getBanning().get(p).setEnd(null);
					else if (clickedName.equalsIgnoreCase("6 Months"))
						plugin.getBanning().get(p).setEnd(new Date(System.currentTimeMillis() + 3600000 * 24 * 180));
					else if (clickedName.equalsIgnoreCase("3 Months"))
						plugin.getBanning().get(p).setEnd(new Date(System.currentTimeMillis() + 3600000 * 24 * 90));
					else if (clickedName.equalsIgnoreCase("1 Week"))
						plugin.getBanning().get(p).setEnd(new Date(System.currentTimeMillis() + 3600000 * 24 * 7));
					else if (clickedName.equalsIgnoreCase("1 Hour"))
						plugin.getBanning().get(p).setEnd(new Date(System.currentTimeMillis() + 3600000));
					else
						addedTime = false;
					if (addedTime) {
						plugin.getBanning().get(p).setDuration(clickedName);
						// Create inv for reasons and open it
						Inventory i = Bukkit.createInventory(null, 27,
								"Reason: " + plugin.getBanning().get(p).getTarget());
						i.setItem(10, Utils.makeItem(Material.DIAMOND_SWORD, ChatColor.RED + "Cheating"));
						i.setItem(12, Utils.makeItem(Material.EMPTY_MAP, ChatColor.RED + "Advertising"));
						i.setItem(14, Utils.makeItem(Material.TNT, ChatColor.RED + "Spamming"));
						i.setItem(16, Utils.makeItem(Material.BARRIER, ChatColor.RED + "Other"));
						p.openInventory(i);
					}
				}
			}
		}
	}

}
