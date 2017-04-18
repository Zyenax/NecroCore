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

public class SQLStacker implements Listener{
	
	private static NecroCore plugin;
	public SQLStacker(NecroCore listener) {
		SQLStacker.plugin = listener;		
	}

    public static HashMap<UUID, String> stacker = new HashMap<UUID, String>();

    private static final String INSERT = "INSERT INTO Stacker VALUES(?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT STACKER FROM Stacker WHERE UUID=?";
    private static final String SAVE = "UPDATE Stacker SET STACKER=? WHERE UUID=?";
    
    private static void addPlayer(Player p, String value) {
        removePlayer(p);
        stacker.put(p.getUniqueId(), value);
    }

    public static void removePlayer(Player p) {
        stacker.remove(p.getUniqueId());
    }

    public static String getStacker(Player target) {
        if (stacker.containsKey(target.getUniqueId())){
            return stacker.get(target.getUniqueId());
        }else{
        	return null;
        }
        
    }

    public static void setStacker(Player target, String value) {
        if (stacker.containsKey(target.getUniqueId())){
        	stacker.remove(target.getUniqueId());
            stacker.put(target.getUniqueId(), value);
        }else{
           stacker.put(target.getUniqueId(), value);
        }
    }
    
    public static void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(INSERT);
                    statement.setString(1, p.getUniqueId().toString());
                    statement.setString(2, p.getName());
                    statement.setString(3, stacker.get(p.getUniqueId()));
                    statement.setString(4, p.getName());
                    NecroCore.mysql.update(statement);

                    statement = NecroCore.mysql.prepareStatement(SELECT);
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet result = NecroCore.mysql.query(statement);
                    if (result.next())
                        addPlayer(p, result.getString("STACKER"));
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
                    statement.setString(1, stacker.get(p.getUniqueId()));
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
                	if(stacker.containsKey(p.getUniqueId())){
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setString(1, stacker.get(p.getUniqueId()));
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
