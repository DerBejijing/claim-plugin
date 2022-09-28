package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import io.github.derbejijing.claim.storage.TeamMember;
import net.md_5.bungee.api.ChatColor;

public class TeamInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Team team = null;
        
        if(args.length == 0) {
            if(!DataStorage.team_player_in_team(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                sender.sendMessage(ChatColor.GRAY + "To display info about a team, run " + ChatColor.GREEN + "/teaminfo <team>");
                sender.sendMessage(ChatColor.GRAY + "To get a list of all teams, run " + ChatColor.GREEN + "/teamlist");
                return true;
            }
            team = DataStorage.team_get_by_player(sender.getName());
        } else if(args.length == 1) {
            team = DataStorage.team_get_by_name(args[0]);
            if(team == null) {
                sender.sendMessage(ChatColor.RED + "That team does not exist");
                sender.sendMessage(ChatColor.GRAY + "To get a list of all teams, run " + ChatColor.GREEN + "/teamlist");
                return true;
            }
        } else return false;

        sender.sendMessage(ChatColor.GREEN + "----------------------------------------");
        sender.sendMessage(ChatColor.GRAY + "Team name: " + team.name);
        sender.sendMessage(ChatColor.GRAY + "Team subtitle: " + team.subtitle);
        sender.sendMessage(ChatColor.GRAY + "Team domain: " + team.domain);
        sender.sendMessage(ChatColor.GRAY + "Team leader(s):");
        for(TeamMember tm : team.getLeaders()) sender.sendMessage(" " + tm.name);
        sender.sendMessage(ChatColor.GRAY + "Team member(s):");
        for(TeamMember tm : team.getMembers()) sender.sendMessage(" " + tm.name);

        return true;
    }
    
}
