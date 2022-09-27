package io.github.derbejijing.claim.dialogue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.Team;

public class TeamCreateDialogue extends Dialogue {

    private Team team;


    public TeamCreateDialogue(Player player) {
        super(player, 7, "create your team");
        this.team = null;
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

        ++this.stage;
    }


    @Override
    public void parseAnswer(String answer) {
        if(answer.toLowerCase().equals("cancel")) this.cancel();

        boolean advance = true;

        switch(this.stage) {
            case 1:
                this.team = new Team(answer);

                this.sendMessageHeader();
                this.sendMessage("Great name!");
                this.sendMessage("Now, give a short subtitle:");
                this.sendMessageFooter();
                break;
            case 2:
                this.team.subtitle = answer;

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
            case 3:
                if(answer.toLowerCase().equals("a")) this.team.domain = "presidency";
                else if(answer.toLowerCase().equals("b")) this.team.domain = "dictatorship";
                else if(answer.toLowerCase().equals("c")) this.team.domain = "monarchy";
                else if(answer.toLowerCase().equals("d")) this.team.domain = "democratic council";
                else if(answer.toLowerCase().equals("e")) this.team.domain = "dictated council";
                else if(answer.toLowerCase().equals("f")) this.team.domain = "anarchy";
                else if(answer.toLowerCase().equals("g")) this.team.domain = "other";
                else {
                    this.sendMessage("Invalid option, try again.");
                    advance = false;
                    break;
                }

                this.sendMessageHeader();
                this.sendMessage("Name all leader members, separated");
                this.sendMessage("by a whitespace \" \". For anarchy,");
                this.sendMessage("There will be no leader.");
                this.sendMessageFooter();
                break;
            case 4:
                String[] members = answer.split(" ");
                for(String member : members) this.team.addMember(member, true);

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
            case 5:
                this.team.permission_l_invite = answer.toLowerCase().contains("A");
                this.team.permission_l_promote = answer.toLowerCase().contains("B");
                this.team.permission_l_kick = answer.toLowerCase().contains("C");
                this.team.permission_l_claim = answer.toLowerCase().contains("D");

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
            case 6:
                this.team.permission_m_invite = answer.toLowerCase().contains("A");
                this.team.permission_m_promote = answer.toLowerCase().contains("B");
                this.team.permission_m_kick = answer.toLowerCase().contains("C");
                this.team.permission_m_claim = answer.toLowerCase().contains("D");

                this.sendMessageHeader();
                this.sendMessage("Send ok to create " + this.team.name);
                this.sendMessage("Send cancel to quit");
                this.sendMessage("Here all data...");
                this.sendMessageFooter();
                break;
            case 7:
                if(!answer.toLowerCase().equals("ok")) {
                    this.cancel();
                    break;
                }
                this.finish();
        }

        if(advance) ++this.stage;
    }


    @Override
    public void cancelMessage() {
        this.sendMessage("canceled");
    }


    @Override
    public void successfulFinish() {
        this.sendMessage("Successfully created team " + this.team.name + "!");
        Bukkit.getLogger().info("created team");
    }
    
}
