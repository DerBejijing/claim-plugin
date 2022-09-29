package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import io.github.derbejijing.claim.storage.TeamMember;
import net.md_5.bungee.api.ChatColor;

public class TeamKick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Team team = DataStorage.team_get_by_player(sender.getName());
            if(team == null) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                return true;
            }
            if(args.length == 1) {
                for(TeamMember tm : team.getMembers()) if(tm.name.equals(args[0])) {
                    if(DataStorage.team_player_can_kick(sender.getName())) {
                        team.removeMember(tm.name);
                        DataStorage.team_log(team.name, sender.getName() + " kicked " + args[0]);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You are not allowed to kick players");
                        sender.sendMessage(ChatColor.RED + "This incident will be reported");
                        DataStorage.team_log(team.name, sender.getName() + " attempted to kick " + args[0]);
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Player not found");
            } else return false;
        }
        return true;
    }
    
}
