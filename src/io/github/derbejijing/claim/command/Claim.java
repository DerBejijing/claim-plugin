package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Claim implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if(args[0] == "start") {

            } else if(args[0] == "stop") {

            } else return false;
        }
        return true;
    }

}
