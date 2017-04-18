package net.necrocore.main.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import net.necrocore.main.NecroCore;
import net.necrocore.main.handlers.Rank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SQLRanks implements Listener{
	
	private static NecroCore plugin;
	public SQLRanks(NecroCore listener) {
		SQLRanks.plugin = listener;		
	}

    public static HashMap<UUID, String> rank = new HashMap<UUID, String>();

    private static final String INSERT = "INSERT INTO Ranks VALUES(?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT RANK FROM Ranks WHERE UUID=?";
    private static final String SAVE = "UPDATE Ranks SET RANK=? WHERE UUID=?";
    
    private static void addPlayer(Player p, String rankName) {
        removePlayer(p);
        rank.put(p.getUniqueId(), rankName);
    }

    public static void removePlayer(Player p) {
        rank.remove(p.getUniqueId());
    }

    public static String getRank(Player target) {
        if (rank.containsKey(target.getUniqueId())){
            return rank.get(target.getUniqueId());
        }else{
        	return null;
        }
        
    }
    
    public static String getRankColor(Player target) {
    	if(getRank(target).equalsIgnoreCase(Rank.OWNER.getName())){
    		return Rank.OWNER.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.ADMIN.getName())){
    		return Rank.ADMIN.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.SRMOD.getName())){
    		return Rank.SRMOD.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.MOD.getName())){
    		return Rank.MOD.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.HELPER.getName())){
    		return Rank.HELPER.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.BUILDER.getName())){
    		return Rank.BUILDER.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.DONOR3.getName())){
    		return Rank.DONOR3.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.DONOR2.getName())){
    		return Rank.DONOR2.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.DONOR1.getName())){
    		return Rank.DONOR1.getColor();
    	}else if(getRank(target).equalsIgnoreCase(Rank.DEFAULT.getName())){
    		return Rank.DEFAULT.getColor();
    	}
		return null;
    }

    public static void setRank(Player target, String rankname) {
        if (rank.containsKey(target.getUniqueId())){
        	rank.remove(target.getUniqueId());
            rank.put(target.getUniqueId(), rankname);
        }else{
           rank.put(target.getUniqueId(), rankname);
        }
    }
    
    public static void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(INSERT);
                    statement.setString(1, p.getUniqueId().toString());
                    statement.setString(2, p.getName());
                    statement.setString(3, rank.get(p.getUniqueId()));
                    statement.setString(4, p.getName());
                    NecroCore.mysql.update(statement);

                    statement = NecroCore.mysql.prepareStatement(SELECT);
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet result = NecroCore.mysql.query(statement);
                    if (result.next())
                        addPlayer(p, result.getString("RANK"));
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
                    statement.setString(1, rank.get(p.getUniqueId()));
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
                	if(rank.containsKey(p.getUniqueId())){
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setString(1, rank.get(p.getUniqueId()));
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
