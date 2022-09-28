package io.github.derbejijing.claim.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.derbejijing.claim.chunk.ChunkManager;
import io.github.derbejijing.claim.dialogue.DialogueManager;

public class PlayerEvent implements Listener {
    
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        ChunkManager.add_player(e.getPlayer());
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        DialogueManager.cancelDialogue(e.getPlayer());
        ChunkManager.remove_player(e.getPlayer());
    }

    @EventHandler
    public void playerChangeChunk(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if(!e.getTo().getChunk().equals(e.getTo().getChunk())) {
            ChunkManager.handleMovement(player, e.getTo().getChunk());
        }
    }

}
