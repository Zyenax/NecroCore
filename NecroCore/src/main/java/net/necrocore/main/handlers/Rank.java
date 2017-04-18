package net.necrocore.main.handlers;

import net.necrocore.main.utils.Utils;

public enum Rank {
	
	OWNER("OWNER", Utils.color("&c")),
	ADMIN("ADMIN", Utils.color("&c")),
	SRMOD("SRMOD", Utils.color("&6")),
	MOD("MOD", Utils.color("&e")),
	HELPER("HELPER", Utils.color("&9")),
	BUILDER("BUILDER", Utils.color("&b")),
	DONOR3("TITAN", Utils.color("&c")),
	DONOR2("LEGEND", Utils.color("&6")),
	DONOR1("KNIGHT", Utils.color("&3")),
	DEFAULT("DEFAULT", Utils.color("&7"));
	
	private String RankName;
	private String RankColor;
	
	private Rank(String RankName, String RankColor){
		this.RankName = RankName;
		this.RankColor = RankColor;
	}
	
	public String getName(){
		return RankName;
	}
	
	public String getColor(){
		return RankColor;
	}
	
}
