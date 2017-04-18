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

public class SQLNetworkLevels implements Listener{
	
	private static NecroCore plugin;
	public SQLNetworkLevels(NecroCore listener) {
		SQLNetworkLevels.plugin = listener;		
	}

	public static HashMap<UUID, Integer> levels = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Integer> experience = new HashMap<UUID, Integer>();
	private static HashMap<UUID, Integer> xpneeded = new HashMap<UUID, Integer>();

    private static final String INSERT = "INSERT INTO Level VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE NAME=?";
    private static final String SELECT = "SELECT * FROM Level WHERE UUID=?";
    private static final String SAVE = "UPDATE Level SET LEVEL=?, EXPERIENCE=?, EXPERIENCENEEDED=? WHERE UUID=?";
    
    private static void addPlayer(Player p, Integer levelAmount, Integer xptheyhave, Integer experienceNeeded) {
        removePlayer(p);
        levels.put(p.getUniqueId(), levelAmount);
        experience.put(p.getUniqueId(), xptheyhave);
        xpneeded.put(p.getUniqueId(), experienceNeeded);
    }

    public static void removePlayer(Player p) {
        levels.remove(p.getUniqueId());
        experience.remove(p.getUniqueId());
        xpneeded.remove(p.getUniqueId());
    }

    public static Integer getLevel(Player p) {
        if (levels.containsKey(p.getUniqueId())){
            return levels.get(p.getUniqueId());
        }else{
        	return null;
        }
        
    }
    
    public static Integer getXP(Player p) {
        if (experience.containsKey(p.getUniqueId())){
            return experience.get(p.getUniqueId());
        }else{
        	return null;
        }
        
    }
    
    public static float getXPProgress(Player p) {
    	float percent = ((float)getXP(p)/(float)getXPNeeded(p)) * 100;
    	return (int)percent;
    }
    
    public static Integer getXPNeeded(Player p) {
        if (xpneeded.containsKey(p.getUniqueId())){
            return xpneeded.get(p.getUniqueId());
        }else{
        	return null;
        }
        
    }

    public static void setLevel(Player p, Integer levelAmount) {
        if (levels.containsKey(p.getUniqueId())){
        	if(getLevel(p) + levelAmount > 100){
        		levels.remove(p.getUniqueId());
                levels.put(p.getUniqueId(), levelAmount);
        		setXP(p, 0);
        		setXPNeeded(p, getXPNeededForLevel(getLevel(p)));
        	}else{
        	levels.remove(p.getUniqueId());
            levels.put(p.getUniqueId(), levelAmount);
            setXP(p, 0);
    		setXPNeeded(p, getXPNeededForLevel(getLevel(p)));
        	}
        }else{
           levels.put(p.getUniqueId(), levelAmount);
        }
    }
    
    public static void setXP(Player p, Integer xpamount) {
        if (experience.containsKey(p.getUniqueId())){
        	if(getXP(p) + xpamount > getXPNeeded(p)){
        		addLevel(p, getLevel(p) + 1);
        		setXP(p, 0);
        	}else{
        	experience.remove(p.getUniqueId());
        	experience.put(p.getUniqueId(), xpamount);
        	}
        }else{
        	experience.put(p.getUniqueId(), xpamount);
        }
    }
    
    public static void setXPNeeded(Player p, Integer xpneededAmount) {
        if (xpneeded.containsKey(p.getUniqueId())){
        	xpneeded.remove(p.getUniqueId());
        	xpneeded.put(p.getUniqueId(), xpneededAmount);
        }else{
        	xpneeded.put(p.getUniqueId(), xpneededAmount);
        }
    }
    
    public static void addLevel(Player p, Integer levelAmount) {
        if (levels.containsKey(p.getUniqueId())){
        	if(getLevel(p) + levelAmount > 100){
        		setLevel(p, 100);
        		setXP(p, 5000);
        		setXPNeeded(p, getXPNeededForLevel(getLevel(p)));
        	}else{
            levels.replace(p.getUniqueId(), getLevel(p) + levelAmount);
            setXP(p, 0);
    		setXPNeeded(p, getXPNeededForLevel(getLevel(p)));
        	}
        }else{
           levels.put(p.getUniqueId(), levelAmount);
        }
    }
    
