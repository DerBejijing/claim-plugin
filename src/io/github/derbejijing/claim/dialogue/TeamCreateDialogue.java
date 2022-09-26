package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

public class TeamCreateDialogue extends Dialogue {

    public TeamCreateDialogue(Player player) {
        super(player, 4, "create your team");
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
        switch(this.stage) {
            case 0:
                this.sendMessageHeader();
                this.sendMessage("Great name!");
                this.sendMessage("Now, give a short description:");
                this.sendMessageFooter();
                break;
            case 1:
                this.sendMessageHeader();
                this.sendMessage("Great!");
                this.sendMessage("Give a list of all players");
                this.sendMessageFooter();
                break;
            case 2:
                this.sendMessageHeader();
                this.sendMessage("Lol, another dumb question!");
                this.sendMessageFooter();
                break;
            case 3:
                this.sendMessageHeader();
                this.sendMessage("We are done!");
                this.sendMessage("Type ok to confirm!");
                this.sendMessageFooter();
                break;
            case 4:
                this.finish();
        }
    }


    @Override
    public void cancelMessage() {
        this.sendMessage("canceled");
    }
    
}