package io.github.derbejijing.claim.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.github.derbejijing.claim.dialogue.DialogueManager;

public class ChatEvent implements Listener {

    @EventHandler
    public void onMessageSend(AsyncPlayerChatEvent e) {
        DialogueManager.handleChatMessage(e.getPlayer(), e.getMessage());
    }
    
}
