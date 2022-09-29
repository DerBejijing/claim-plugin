package io.github.derbejijing.claim.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.chunk.ChunkManager;
import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import net.md_5.bungee.api.ChatColor;

public class ClaimsList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Team team = DataStorage.team_get_by_player(sender.getName());
            if(team == null) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                return true;
            }

            ArrayList<String> chunks = ChunkManager.get_claimed_chunk_positions(team.name);
            if(chunks.size() == 0) {
                sender.sendMessage(ChatColor.GRAY + "There are no chunks claimed by your team");
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            for(String s : chunks) sender.sendMessage(s);
            sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
        }
        return true;
    }
    
}
