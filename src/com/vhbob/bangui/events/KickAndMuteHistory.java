package com.vhbob.bangui.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import com.vhbob.bangui.BanGUI;
import com.vhbob.bangui.Utils;

import net.ess3.api.events.MuteStatusChangeEvent;

public class KickAndMuteHistory implements Listener {

	private BanGUI plugin;

	public KickAndMuteHistory(BanGUI plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Utils.logAction(e.getPlayer().getName(), "Kicked for " + e.getReason(), plugin);
	}

	@EventHandler
	public void onMute(MuteStatusChangeEvent e) {
		if (e.getValue())
			Utils.logAction(e.getAffected().getName(), "Muted", plugin);
	}

}
