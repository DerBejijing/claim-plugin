package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import net.md_5.bungee.api.ChatColor;

public class TeamInviteAccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 1) {
                String playerFromName = args[0];
                
                if(!DataStorage.request_exists(playerFromName, sender.getName())) {
                    sender.sendMessage(ChatColor.RED + "That request does not exist");
                    return true;
                }
                if(DataStorage.team_player_in_team(sender.getName())) {
                    sender.sendMessage(ChatColor.RED + "You are already in a team");
                    sender.sendMessage(ChatColor.RED + "Abandon your team and try again");
                    return true;
                }

                Team teamJoin = DataStorage.team_get_by_player(playerFromName);

                if(teamJoin == null) {
                    sender.sendMessage(ChatColor.RED + "The player that invited you is no longer in the team or it has been deleted");
                    return true;
                }

                DataStorage.team_player_join(sender.getName(), teamJoin.name);
                DataStorage.request_remove(playerFromName);
                
                sender.sendMessage(ChatColor.GOLD + "Request accepted!");

            }
        }
        return true;
    }
    
}
