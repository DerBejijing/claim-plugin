package io.github.derbejijing.claim.dialogue;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DialogueManager {

    private static ArrayList<Dialogue> dialogues = new ArrayList<Dialogue>();

    public static void addDialogueTeamCreate(Player player) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) return;
        DialogueManager.dialogues.add(new TeamCreateDialogue(player));
    }


    public static void addDialogueTeamLeave(Player player) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) return;
        DialogueManager.dialogues.add(new TeamLeaveDialogue(player));
    }


    public static void cancelDialogue(Player player) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) d.cancel();
    }


    public static boolean handleChatMessage(Player player, String message) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) {
            Bukkit.getLogger().info("parse message: " + message);
            d.parseAnswer(message);
            return true;
        }
        return false;
    }


    public static void tick() {
        ArrayList<Dialogue> remove = new ArrayList<Dialogue>();
        for(Dialogue d : DialogueManager.dialogues) if(!d.active()) remove.add(d);
        DialogueManager.dialogues.removeAll(remove);
    }
    
}
