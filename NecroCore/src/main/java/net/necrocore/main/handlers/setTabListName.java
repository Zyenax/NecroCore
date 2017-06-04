package net.necrocore.main.handlers;

import net.necrocore.main.NecroCore;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.utils.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class setTabListName implements Listener{
	
	@SuppressWarnings("unused")
	private NecroCore plugin;
	public setTabListName(NecroCore listener) {
		this.plugin = listener;		
	}
	
	public static void setName(Player p){
		if(NecroCore.useCoreTabAndTag == true){
			p.setPlayerListName(Utils.color(SQLRanks.getRankColor(p) + p.getName()));
			Utils.setTag(p, Utils.color(SQLRanks.getRankColor(p) + "[" + SQLRanks.getRank(p) + "]" + " "));
		}
	}
}
