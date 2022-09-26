package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

public class TeamCreateDialogue extends Dialogue {

    public TeamCreateDialogue(Player player) {
        super(player, 6, "create your team");
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
        if(answer.toLowerCase().equals("cancel")) this.cancel();
        ++this.stage;

        switch(this.stage) {
            case 0:
                this.sendMessageHeader();
                this.sendMessage("Great name!");
                this.sendMessage("Now, give a short subtitle:");
                this.sendMessageFooter();
                break;
            case 1:
                this.sendMessageHeader();
                this.sendMessage("Here is a list of currently supported");
                this.sendMessage("domains. Choose the one that fits best.");
                this.sendMessage("Only send the index letter:");
                this.sendMessage("A) Presidency");
                this.sendMessage("B) Dictatorship");
                this.sendMessage("C) Monarchy");
                this.sendMessage("D) Democratic Council");
                this.sendMessage("E) Dictated Council");
                this.sendMessage("F) Anarchy");
                this.sendMessage("G) Other");
                this.sendMessageFooter();
                break;
            case 2:
                this.sendMessageHeader();
                this.sendMessage("Name all leader members, separated");
                this.sendMessage("by a colon \",\"");
                this.sendMessageFooter();
                break;
            case 3:
                this.sendMessageHeader();
                this.sendMessage("Now set permissions for leaders.");
                this.sendMessage("Name all abilities only the leader(s)");
                this.sendMessage("will have and only send the index");
                this.sendMessage("letters non-separated");
                this.sendMessage("A) invite new players");
                this.sendMessage("B) promote players / transfer domain");
                this.sendMessage("C) kick players from the team");
                this.sendMessage("D) claim area for the team");
                this.sendMessage("E) none");
                this.sendMessageFooter();
                break;
            case 4:
                this.sendMessageHeader();
                this.sendMessage("Now set permissions for members.");
                this.sendMessage("Name all abilities everybody");
                this.sendMessage("will have and only send the index");
                this.sendMessage("letters non-separated");
                this.sendMessage("A) invite new players");
                this.sendMessage("B) promote players / transfer domain");
                this.sendMessage("C) kick players from the team");
                this.sendMessage("D) claim area for the team");
                this.sendMessage("E) none");
                this.sendMessageFooter();
                break;
            case 5:
                this.sendMessageHeader();
                this.sendMessage("Send ok to create <nation>");
                this.sendMessage("Here all data...");
                this.sendMessageFooter();
            case 6:
                this.finish();
        }
    }


    @Override
    public void cancelMessage() {
        this.sendMessage("canceled");
    }
    
}
