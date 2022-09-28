package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.dialogue.DialogueManager;
import io.github.derbejijing.claim.storage.DataStorage;
import net.md_5.bungee.api.ChatColor;

public class TeamCreate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(DataStorage.team_player_in_team(sender.getName())) {
                sender.sendMessage(ChatColor.RED + "You are already in a team");
                sender.sendMessage(ChatColor.RED + "Abandon your team and try again");
                return true;
            }
            DialogueManager.addDialogueTeamCreate((Player)sender);
        }
        return true;
    }
    
}
