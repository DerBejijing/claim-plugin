package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

public class TeamCreateDialogue extends Dialogue {

    public TeamCreateDialogue(Player player) {
        super(player, 7, "create your team");
    }


    @Override
    public void initialPrompt() {
        this.sendMessageHeader();

        this.sendMessage("You have started the interactive team");
        this.sendMessage("creation utility. You will have to");
        this.sendMessage("answer a few questions to get started");
        this.sendMessage("All questions are answered through chat");
        this.sendMessage("To cancel, type \"cancel\" in chat. Please");
        this.sendMessage("type the desired name of your team:");

        this.sendMessageFooter();
    }


    @Override
    public void parseAnswer(String answer) {
    }

    
    @Override
    public void cancelMessage() {
        this.sendMessage("canceled");
    }
    
}
