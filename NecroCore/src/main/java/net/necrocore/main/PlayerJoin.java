package net.necrocore.main;

import net.necrocore.main.SQL.SQLMeteors;
import net.necrocore.main.SQL.SQLNetworkLevels;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.SQL.SQLShards;
import net.necrocore.main.SQL.SQLStacker;
import net.necrocore.main.SQL.SQLVisibility;
import net.necrocore.main.handlers.Rank;
import net.necrocore.main.handlers.setTabListName;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener{
	
	private NecroCore plugin;
	public PlayerJoin(NecroCore listener) {
		this.plugin = listener;		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = (Player) e.getPlayer();
		if(SQLRanks.getRank(p) == null){
			SQLRanks.setRank(p, Rank.DEFAULT.getName());
		}
		SQLRanks.loadPlayer(p);
		if(SQLVisibility.getVisibility(p) == null){
			SQLVisibility.setVisibility(p, "true");
		}
		SQLVisibility.loadPlayer(p);
		if(SQLStacker.getStacker(p) == null){
			SQLStacker.setStacker(p, "true");
		}
		SQLStacker.loadPlayer(p);
		if(SQLShards.getShards(p) == null){
			SQLShards.setShards(p, 0);
		}
		SQLShards.loadPlayer(p);
		
		if(SQLMeteors.getMeteors(p) == null){
			SQLMeteors.setMeteors(p, 0);
		}
		SQLMeteors.loadPlayer(p);
		
		if(SQLNetworkLevels.getLevel(p) == null){
			SQLNetworkLevels.setLevel(p, 1);
		}
		if(SQLNetworkLevels.getXP(p) == null){
			SQLNetworkLevels.setXP(p, 0);
		}
		if(SQLNetworkLevels.getXPNeeded(p) == null){
			SQLNetworkLevels.setXPNeeded(p, SQLNetworkLevels.getXPNeededForLevel(SQLNetworkLevels.getLevel(p)));
		}
		SQLNetworkLevels.loadPlayer(p);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
    		public void run() {
    			setTabListName.setName(p);
    		}
    	}, 3);
	}
}
