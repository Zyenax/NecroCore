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

public class SQLVisibility implements Listener{
	
	private static NecroCore plugin;
	public SQLVisibility(NecroCore listener) {
		SQLVisibility.plugin = listener;		
	}

    public static HashMap<UUID, String> visibility = new HashMap<UUID, String>();

    private static final String INSERT = "INSERT INTO Visibility VALUES(?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT VISIBILITY FROM Visibility WHERE UUID=?";
    private static final String SAVE = "UPDATE Visibility SET VISIBILITY=? WHERE UUID=?";
    
    private static void addPlayer(Player p, String value) {
        removePlayer(p);
        visibility.put(p.getUniqueId(), value);
    }

    public static void removePlayer(Player p) {
        visibility.remove(p.getUniqueId());
    }

    public static String getVisibility(Player target) {
        if (visibility.containsKey(target.getUniqueId())){
            return visibility.get(target.getUniqueId());
        }else{
        	return null;
        }
        
    }

    public static void setVisibility(Player target, String value) {
        if (visibility.containsKey(target.getUniqueId())){
        	visibility.remove(target.getUniqueId());
            visibility.put(target.getUniqueId(), value);
        }else{
           visibility.put(target.getUniqueId(), value);
        }
    }
    
    public static void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(INSERT);
                    statement.setString(1, p.getUniqueId().toString());
                    statement.setString(2, p.getName());
                    statement.setString(3, visibility.get(p.getUniqueId()));
                    statement.setString(4, p.getName());
                    NecroCore.mysql.update(statement);

                    statement = NecroCore.mysql.prepareStatement(SELECT);
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet result = NecroCore.mysql.query(statement);
                    if (result.next())
                        addPlayer(p, result.getString("VISIBILITY"));
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
                    statement.setString(1, visibility.get(p.getUniqueId()));
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
                	if(visibility.containsKey(p.getUniqueId())){
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setString(1, visibility.get(p.getUniqueId()));
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
