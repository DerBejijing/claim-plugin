package io.github.derbejijing.claim.chunk;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import net.md_5.bungee.api.ChatColor;

public class ChunkManager {
    
    private static ArrayList<ChunkPlayer> players = new ArrayList<ChunkPlayer>();
    private static ArrayList<ClaimChunk> chunks = new ArrayList<ClaimChunk>(); 


    public static void handleMovement(Player player, Chunk chunk) {
        for(ChunkPlayer p : ChunkManager.players) if(p.name.equals(player.getName())) {
            if(p.claiming) {
                if(DataStorage.team_player_can_claim(player.getName())) claim_chunk(player, chunk);
            }
            if(in_enemy_terrain(player, chunk)) {
                if(!p.in_enemy_terrain) {
                    player.sendMessage("You have entered enemy terrain");
                    p.in_enemy_terrain = true;
                }
            } else {
                if(p.in_enemy_terrain) {
                    player.sendMessage("You have left enemy terrain");
                    p.in_enemy_terrain = false;
                }
            }
        }
    }


    public static void start_claiming(Player player) {
        for(ChunkPlayer cp : ChunkManager.players) if(cp.name.equals(player.getName())) cp.claiming = true;
    }


    public static void stop_claiming(Player player) {
        for(ChunkPlayer cp : ChunkManager.players) if(cp.name.equals(player.getName())) cp.claiming = false;
    }


    public static boolean chunk_claimed(Chunk chunk) {
        for(ClaimChunk cc : ChunkManager.chunks) if(cc.x == chunk.getX()) if(cc.z == chunk.getZ()) return true;
        return false;
    }


    public static boolean in_enemy_terrain(Player player, Chunk chunk) {
        for(ClaimChunk cc : ChunkManager.chunks) if(cc.x == chunk.getX()) if(cc.z == chunk.getZ()) {
            Team t = DataStorage.team_get_by_player(player.getName());
            if(t == null) return true;
            if(cc.team.equals(t.name)) return false;
        }
        return false;
    }


    public static void claim_chunk(Player player, Chunk chunk) {
        if(chunk_claimed(chunk)) {
            player.sendMessage(ChatColor.RED + "Chunk already claimed");
            return;
        }
        ChunkManager.add_chunk(new ClaimChunk(DataStorage.team_get_by_player(player.getName()).name, chunk.getX(), chunk.getZ()));
    }


    public static void add_chunk(ClaimChunk chunk) {
        for(ClaimChunk cc : ChunkManager.chunks) if(chunk.x == cc.x) if(chunk.z == cc.z) return;
        ChunkManager.add_chunk(chunk);
    }


    public static void add_player(Player player) {
        for(ChunkPlayer cp : ChunkManager.players) if(cp.name.equals(player.getName())) return;
        ChunkPlayer cp = new ChunkPlayer(player.getName(), "");
        Team team = DataStorage.team_get_by_player(player.getName());
        if(team != null) cp.team = team.name;
        ChunkManager.players.add(cp);
    }


    public static void remove_player(Player player) {
        ChunkPlayer remove = null;
        for(ChunkPlayer cp : ChunkManager.players) if(cp.name.equals(player.getName())) remove = cp;
        ChunkManager.players.remove(remove);
    }

}