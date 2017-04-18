package net.necrocore.main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import net.necrocore.main.NecroCore;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class WorldManager implements Listener{
	
	@SuppressWarnings("unused")
	private NecroCore plugin;
	public WorldManager(NecroCore listener) {
		this.plugin = listener;		
	}	
	
	public static Player player;
	
	//worldManager.unloadWorld(Bukkit.getWorld("WorldName"));
	public static void unloadWorld(World world) {
		if(!(world.equals(null))){
			Bukkit.getServer().unloadWorld(world, true);
		}else{
			player.sendMessage("No world found");
		}
	}
	
	public static void createWorld(String world){
		if(!(world.equals(null))){
			Bukkit.createWorld(new WorldCreator (world).type(WorldType.FLAT));
		}
	}
	
	//USED BY DOING worldManager.deleteWorld(Bukkit.getWorld("WorldName").getWorldFolder());
	public static boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	
	//USE BY DOING worldManager.copyWorld(Bukkit.getWorld("world").getWorldFolder(), Bukkit.getWorld("world").getWorldFolder());
	public static void copyWorld(File source, File target){
	    try {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) {
	            if(source.isDirectory()) {
	                if(!target.exists())
	                target.mkdirs();
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                    copyWorld(srcFile, destFile);
	                }
	            } else {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	 
	    }
	}

}
