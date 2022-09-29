package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.chunk.ChunkManager;

public class Unclaim implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {
                ChunkManager.unclaim_chunk((Player)sender, ((Player)sender).getLocation().getChunk());
                return true;
            }
            else if(args.length == 1) if(args[0].equals("all")) ChunkManager.unclaim_all((Player)sender);
        }
        return true;
    }
    
}
