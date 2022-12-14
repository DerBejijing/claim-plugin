package io.github.derbejijing.claim.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import io.github.derbejijing.claim.storage.TeamMember;
import net.md_5.bungee.api.ChatColor;

public class TeamPromotion implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Team team = DataStorage.team_get_by_player(sender.getName());
            if(team == null) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                return true;
            }
            if(!DataStorage.team_player_can_promote(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that");
                return true;
            }

            if(args.length == 2) {
                TeamMember newLeader = null;
                for(TeamMember tm : team.getMembers()) if(tm.name.equals(args[1])) newLeader = tm;
                if(newLeader == null) {
                    sender.sendMessage(ChatColor.RED + "The specified player is not a member of your team");
                    return true;
                }
                
                if(args[0].equals("promote")) {
                    if(team.domain == "dictatorship" || team.domain == "monarchy" || team.domain == "anarchy") {
                        sender.sendMessage(ChatColor.RED + "Your team domain " + team.domain + " only allows one or no leaders");
                        sender.sendMessage(ChatColor.GRAY + "Please use " + ChatColor.GREEN + "\"/teampromotion transfer\"" + ChatColor.GREEN + " instead");
                        return false;
                    }
                    team.setLeader(args[1], false);
                } else if(args[0].equals("degrade")) {
                    if(team.domain == "dictatorship" || team.domain == "monarchy" || team.domain == "anarchy") {
                        sender.sendMessage(ChatColor.RED + "Your team domain " + team.domain + " only allows one or no leaders");
                        sender.sendMessage(ChatColor.GRAY + "Please use " + ChatColor.GREEN + "\"/teampromotion transfer\"" + ChatColor.GREEN + " instead");
                        return false;
                    }
                    team.degradeLeader(args[1]);
                    this.broadcast(ChatColor.GRAY + args[1] + ChatColor.RED + " has been degraded from domain!", team);
                    return true;
                } else if(args[0].equals("transfer")) {
                    team.setLeader(args[1], true);
                }

                this.broadcast(ChatColor.GRAY + "Your new leader is " + ChatColor.GREEN + args[1] + ChatColor.GRAY + "!", team);
                
                return true;
            }
        }
        return false;
    }


    private void broadcast(String text, Team team) {
        for(TeamMember tm : team.getMembers()) {
            Player player = Bukkit.getPlayer(tm.name);
            if(player != null) if(player.getName().equals(tm.name)) {
                player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
                player.sendMessage(ChatColor.GRAY + text);
                player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            } 
        }
    }
    
}
