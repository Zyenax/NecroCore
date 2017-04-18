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
		if(SQLRanks.getRank(p).equals(Rank.OWNER.getName())){
			p.setPlayerListName(Utils.color(Rank.OWNER.getColor()+ p.getName()));
			Utils.setTag(p, Utils.color(Rank.OWNER.getColor() + "[" + Rank.OWNER.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.ADMIN.getName())){
			p.setPlayerListName(Utils.color(Rank.ADMIN.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.ADMIN.getColor() + "[" + Rank.ADMIN.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.SRMOD.getName())){
			p.setPlayerListName(Utils.color(Rank.SRMOD.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.SRMOD.getColor() + "[" + Rank.SRMOD.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.MOD.getName())){
			p.setPlayerListName(Utils.color(Rank.MOD.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.MOD.getColor() + "[" + Rank.MOD.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.HELPER.getName())){
			p.setPlayerListName(Utils.color(Rank.HELPER.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.HELPER.getColor() + "[" + Rank.HELPER.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.BUILDER.getName())){
			p.setPlayerListName(Utils.color(Rank.BUILDER.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.BUILDER.getColor() + "[" + Rank.BUILDER.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.DONOR3.getName())){
			p.setPlayerListName(Utils.color(Rank.DONOR3.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.DONOR3.getColor() + "[" + Rank.DONOR3.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.DONOR2.getName())){
			p.setPlayerListName(Utils.color(Rank.DONOR2.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.DONOR2.getColor() + "[" + Rank.DONOR2.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.DONOR1.getName())){
			p.setPlayerListName(Utils.color(Rank.DONOR1.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.DONOR1.getColor() + "[" + Rank.DONOR1.getName() + "]" + " "));
		}else if(SQLRanks.getRank(p).equals(Rank.DEFAULT.getName())){
			p.setPlayerListName(Utils.color(Rank.DEFAULT.getColor() + p.getName()));
			Utils.setTag(p, Utils.color(Rank.DEFAULT.getColor()));
		}
		}
	}
}
