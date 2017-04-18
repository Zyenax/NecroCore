package net.necrocore.main.utils;

import java.util.HashMap;

import net.necrocore.main.NecroCore;

import org.bukkit.event.Listener;

public class HashMapStorage implements Listener{
	
	@SuppressWarnings("unused")
	private NecroCore plugin;
	public HashMapStorage(NecroCore listener) {
		plugin = listener;		
	}
	
	public static HashMap<String, Integer> PlayerCount = new HashMap<String, Integer>();
}
