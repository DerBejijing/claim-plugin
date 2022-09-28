package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import net.md_5.bungee.api.ChatColor;

public class TeamList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(DataStorage.team_get_count() == 0) {
            sender.sendMessage(ChatColor.GRAY + "There are no teams");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "----------------------------------------");
        for(Team team : DataStorage.team_get_list()) sender.sendMessage(ChatColor.GRAY + team.name);
        sender.sendMessage(ChatColor.GREEN + "----------------------------------------");
        
        return true;
    }
    
}
