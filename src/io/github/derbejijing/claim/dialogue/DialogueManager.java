package io.github.derbejijing.claim.dialogue;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;

public class DialogueManager {

    private static ArrayList<Dialogue> dialogues = new ArrayList<Dialogue>();

    
    public static void addDialogue(Player player, Class<Dialogue> dialogType) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) return;
        DialogueManager.dialogues.add(new TeamCreateDialogue(player));
    }


    public static void removeDialogue(Player player) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) d.cancel();
    }


    public static boolean handleChatMessage(Player player, String message) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) {
            d.parseAnswer(message);
            return true;
        }
        return false;
    }


    public static void tick() {
        Iterator<Dialogue> it = DialogueManager.dialogues.iterator();
        while(it.hasNext()) {
            Dialogue d = (Dialogue)it.next();
            if(!d.active()) dialogues.remove(d); 
        }
    }
    
}
