package net.necrocore.main.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import net.necrocore.main.NecroCore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SQLMeteors implements Listener{
	
	private static NecroCore plugin;
	public SQLMeteors(NecroCore listener) {
		SQLMeteors.plugin = listener;		
	}

    public static HashMap<UUID, Integer> meteors = new HashMap<UUID, Integer>();

    private static final String INSERT = "INSERT INTO Meteors VALUES(?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT METEORS FROM Meteors WHERE UUID=?";
    private static final String SAVE = "UPDATE Meteors SET METEORS=? WHERE UUID=?";
    
    private static void addPlayer(Player p, Integer shardAmount) {
        removePlayer(p);
        meteors.put(p.getUniqueId(), shardAmount);
    }

    public static void removePlayer(Player p) {
        meteors.remove(p.getUniqueId());
    }

    public static Integer getMeteors(Player p) {
        if (meteors.containsKey(p.getUniqueId())){
            return meteors.get(p.getUniqueId());
        }else{
        	return null;
        }
        
    }

    public static void setMeteors(Player p, Integer shardAmount) {
        if (meteors.containsKey(p.getUniqueId())){
        	meteors.remove(p.getUniqueId());
            meteors.put(p.getUniqueId(), shardAmount);
        }else{
           meteors.put(p.getUniqueId(), shardAmount);
        }
    }
    
    public static void addMeteors(Player p, Integer shardAmount) {
        if (meteors.containsKey(p.getUniqueId())){
            meteors.replace(p.getUniqueId(), getMeteors(p) + shardAmount);
        }else{
           meteors.put(p.getUniqueId(), shardAmount);
        }
    }
    
    public static void removeMeteors(Player p, Integer shardAmount) {
        if (meteors.containsKey(p.getUniqueId())){
            meteors.replace(p.getUniqueId(), getMeteors(p) - shardAmount);
        }else{
           meteors.put(p.getUniqueId(), shardAmount);
        }
    }
    
    public static void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(INSERT);
                    statement.setString(1, p.getUniqueId().toString());
                    statement.setString(2, p.getName());
                    statement.setInt(3, meteors.get(p.getUniqueId()));
                    statement.setString(4, p.getName());
                    NecroCore.mysql.update(statement);

                    statement = NecroCore.mysql.prepareStatement(SELECT);
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet result = NecroCore.mysql.query(statement);
                    if (result.next())
                        addPlayer(p, result.getInt("METEORS"));
                    result.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static void savePlayer(final Player p) {
                try {
                	if(meteors.containsKey(p.getUniqueId())){
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setInt(1, meteors.get(p.getUniqueId()));
                    statement.setString(2, p.getUniqueId().toString());
                    NecroCore.mysql.update(statement);
                    removePlayer(p);
                	}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    public static void onDisableSavePlayer() {
            	for(Player p : Bukkit.getOnlinePlayers()){
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setInt(1, meteors.get(p.getUniqueId()));
                    statement.setString(2, p.getUniqueId().toString());
                    NecroCore.mysql.update(statement);
                    removePlayer(p);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            	}
    }
}
