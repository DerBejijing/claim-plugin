package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.derbejijing.claim.util.ChatUtils;

public class TeamCreate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ChatUtils.user_prompt_start(sender.getName());
        return false;
    }
    
}
