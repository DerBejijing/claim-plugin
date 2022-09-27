package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.derbejijing.claim.storage.DataStorage;

public class TeamInviteDecline implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DataStorage.request_remove(sender.getName());
        return true;
    }
    
}