    public static void addXP(Player p, Integer xpAmount) {
        if (experience.containsKey(p.getUniqueId())){
        	if(getXP(p) + xpAmount > getXPNeeded(p)){
        		addLevel(p, 1);
        		setXP(p, 0);
        	}else{
            experience.replace(p.getUniqueId(), getXP(p) + xpAmount);
        	}
        }else{
           experience.put(p.getUniqueId(), xpAmount);
        }
    }
    
    public static void addXPNeeded(Player p, Integer xpAmount) {
        if (xpneeded.containsKey(p.getUniqueId())){
            xpneeded.replace(p.getUniqueId(), getXPNeeded(p) + xpAmount);
        }else{
           xpneeded.put(p.getUniqueId(), xpAmount);
        }
    }
    
    public static void removeLevel(Player p, Integer levelAmount) {
        if (levels.containsKey(p.getUniqueId())){
        	if(getLevel(p) - levelAmount <= 1){
        		setLevel(p, 1);
        		setXP(p, 0);
        		setXPNeeded(p, getXPNeededForLevel(getLevel(p)));
        	}else{
            levels.replace(p.getUniqueId(), getLevel(p) - levelAmount);
            setXP(p, 0);
    		setXPNeeded(p, getXPNeededForLevel(getLevel(p)));
        	}
        }else{
           levels.put(p.getUniqueId(), levelAmount);
        }
    }
    
    public static void removeXP(Player p, Integer xpAmount) {
        if (experience.containsKey(p.getUniqueId())){
        	if(getXP(p) - xpAmount < 0){
        		removeLevel(p, 1);
        		setXP(p, 0);
        	}else{
            experience.replace(p.getUniqueId(), getXP(p) - xpAmount);
        	}
            
        }else{
           experience.put(p.getUniqueId(), xpAmount);
        }
    }
    
    public static void removeXPNeeded(Player p, Integer xpAmount) {
        if (xpneeded.containsKey(p.getUniqueId())){
            xpneeded.replace(p.getUniqueId(), getXPNeeded(p) - xpAmount);
            
        }else{
           xpneeded.put(p.getUniqueId(), xpAmount);
        }
    }
    
    
    public static int getXPNeededForLevel(Integer levelToGetXPFor){
		return (int)levelToGetXPFor * 100 / 2;
    }
    
    
    
    
    
    
    public static void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            public void run() {
                try {
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(INSERT);
                    statement.setString(1, p.getUniqueId().toString());
                    statement.setString(2, p.getName());
                    statement.setInt(3, levels.get(p.getUniqueId()));
                    statement.setInt(4, experience.get(p.getUniqueId()));
                    statement.setInt(5, xpneeded.get(p.getUniqueId()));
                    statement.setString(6, p.getName());
                    NecroCore.mysql.update(statement);

                    statement = NecroCore.mysql.prepareStatement(SELECT);
                    statement.setString(1, p.getUniqueId().toString());
                    ResultSet result = NecroCore.mysql.query(statement);
                    if (result.next())
                        addPlayer(p, result.getInt("LEVEL"), result.getInt("EXPERIENCE"), result.getInt("EXPERIENCENEEDED"));
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
                    statement.setInt(1, levels.get(p.getUniqueId()));
                    statement.setInt(2, experience.get(p.getUniqueId()));
                    statement.setInt(3, xpneeded.get(p.getUniqueId()));
                    statement.setString(4, p.getUniqueId().toString());
                    NecroCore.mysql.update(statement);
                    removePlayer(p);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }
    
    public static void onDisableSavePlayer() {
            	for(Player p : Bukkit.getOnlinePlayers()){
                try {
                	if(levels.containsKey(p.getUniqueId()) && (experience.containsKey(p.getUniqueId())) && (xpneeded.containsKey(p.getUniqueId()))){
                    PreparedStatement statement = NecroCore.mysql.prepareStatement(SAVE);
                    statement.setInt(1, levels.get(p.getUniqueId()));
                    statement.setInt(2, experience.get(p.getUniqueId()));
                    statement.setInt(3, xpneeded.get(p.getUniqueId()));
                    statement.setString(4, p.getUniqueId().toString());
                    NecroCore.mysql.update(statement);
                    removePlayer(p);
                	}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            	}
    }
}
