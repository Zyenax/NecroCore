package net.necrocore.main.Commands;

import net.necrocore.main.NecroCore;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.handlers.Rank;
import net.necrocore.main.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GameModeCommand implements Listener, CommandExecutor {
	
	@SuppressWarnings("unused")
	private NecroCore plugin;

	public GameModeCommand(NecroCore hub) {
		this.plugin = hub;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
			if (command.getName().equalsIgnoreCase("gamemode") || command.getName().equalsIgnoreCase("gm")){
				if(args.length == 0){
					String incompleteCommand = Utils.color(NecroCore.name + " &cThe command is /gamemode [playername] [gamemode]");
					String noPerms = Utils.color(NecroCore.name + " &cYou must have " + Rank.MOD.getColor() + Rank.MOD.getName() + " &cor above to use this!");
					String badSender = Utils.color(NecroCore.name + " &cYou do not have permission to send this command!");
					if(sender instanceof Player){
						Player player = (Player)sender;
						if(SQLRanks.getRank(player).equals(Rank.MOD.getName())
								|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())
								|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
								|| SQLRanks.getRank(player).equals(Rank.OWNER.getName())){
							player.sendMessage(incompleteCommand);
						}else{
							player.sendMessage(noPerms);
						}
					}else if(sender instanceof ConsoleCommandSender){
						sender.sendMessage(incompleteCommand);
					}else{
						sender.sendMessage(badSender);
					}
				}
			
				if(args.length == 1){
					Player target = Bukkit.getServer().getPlayer(args[0]);
					String noRankProvided = Utils.color(NecroCore.name + " &cYou must supply a gamemode to set!");
					String noPerms = Utils.color(NecroCore.name + " &cYou must have " + Rank.MOD.getColor() + Rank.MOD.getName() + " &cor above to use this!");
					String invalidPlayer = Utils.color(NecroCore.name + " &cYou must supply an online players name or a valid gamemode!");
					String badSender = Utils.color(NecroCore.name + " &cYou do not have permission to send this command!");
						if(sender instanceof Player){
							Player player = (Player)sender;
							if(args[0].equalsIgnoreCase("reset")){
								if(SQLRanks.getRank(player).equals(Rank.OWNER.getName())
										|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())){
									for(Player p : Bukkit.getOnlinePlayers()){
										if(p.isFlying()){
											if(!SQLRanks.getRank(p).equals(Rank.OWNER.getName()) 
													&& !SQLRanks.getRank(p).equals(Rank.ADMIN.getName())){
												p.setGameMode(GameMode.ADVENTURE);
												p.setAllowFlight(false);
												p.setFlying(false);
												p.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas reset everyones gamemode to &cAdventure"));
											}
										}else{
											p.setGameMode(GameMode.ADVENTURE);
											p.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas reset everyones gamemode to &cAdventure"));
										}
									}
								}else{
									player.sendMessage(Utils.color(NecroCore.name + " &cYou must have " + Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " &cor above to use this!"));
								}
							}else if(args[0].equalsIgnoreCase(GameMode.ADVENTURE.name()) || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")){
								if(SQLRanks.getRank(player).equals(Rank.OWNER.getName())
										|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())
										|| SQLRanks.getRank(player).equals(Rank.MOD.getName())){
										player.setGameMode(GameMode.ADVENTURE);
										player.sendMessage(Utils.color(NecroCore.name + " &eYour gamemode has been set to &cAdventure"));
								}else{
									player.sendMessage(Utils.color(NecroCore.name + " &cYou must have " + Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " &cor above to use this!"));
								}
							}else if(args[0].equalsIgnoreCase(GameMode.CREATIVE.name()) || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")){
								if(SQLRanks.getRank(player).equals(Rank.OWNER.getName())
										|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())
										|| SQLRanks.getRank(player).equals(Rank.MOD.getName())){
										player.setGameMode(GameMode.CREATIVE);
										player.sendMessage(Utils.color(NecroCore.name + " &eYour gamemode has been set to &cCreative"));
								}else{
									player.sendMessage(Utils.color(NecroCore.name + " &cYou must have " + Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " &cor above to use this!"));
								}
							}else if(args[0].equalsIgnoreCase(GameMode.SPECTATOR.name()) || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")){
								if(SQLRanks.getRank(player).equals(Rank.OWNER.getName())
										|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())
										|| SQLRanks.getRank(player).equals(Rank.MOD.getName())){
										player.setGameMode(GameMode.SPECTATOR);
										player.sendMessage(Utils.color(NecroCore.name + " &eYour gamemode has been set to &cSpectator"));
								}else{
									player.sendMessage(Utils.color(NecroCore.name + " &cYou must have " + Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " &cor above to use this!"));
								}
							}else if(args[0].equalsIgnoreCase(GameMode.SURVIVAL.name()) || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")){
								if(SQLRanks.getRank(player).equals(Rank.OWNER.getName())
										|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())
										|| SQLRanks.getRank(player).equals(Rank.MOD.getName())){
										player.setGameMode(GameMode.SURVIVAL);
										player.sendMessage(Utils.color(NecroCore.name + " &eYour gamemode has been set to &cSurvival"));
								}else{
									player.sendMessage(Utils.color(NecroCore.name + " &cYou must have " + Rank.ADMIN.getColor() + Rank.ADMIN.getName() + " &cor above to use this!"));
								}
							}else if(target != null){
								if(SQLRanks.getRank(player).equals(Rank.MOD.getName())
										|| SQLRanks.getRank(player).equals(Rank.SRMOD.getName())
										|| SQLRanks.getRank(player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(player).equals(Rank.OWNER.getName())){
									player.sendMessage(noRankProvided);
								}else{
									player.sendMessage(noPerms);
								}
							}else{
								player.sendMessage(invalidPlayer);
							}
						}else if(sender instanceof ConsoleCommandSender){
							if(args[0].equalsIgnoreCase("reset")){
								for(Player p : Bukkit.getOnlinePlayers()){
									if(!SQLRanks.getRank(p).equals(Rank.OWNER.getName()) 
											&& !SQLRanks.getRank(p).equals(Rank.ADMIN.getName())){
										p.setGameMode(GameMode.ADVENTURE);
										p.setAllowFlight(false);
										p.setFlying(false);
										p.sendMessage(Utils.color(NecroCore.name + " &c" + sender.getName() + " &ehas reset everyones gamemode to &cAdventure"));
									}	
								}
							}else if(target != null){
								sender.sendMessage(noRankProvided);
							}else{
								sender.sendMessage(invalidPlayer);
							}
						}else{
							sender.sendMessage(badSender);
						}
					}
			
				if(args.length == 2){
					String gamemode = args[1].toString(); 
					String noPerms = Utils.color(NecroCore.name + " &cYou must have " + Rank.MOD.getColor() + Rank.MOD.getName() + " &cor above to use this!");
					String invalidPlayer = Utils.color(NecroCore.name + " &cYou must supply an online players name!");
					String invalidGamemode = Utils.color(NecroCore.name + " &cYou must supply a valid gamemode!");
					String badSender = Utils.color(NecroCore.name + " &cYou do not have permission to send this command!");
					String noHigherPerm = Utils.color(NecroCore.name + " &cYou are not allowed to edit this players gamemode!");
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(sender instanceof Player){
						Player player = (Player)sender;
						if(target != null){
							if(SQLRanks.getRank(player).equals(Rank.SRMOD.getName())){
								if(!SQLRanks.getRank(target).equals(Rank.OWNER.getName()) 
										&& !SQLRanks.getRank(target).equals(Rank.ADMIN.getName())){
									if(gamemode.equalsIgnoreCase(GameMode.ADVENTURE.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.CREATIVE.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.SURVIVAL.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.SPECTATOR.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else{
										sender.sendMessage(invalidGamemode);
									}
								}else{
									sender.sendMessage(noHigherPerm);
								}
							}else if(SQLRanks.getRank(player).equals(Rank.ADMIN.getName())){
								if(!SQLRanks.getRank(target).equals(Rank.OWNER.getName())){
									if(gamemode.equalsIgnoreCase(GameMode.ADVENTURE.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.CREATIVE.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.SURVIVAL.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.SPECTATOR.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else{
										sender.sendMessage(invalidGamemode);
									}
								}else{
									sender.sendMessage(noHigherPerm);
								}
							}else if(SQLRanks.getRank(player).equals(Rank.OWNER.getName())){
									if(gamemode.equalsIgnoreCase(GameMode.ADVENTURE.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.CREATIVE.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.SURVIVAL.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else if(gamemode.equalsIgnoreCase(GameMode.SPECTATOR.name())){
										//UPDATE GAMEMODE
										target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
										target.sendMessage(Utils.color(NecroCore.name + " &c" + player.getName() + " &ehas set your gamemode to &c" + gamemode));
									}else{
										sender.sendMessage(invalidGamemode);
									}
							}else{
								player.sendMessage(noPerms);
							}
						}else{
							player.sendMessage(invalidPlayer);
						}
					}else if(sender instanceof ConsoleCommandSender){
						if(target != null){
							if(gamemode.equalsIgnoreCase(GameMode.ADVENTURE.name())){
								//UPDATE GAMEMODE
								target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
								target.sendMessage(Utils.color(NecroCore.name + " &c" + sender.getName() + " &ehas set your gamemode to &c" + gamemode));
							}else if(gamemode.equalsIgnoreCase(GameMode.CREATIVE.name())){
								//UPDATE GAMEMODE
								target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
								target.sendMessage(Utils.color(NecroCore.name + " &c" + sender.getName() + " &ehas set your gamemode to &c" + gamemode));
							}else if(gamemode.equalsIgnoreCase(GameMode.SURVIVAL.name())){
								//UPDATE GAMEMODE
								target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
								target.sendMessage(Utils.color(NecroCore.name + " &c" + sender.getName() + " &ehas set your gamemode to &c" + gamemode));
							}else if(gamemode.equalsIgnoreCase(GameMode.SPECTATOR.name())){
								//UPDATE GAMEMODE
								target.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
								target.sendMessage(Utils.color(NecroCore.name + " &c" + sender.getName() + " &ehas set your gamemode to &c" + gamemode));
							}else{
								sender.sendMessage(invalidGamemode);
							}
						}else{
							sender.sendMessage(invalidPlayer);
						}
					}else{
						sender.sendMessage(badSender);
					}
				}
			}	
			return true;
		}
}
