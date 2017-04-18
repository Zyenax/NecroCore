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

public class SQLShards implements Listener{
	
	private static NecroCore plugin;
	public SQLShards(NecroCore listener) {
		SQLShards.plugin = listener;		
	}

    public static HashMap<UUID, Integer> shards = new HashMap<UUID, Integer>();

    private static final String INSERT = "INSERT INTO Shards VALUES(?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT SHARDS FROM Shards WHERE UUID=?";
    private static final String SAVE = "UPDATE Shards SET SHARDS=? WHERE UUID=?";
    
    private static void addPlayer(Player p, Integer shardAmount) {
        removePlayer(p);
        shards.put(p.getUniqueId(), shardAmount);
    }

    public static void removePlayer(Player p) {
        shards.remove(p.getUniqueId());
    }

    public static Integer getShards(Player p) {
        if (shards.containsKey(p.getUniqueId())){
            return shards.get(p.getUniqueId());
        }else{
        	return null;
        }
        
    }

    public static void setShards(Player p, Integer shardAmount) {
        if (shards.containsKey(p.getUniqueId())){
        	shards.remove(p.getUniqueId());
            shards.put(p.getUniqueId(), shardAmount);
        }else{
           shards.put(p.getUniqueId(), shardAmount);
        }
    }
    
    public static void addShards(Player p, Integer shardAmount) {
        if (shards.containsKey(p.getUniqueId())){
            shards.replace(p.getUniqueId(), getShards(p) + shardAmount);
        }else{
           shards.put(p.getUniqueId(), shardAmount);
        }
    }
    
    public static void removeShards(Player p, Integer shardAmount) {
        if (shards.containsKey(p.getUniqueId())){
            shards.replace(p.getUniqueId(), getShards(p) - shardAmount);
        }else{
           shards.put(p.getUniqueId(), shardAmount);
        }
    }
    
    public static void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(INSERT);
                    statement.setString(1, p.getUniqueId().toString());
                    statement.setString(2, p.getName());
                    statement.setInt(3, shards.get(p.getUniqueId()));
                    statement.setString(4, p.getName());
                    NecroCore.mysql.update(statement);

                    statement = NecroCore.mysql.prepareStatement(SELECT);
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet result = NecroCore.mysql.query(statement);
                    if (result.next())
                        addPlayer(p, result.getInt("SHARDS"));
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
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setInt(1, shards.get(p.getUniqueId()));
                    statement.setString(2, p.getUniqueId().toString());
                    NecroCore.mysql.update(statement);
                    removePlayer(p);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    public static void onDisableSavePlayer() {
            	for(Player p : Bukkit.getOnlinePlayers()){
                try {
                	if(shards.containsKey(p.getUniqueId())){
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setInt(1, shards.get(p.getUniqueId()));
                    statement.setString(2, p.getUniqueId().toString());
                    NecroCore.mysql.update(statement);
                    removePlayer(p);
                	}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            	}
    }
}
