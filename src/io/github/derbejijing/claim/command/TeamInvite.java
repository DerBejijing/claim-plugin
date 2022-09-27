package io.github.derbejijing.claim.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TeamInvite implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(!DataStorage.team_player_in_team(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                return true;
            }
            String team = DataStorage.team_get_by_player(sender.getName()).name;

            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    sender.sendMessage(ChatColor.RED + "That player is not online");
                    return true;
                }
                else {

                    TextComponent accept_msg = new TextComponent(ChatColor.GREEN + "[accept]");
					accept_msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to accept").create()));
					accept_msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teamjoinaccept " + sender.getName()));
								
					TextComponent decline_msg = new TextComponent(ChatColor.RED + "[decline]");
					decline_msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to decline").create()));
					decline_msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teamjoindecline " + sender.getName()));
								
					accept_msg.addExtra(" ");
					accept_msg.addExtra(decline_msg);

                    target.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
					target.sendMessage(ChatColor.GRAY + "You have been invited to join " + ChatColor.GOLD + team + ChatColor.GRAY + "!");
					target.sendMessage(ChatColor.GRAY + "Before you join, inform yourself using /teaminfo!");
					target.sendMessage(ChatColor.GRAY + "This request will timeout in [" + ChatColor.RED + "2 minutes" + ChatColor.GRAY + "]");
                    target.spigot().sendMessage(accept_msg);
					target.sendMessage(ChatColor.GREEN + "-----------------------------------------------");

                    sender.sendMessage(ChatColor.GOLD + "Request sent!");

                }
            }
        } 
        return true;
    }
    
}
