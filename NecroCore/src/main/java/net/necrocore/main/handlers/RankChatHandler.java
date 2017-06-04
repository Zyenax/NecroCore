package net.necrocore.main.handlers;

import net.necrocore.main.NecroCore;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.utils.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RankChatHandler implements Listener{

	@SuppressWarnings("unused")
	private static NecroCore plugin;

	public RankChatHandler(NecroCore hub) {
		RankChatHandler.plugin = hub;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		if(NecroCore.useCoreChat == true){
		Player p = e.getPlayer();
		e.setFormat(Utils.color(SQLRanks.getRankColor(p) + "[" + SQLRanks.getRank(p) + "]" + " " + p.getName() + " &r" + e.getMessage()));
		}
	}

}
