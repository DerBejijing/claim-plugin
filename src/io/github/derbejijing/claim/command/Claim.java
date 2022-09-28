package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.chunk.ChunkManager;
import io.github.derbejijing.claim.storage.DataStorage;
import net.md_5.bungee.api.ChatColor;

public class Claim implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(DataStorage.team_get_by_player(sender.getName()) == null) {
                sender.sendMessage(ChatColor.RED + "You are not in a team");
                return true;
            }

            if(args.length == 0) {
                ChunkManager.claim_chunk((Player)sender, ((Player)sender).getLocation().getChunk());
            }
            else if(args.length == 1) {
                if(args[0] == "start") {
                    ChunkManager.start_claiming((Player)sender);
                } else if(args[0] == "stop") {
                    ChunkManager.stop_claiming((Player)sender);
                } else return false;
            }
        }
        return true;
    }

}
