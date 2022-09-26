package io.github.derbejijing.claim.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ClaimInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        String url_string = "https://github.com/DerBejijing/claim-plugin";

        if(sender instanceof Player) {
    		Player player = (Player)sender;

            TextComponent url = new TextComponent(ChatColor.GRAY + url_string);
            url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url_string));

            player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            player.spigot().sendMessage(url);
            player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
        } else sender.sendMessage(url_string);

		return false;

    }    

}
