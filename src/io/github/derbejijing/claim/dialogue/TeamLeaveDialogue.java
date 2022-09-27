package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import net.md_5.bungee.api.ChatColor;

public class TeamLeaveDialogue extends Dialogue {

    public TeamLeaveDialogue(Player player) {
        super(player, 1, "leave team");
    }

    @Override
    public void initialPrompt() {
        if(!DataStorage.team_player_in_team(player.getName())) {
            this.sendMessageColored(ChatColor.RED + "You are not in a team");
            this.cancel();
            return;
        }

        this.sendMessageHeader();
        this.sendMessage("You will leave your team");
        if(DataStorage.team_get_by_player(this.player.getName()).getMemberCount() == 1) {
            this.sendMessageColored(ChatColor.RED + "If you do so, your team has no members");
            this.sendMessageColored(ChatColor.RED + "and thus, will be deleted!");
        }
        this.sendMessage("Write OK in chat to confirm");
        this.sendMessage("Write CANCEL in chat to quit");
        this.sendMessageFooter();

        ++this.stage;
    }

    @Override
    public void parseAnswer(String answer) {
        if(answer.toLowerCase().equals("ok")) {
            DataStorage.team_player_leave(this.player.getName());
            this.finish();
        } else this.cancel();
    }

    @Override
    public void cancelMessage() {
        
    }

    @Override
    public void successfulFinish() {
        this.sendMessage("You have abandoned your team!");
    }
    
}
