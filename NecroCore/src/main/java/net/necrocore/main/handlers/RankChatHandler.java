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
		if(SQLRanks.getRank(p).equals(Rank.OWNER.getName())){
			e.setFormat(Utils.color(Rank.OWNER.getColor() + "[" + Rank.OWNER.getName() + "]" + " " + p.getName() + " &r" + e.getMessage()));
		}else if(SQLRanks.getRank(p).equals(Rank.ADMIN.getName())){
			e.setFormat(Utils.color(Rank.ADMIN.getColor() + "[" + Rank.ADMIN.getName() + "]" + " " + p.getName() + " &r" + e.getMessage()));
		}else if(SQLRanks.getRank(p).equals(Rank.SRMOD.getName())){
			e.setFormat(Utils.color(Rank.SRMOD.getColor() + "[" + Rank.SRMOD.getName() + "]" + " " + p.getName() + " &r" + e.getMessage()));
		}else if(SQLRanks.getRank(p).equals(Rank.MOD.getName())){
			e.setFormat(Utils.color(Rank.MOD.getColor() + "[" + Rank.MOD.getName() + "]" + " " + p.getName() + " &r" + e.getMessage()));
		}else if(SQLRanks.getRank(p).equals(Rank.HELPER.getName())){
			e.setFormat(Utils.color(Rank.HELPER.getColor() + "[" + Rank.HELPER.getName() + "]" + " " + p.getName() + " &r") + e.getMessage());
		}else if(SQLRanks.getRank(p).equals(Rank.BUILDER.getName())){
			e.setFormat(Utils.color(Rank.BUILDER.getColor() + "[" + Rank.BUILDER.getName() + "]" + " " + p.getName() + " &r") + e.getMessage());
		}else if(SQLRanks.getRank(p).equals(Rank.DONOR3.getName())){
			e.setFormat(Utils.color(Rank.DONOR3.getColor() + "[" + Rank.DONOR3.getName() + "]" + " " + p.getName() + " &r") + e.getMessage());
		}else if(SQLRanks.getRank(p).equals(Rank.DONOR2.getName())){
			e.setFormat(Utils.color(Rank.DONOR2.getColor() + "[" + Rank.DONOR2.getName() + "]" + " " + p.getName() + " &r") + e.getMessage());
		}else if(SQLRanks.getRank(p).equals(Rank.DONOR1.getName())){
			e.setFormat(Utils.color(Rank.DONOR1.getColor() + "[" + Rank.DONOR1.getName() + "]" + " " + p.getName() + " &r") + e.getMessage());
		}else if(SQLRanks.getRank(p).equals(Rank.DEFAULT.getName())){
			e.setFormat(Utils.color(Rank.DEFAULT.getColor() + p.getName() + " &r") + e.getMessage());
		}
		}
	}

}
