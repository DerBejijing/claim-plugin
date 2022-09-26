package io.github.derbejijing.claim.dialogue;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;

public class DialogueManager {

    private static ArrayList<Dialogue> dialogues = new ArrayList<Dialogue>();
    private static ArrayList<UserPrompt> user_prompts = new ArrayList<UserPrompt>();

    public static void addDialogue(Player player, Class<Dialogue> dialogType) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) return;
        DialogueManager.dialogues.add(new TeamCreateDialogue(player));
    }

    public static void removeDialogue(Player player) {
        for(Dialogue d : DialogueManager.dialogues) if(d.player.equals(player)) d.cancel();
    }

    public static boolean handleChatMessage(Player player, String message) {
        if(user_prompt_active(player.getName())) {
            user_prompt_submit(player.getName(), message);
            return true;
        }
        return false;
    }

    public static void chat_block(String player) {

    }

    public static void chat_unblock(String player) {
    
    }

    public static boolean user_prompt_active(String player) {
        for(UserPrompt up : DialogueManager.user_prompts) if(up.player.equals(player)) return true;
        return false;
    }

    public static void user_prompt_start(String player) {
        for(UserPrompt up : DialogueManager.user_prompts) if(up.player.equals(player)) return;
        DialogueManager.user_prompts.add(new UserPrompt(player));
    }

    public static void user_prompt_submit(String player, String result) {
        for(UserPrompt up : DialogueManager.user_prompts) if(up.player.equals(player)) {
            up.result = result;
            up.answer();
            return;
        }
    }

    public static boolean user_prompt_answered(String player) {
        for(UserPrompt up : DialogueManager.user_prompts) if(up.player.equals(player)) if(up.answered) return true;
        return false;
    }

    public static String user_prompt_result(String player) {
        UserPrompt remove = null;
        String result = "";
        for(UserPrompt up : DialogueManager.user_prompts) if(up.player.equals(player)) if(up.answered) {
            remove = up;
            result = up.result;
        }
        DialogueManager.user_prompts.remove(remove);
        return result;
    }


    public static void tick() {
        Iterator<Dialogue> it = DialogueManager.dialogues.iterator();
        while(it.hasNext()) {
            Dialogue d = (Dialogue)it.next();
            if(!d.active()) dialogues.remove(d); 
        }
    }
    
}
