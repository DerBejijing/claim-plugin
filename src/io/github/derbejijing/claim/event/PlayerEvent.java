package io.github.derbejijing.claim.event;

import java.net.http.WebSocket.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.derbejijing.claim.dialogue.DialogueManager;

public class PlayerEvent implements Listener {
    
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        DialogueManager.cancelDialogue(e.getPlayer());
    }

}
