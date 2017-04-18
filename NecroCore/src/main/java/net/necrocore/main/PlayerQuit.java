package net.necrocore.main;

import net.necrocore.main.SQL.SQLMeteors;
import net.necrocore.main.SQL.SQLNetworkLevels;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.SQL.SQLShards;
import net.necrocore.main.SQL.SQLStacker;
import net.necrocore.main.SQL.SQLVisibility;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener{
	
	@SuppressWarnings("unused")
	private NecroCore plugin;
	public PlayerQuit(NecroCore listener) {
		this.plugin = listener;		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		if(SQLRanks.rank.containsKey(e.getPlayer().getUniqueId())){
			SQLRanks.savePlayer(e.getPlayer());
		}
		if(SQLVisibility.visibility.containsKey(e.getPlayer().getUniqueId())){
			SQLVisibility.savePlayer(e.getPlayer());
		}
		if(SQLStacker.stacker.containsKey(e.getPlayer().getUniqueId())){
			SQLStacker.savePlayer(e.getPlayer());
		}
		if(SQLShards.shards.containsKey(e.getPlayer().getUniqueId())){
			SQLShards.savePlayer(e.getPlayer());
		}
		if(SQLMeteors.meteors.containsKey(e.getPlayer().getUniqueId())){
			SQLMeteors.savePlayer(e.getPlayer());
		}
		if(SQLNetworkLevels.levels.containsKey(e.getPlayer().getUniqueId())){
			SQLNetworkLevels.savePlayer(e.getPlayer());
		}
	}
}
