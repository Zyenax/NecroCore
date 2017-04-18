package net.necrocore.main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.sql.PreparedStatement;

import net.necrocore.main.Commands.AntiReloadCommand;
import net.necrocore.main.Commands.GameModeCommand;
import net.necrocore.main.Commands.MSGCommand;
import net.necrocore.main.Commands.RankCommand;
import net.necrocore.main.SQL.SQL;
import net.necrocore.main.SQL.SQLMeteors;
import net.necrocore.main.SQL.SQLNetworkLevels;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.SQL.SQLShards;
import net.necrocore.main.SQL.SQLStacker;
import net.necrocore.main.SQL.SQLVisibility;
import net.necrocore.main.handlers.RankChatHandler;
import net.necrocore.main.handlers.setTabListName;
import net.necrocore.main.utils.BungeeUtil;
import net.necrocore.main.utils.HashMapStorage;
import net.necrocore.main.utils.HoloGrams;
import net.necrocore.main.utils.Packets;
import net.necrocore.main.utils.Utils;
import net.necrocore.main.utils.WorldManager;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class NecroCore extends JavaPlugin implements Listener, PluginMessageListener{
	
	public static String GetServer;
	public static String[] serverList;
	public static SQL mysql;
	public static String name = "&8&l[&c&lNECRO&8&l]";
	private static NecroCore instance;
	//USE THIS TO DISABLE THE CORES DEFAULT CHAT FORMAT USEFUL FOR MINIGAMES
	//FOR GAMES RECOMMENDED SETTING = FALSE
	public static Boolean useCoreChat;
	
	//CHOOSE WHETHER TO USE THE CORES NAME ABOVE YOUR HEAD AND THE TAB LIST NAME PROVIDED USEFUL FOR MINIGAMES
	//FOR GAMES RECOMMENDED SETTING = FALSE
	public static Boolean useCoreTabAndTag;
	
	public void onEnable(){
		registerListeners();
		registerCommands();
		instance = this;
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
	    Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	    ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(Utils.color(name + " &aCore plugin has been enabled!"));
		connectMySQL();
		for(Player p : Bukkit.getOnlinePlayers()){
			SQLRanks.loadPlayer(p);
			SQLShards.loadPlayer(p);
			SQLMeteors.loadPlayer(p);
			SQLNetworkLevels.loadPlayer(p);
		}
	}
	
	public void onDisable(){
		SQLRanks.onDisableSavePlayer();
		SQLShards.onDisableSavePlayer();
		SQLMeteors.onDisableSavePlayer();
		SQLNetworkLevels.onDisableSavePlayer();
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(Utils.color(name + " &cCore plugin has been disabled!"));
	}
	
	public void registerListeners(){
		PluginManager manager = Bukkit.getServer().getPluginManager();
		manager.registerEvents(new BungeeUtil(this), this);
		manager.registerEvents(new HashMapStorage(this), this);
		manager.registerEvents(new HoloGrams(this), this);
		manager.registerEvents(new GameModeCommand(this), this);
		manager.registerEvents(new Packets(this), this);
		manager.registerEvents(new SQLNetworkLevels(this), this);
		manager.registerEvents(new SQLVisibility(this), this);
		manager.registerEvents(new SQLMeteors(this), this);
		manager.registerEvents(new SQLRanks(this), this);
		manager.registerEvents(new SQLShards(this), this);
		manager.registerEvents(new Utils(this), this);
		manager.registerEvents(new PlayerJoin(this), this);
		manager.registerEvents(new PlayerQuit(this), this);
		manager.registerEvents(new setTabListName(this), this);
		manager.registerEvents(new RankChatHandler(this), this);
		manager.registerEvents(new AntiReloadCommand(this), this);
		manager.registerEvents(new WorldManager(this), this);
		manager.registerEvents(new SQLStacker(this), this);
		manager.registerEvents(new MSGCommand(this), this);
		manager.registerEvents(new RankCommand(this), this);
	}
	
	public void registerCommands(){
		getCommand("gm").setExecutor(new GameModeCommand(this));
		getCommand("gamemode").setExecutor(new GameModeCommand(this));
		getCommand("msg").setExecutor(new MSGCommand(this));
		getCommand("message").setExecutor(new MSGCommand(this));
		getCommand("setrank").setExecutor(new RankCommand(this));
	}
	
	public static NecroCore getInstance(){
	     return instance;
	}

	public SQL getMySQL(){
	     return mysql;
	}
	
	public void connectMySQL() {
		// IPADDRESS, PORT, DATABASE, USERNAME, PASSWORD
	     mysql = new SQL("127.0.0.1", "3306", "endermite_584", "endermite_584", "8d09ed8619");
	     PreparedStatement ranks = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Ranks(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, RANK VARCHAR(45) NOT NULL, PRIMARY KEY(UUID))");
	     PreparedStatement shards = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Shards(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, SHARDS INT(20) NOT NULL, PRIMARY KEY(UUID))");
	     PreparedStatement meteors = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Meteors(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, METEORS INT(20) NOT NULL, PRIMARY KEY(UUID))");
	     PreparedStatement level = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Level(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, LEVEL INT(20) NOT NULL, EXPERIENCE INT(20) NOT NULL, EXPERIENCENEEDED INT(20) NOT NULL, PRIMARY KEY(UUID))");
	     PreparedStatement visibility = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Visibility(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, VISIBILITY VARCHAR(45) NOT NULL, PRIMARY KEY(UUID))");
	     PreparedStatement stacker = mysql.prepareStatement("CREATE TABLE IF NOT EXISTS Stacker(UUID varchar(36) NOT NULL, NAME VARCHAR(16) NOT NULL, STACKER VARCHAR(45) NOT NULL, PRIMARY KEY(UUID))");
	     
	     
	     //USED TO CREATE TABLES FOR INFORMATION
	     mysql.update(ranks);
	     mysql.update(shards);
	     mysql.update(meteors);
	     mysql.update(level);
	     mysql.update(visibility);
	     mysql.update(stacker);
	}
	
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    if (!channel.equals("BungeeCord")) {
	      return;
	    }
	    try{
	    	DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		    String subchannel = in.readUTF();
	    if (subchannel.equals("PlayerCount")) {
	    	String PlayerCountServer = in.readUTF();
	    	Integer playercount = in.readInt();
	    	HashMapStorage.PlayerCount.remove(PlayerCountServer);
	    	HashMapStorage.PlayerCount.put(PlayerCountServer, playercount);
        } else if (subchannel.equals("GetServers")) {
        	serverList = in.readUTF().split("\n");
        } else if (subchannel.equals("GetServer")) {
            // Example: GetServer subchannel
        	GetServer = in.readUTF();
        }
	    }catch (Exception e){
	    	e.printStackTrace();
	    }
	}
}
