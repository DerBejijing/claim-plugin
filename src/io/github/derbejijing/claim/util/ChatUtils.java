package io.github.derbejijing.claim.util;

import java.util.ArrayList;

public class ChatUtils {
    
    private static ArrayList<UserPrompt> user_prompts = new ArrayList<UserPrompt>();

    public static void chat_block(String player) {

    }

    public static void chat_unblock(String player) {
    
    }

    public static boolean user_prompt_active(String player) {
        for(UserPrompt up : ChatUtils.user_prompts) if(up.player.equals(player)) return true;
        return false;
    }

    public static void user_prompt_start(String player) {
        for(UserPrompt up : ChatUtils.user_prompts) if(up.player.equals(player)) return;
        ChatUtils.user_prompts.add(new UserPrompt(player));
    }

    public static void user_prompt_submit(String player, String result) {
        for(UserPrompt up : ChatUtils.user_prompts) if(up.player.equals(player)) {
            up.result = result;
            up.answer();
            return;
        }
    }

    public static boolean user_prompt_answered(String player) {
        for(UserPrompt up : ChatUtils.user_prompts) if(up.player.equals(player)) if(up.answered) return true;
        return false;
    }

    public static String user_prompt_result(String player) {
        UserPrompt remove = null;
        String result = "";

        for(UserPrompt up : ChatUtils.user_prompts) if(up.player.equals(player)) if(up.answered) {
            remove = up;
            result = up.result;
        }

        ChatUtils.user_prompts.remove(remove);

        return result;
    }

    public static void utils_tick() {

    }

}
