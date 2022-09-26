package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

public class TeamCreateDialogue extends Dialogue {

    public TeamCreateDialogue(Player player, int length, String title) {
        super(player, length, title);
    }

    @Override
    public void initialPrompt() {
        
    }

    @Override
    public void parseAnswer(String answer) {
    }
    
}
