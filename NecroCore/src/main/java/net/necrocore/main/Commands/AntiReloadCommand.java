package net.necrocore.main.Commands;

import net.necrocore.main.NecroCore;
import net.necrocore.main.utils.Utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AntiReloadCommand implements Listener {
	
	@SuppressWarnings("unused")
	private NecroCore plugin;

	public AntiReloadCommand(NecroCore hub) {
		this.plugin = hub;
	}
	
	@EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
            if (e.getMessage().startsWith("/reload")) {
                    e.getPlayer().sendMessage(Utils.color(NecroCore.name + " &eSorry this command is blocked globally as it breaks things!"));
                    e.setCancelled(true);
            }
    }

}
