package io.github.derbejijing.claim.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.derbejijing.claim.util.ChatUtils;

public class ChatEvent implements Listener {

    @EventHandler
    public void onMessageSend(AsyncPlayerChatEvent e) {
        String player = e.getPlayer().getName();

        if(ChatUtils.user_prompt_active(player)) {
            ChatUtils.user_prompt_submit(player, e.getMessage());
            e.setCancelled(true);
        }
    }
    
}
