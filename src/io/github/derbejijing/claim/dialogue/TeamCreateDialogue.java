package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TeamCreateDialogue {
    
    private Player player;
    private int stage;
    private int length;
    private boolean active;

    public TeamCreateDialogue(Player player, int length) {
        this.player = player;
        this.stage = 0;
        this.length = length;
        this.active = true;
    }

    public void initialPrompt() {
        
    }

    public void parseAnswer(String answer) {

    }
    
    public void sendMessageHeader() {
        this.player.sendMessage("--------- [ create your team ] ---------");
    }

    public void sendMessageFooter() {
        this.player.sendMessage("----------------------------------------");
    }

    public void cancel() {

    }

    public boolean active() {
        return this.active;
    }

}
