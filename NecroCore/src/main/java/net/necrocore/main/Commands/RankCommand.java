package net.necrocore.main.Commands;

import net.necrocore.main.NecroCore;
import net.necrocore.main.SQL.SQLRanks;
import net.necrocore.main.handlers.Rank;
import net.necrocore.main.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class RankCommand implements Listener, CommandExecutor {
	
	@SuppressWarnings("unused")
	private NecroCore plugin;

	public RankCommand(NecroCore hub) {
		this.plugin = hub;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
			if (command.getName().equalsIgnoreCase("setrank")){
				if(args.length == 0){
					String incompleteCommand = Utils.color(NecroCore.name + " &eThe command is /setrank [Player] [rankvalue]");
					String noPerms = Utils.color(NecroCore.name + " &eYou must have " + Rank.SRMOD.getColor() + Rank.SRMOD.getName() + " &eor above to use this!");
					String badSender = Utils.color(NecroCore.name + " &eYou do not have permission to send this command!");
					if(sender instanceof Player){
						Player Player = (Player)sender;
						if(SQLRanks.getRank(Player).equals(Rank.SRMOD.getName())
								|| SQLRanks.getRank(Player).equals(Rank.ADMIN.getName())
								|| SQLRanks.getRank(Player).equals(Rank.OWNER.getName())){
							Player.sendMessage(incompleteCommand);
						}else{
							Player.sendMessage(noPerms);
						}
					}else if(sender instanceof CommandSender){
						sender.sendMessage(incompleteCommand);
					}else{
						sender.sendMessage(badSender);
					}
				}
			
				if(args.length == 1){
					Player target = Bukkit.getServer().getPlayer(args[0]);
					String noRankProvided = Utils.color(NecroCore.name + " &eYou must supply a rank to set!");
					String noPerms = Utils.color(NecroCore.name + " &eYou must have " + Rank.SRMOD.getColor() + Rank.SRMOD.getName() + " &eor above to use this!");
					String invalidPlayer = Utils.color(NecroCore.name + " &eYou must supply an online Players name!");
					String badSender = Utils.color(NecroCore.name + " &eYou do not have permission to send this command!");
						if(sender instanceof Player){
							Player Player = (Player)sender;
							if(target != null){
								if(SQLRanks.getRank(Player).equals(Rank.SRMOD.getName())
										|| SQLRanks.getRank(Player).equals(Rank.ADMIN.getName())
										|| SQLRanks.getRank(Player).equals(Rank.OWNER.getName())){
									Player.sendMessage(noRankProvided);
								}else{
									Player.sendMessage(noPerms);
								}
							}else{
								Player.sendMessage(invalidPlayer);
							}
						}else if(sender instanceof CommandSender){
							if(target != null){
								sender.sendMessage(noRankProvided);
							}else{
								sender.sendMessage(invalidPlayer);
							}
						}else{
							sender.sendMessage(badSender);
						}
					}
			
				if(args.length == 2){
					String rank = args[1].toString(); 
					String kickMessage = Utils.color(NecroCore.name + "\n&cYou have been kicked by: &b" + sender.getName() + "\n&aReason: &eYour rank has been updated!\n&aInfo: &ePlease relog to see the change!\n&aChange: &eRank updated to &c" + rank.toUpperCase());
					String noPerms = Utils.color(NecroCore.name + " &eYou must have " + Rank.SRMOD.getColor() + Rank.SRMOD.getName() + " &eor above to use this!");
					String invalidPlayer = Utils.color(NecroCore.name + " &eYou must supply an online Players name!");
					String invalidRank = Utils.color(NecroCore.name + " &eYou must supply a valid rank name!");
					String badSender = Utils.color(NecroCore.name + " &eYou do not have permission to send this command!");
					String noHigherPerm = Utils.color(NecroCore.name + " &eYou are not allowed to edit this Players rank!");
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if(sender instanceof Player){
						Player Player = (Player)sender;
						if(target != null){
							if(SQLRanks.getRank(Player).equals(Rank.SRMOD.getName())){
								if(!SQLRanks.getRank(target).equals(Rank.OWNER.getName()) 
										&& !SQLRanks.getRank(target).equals(Rank.ADMIN.getName()) 
										&& !SQLRanks.getRank(target).equals(Rank.SRMOD.getName())){
									if(rank.equalsIgnoreCase(Rank.DEFAULT.getName()) 
											|| rank.equalsIgnoreCase(Rank.DONOR1.getName())
											|| rank.equalsIgnoreCase(Rank.DONOR2.getName())
											|| rank.equalsIgnoreCase(Rank.DONOR3.getName())
											|| rank.equalsIgnoreCase(Rank.BUILDER.getName())
											|| rank.equalsIgnoreCase(Rank.HELPER.getName())
											|| rank.equalsIgnoreCase(Rank.MOD.getName())){
										//UPDATE RANK THEN SAVE THEN PROVIDE KICKMESSAGE
										SQLRanks.setRank(target, rank.toUpperCase());
										if(target.isOnline()){
											target.kickPlayer(kickMessage);
										}
									}else{
										sender.sendMessage(invalidRank);
									}
								}else{
									sender.sendMessage(noHigherPerm);
								}
							}else if(SQLRanks.getRank(Player).equals(Rank.ADMIN.getName())){
								if(!SQLRanks.getRank(target).equals(Rank.OWNER.getName()) 
										&& !SQLRanks.getRank(target).equals(Rank.ADMIN.getName())){
									if(rank.equalsIgnoreCase(Rank.DEFAULT.getName()) 
											|| rank.equalsIgnoreCase(Rank.DONOR1.getName())
											|| rank.equalsIgnoreCase(Rank.DONOR2.getName())
											|| rank.equalsIgnoreCase(Rank.DONOR3.getName())
											|| rank.equalsIgnoreCase(Rank.BUILDER.getName())
											|| rank.equalsIgnoreCase(Rank.HELPER.getName())
											|| rank.equalsIgnoreCase(Rank.MOD.getName())
											|| rank.equalsIgnoreCase(Rank.SRMOD.getName())){
										//UPDATE RANK THEN SAVE THEN PROVIDE KICKMESSAGE
										SQLRanks.setRank(target, rank.toUpperCase());
										if(target.isOnline()){
											target.kickPlayer(kickMessage);
										}
									}else{
										sender.sendMessage(invalidRank);
									}
								}else{
									sender.sendMessage(noHigherPerm);
								}
							}else if(SQLRanks.getRank(Player).equals(Rank.OWNER.getName())){
								if(rank.equalsIgnoreCase(Rank.DEFAULT.getName()) 
										|| rank.equalsIgnoreCase(Rank.DONOR1.getName())
										|| rank.equalsIgnoreCase(Rank.DONOR2.getName())
										|| rank.equalsIgnoreCase(Rank.DONOR3.getName())
										|| rank.equalsIgnoreCase(Rank.BUILDER.getName())
										|| rank.equalsIgnoreCase(Rank.HELPER.getName())
										|| rank.equalsIgnoreCase(Rank.MOD.getName())
										|| rank.equalsIgnoreCase(Rank.SRMOD.getName())
										|| rank.equalsIgnoreCase(Rank.ADMIN.getName())
										|| rank.equalsIgnoreCase(Rank.OWNER.getName())){
									SQLRanks.setRank(target, rank.toUpperCase());
									if(target.isOnline()){
										target.kickPlayer(kickMessage);
									}
								}else{
									sender.sendMessage(invalidRank);
								}
							}else{
								Player.sendMessage(noPerms);
							}
						}else{
							Player.sendMessage(invalidPlayer);
						}
					}else if(sender instanceof CommandSender){
						if(target != null){
							if(rank.equalsIgnoreCase(Rank.DEFAULT.getName()) 
									|| rank.equalsIgnoreCase(Rank.DONOR1.getName())
									|| rank.equalsIgnoreCase(Rank.DONOR2.getName())
									|| rank.equalsIgnoreCase(Rank.DONOR3.getName())
									|| rank.equalsIgnoreCase(Rank.BUILDER.getName())
									|| rank.equalsIgnoreCase(Rank.HELPER.getName())
									|| rank.equalsIgnoreCase(Rank.MOD.getName())
									|| rank.equalsIgnoreCase(Rank.SRMOD.getName())
									|| rank.equalsIgnoreCase(Rank.ADMIN.getName())
									|| rank.equalsIgnoreCase(Rank.OWNER.getName())){
								SQLRanks.setRank(target, rank.toUpperCase());
								if(target.isOnline()){
									target.kickPlayer(kickMessage);
								}
							}else{
								sender.sendMessage(invalidRank);
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
