package io.github.derbejijing.claim.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import net.md_5.bungee.api.ChatColor;

public class TeamLog implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Team team = DataStorage.team_get_by_player(sender.getName());
            if(team == null) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                return true;
            }

            if(!DataStorage.team_player_can_view_log(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "You are not allowed to view the log");
                return true;
            }

            int daysBeforeNow = 0;
            try {
                if(args.length == 1) daysBeforeNow = Integer.parseInt(args[0]);
            } catch(Exception e) {
                sender.sendMessage(ChatColor.GRAY + "Log file is empty");
                return true;
            }

            ArrayList<String> log = DataStorage.team_get_log(team.name, daysBeforeNow);
            if(log.size() == 0) {
                sender.sendMessage(ChatColor.GRAY + "log file is empty");
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            sender.sendMessage(ChatColor.GRAY + "Team log file from [" + daysBeforeNow + "] days ago:");
            for(String s : log) sender.sendMessage(s);
            sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
        }

        return true;
    
    }
    
}
