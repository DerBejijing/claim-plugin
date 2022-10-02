package io.github.derbejijing.claim.chunk;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import io.github.derbejijing.claim.storage.TeamMember;
import net.md_5.bungee.api.ChatColor;

public class ChunkManager {
    
    private static ArrayList<ChunkPlayer> players = new ArrayList<ChunkPlayer>();
    private static ArrayList<ClaimChunk> chunks = new ArrayList<ClaimChunk>(); 


    public static ArrayList<String> getChunksString() {
        ArrayList<String> tmp = new ArrayList<String>();
        for(ClaimChunk cp : ChunkManager.chunks) tmp.add("CHUNK " + cp.team + " " + cp.x + " " + cp.z);
        return tmp;
    }


    public static void handleMovement(Player player, Chunk chunk) {
        for(ChunkPlayer p : ChunkManager.players) if(p.name.equals(player.getName())) {
            if(p.claiming) {
                if(DataStorage.team_player_can_claim(player.getName())) claim_chunk(player, chunk);
                else player.sendMessage(ChatColor.RED + "You cannot claim more chunks");
            }

            // still a very dumb implementation
            // but who gives a shit anyway

            String chunk_owner = getOwnerTeam(chunk);
            Team owner_team = DataStorage.team_get_by_name(chunk_owner);
            boolean player_visible = DataStorage.log_property_violation;
            for(PotionEffect effect : player.getActivePotionEffects()) if(effect.getType().equals(PotionEffectType.INVISIBILITY)) player_visible = false;

            // player has left enemy terrain
            if(chunk_owner == "") {
                if(!p.chunk_enemy_team.equals("")) {
                    player.sendMessage(ChatColor.YELLOW + "You have left enemy terrain by " + ChatColor.GRAY + p.chunk_enemy_team);
                    if(player_visible) message_team(p.chunk_enemy_team, "someone has left your terrain");
                }
            }
            // player is in enemy terrain
            else {
                // player has entered enemy terrain
                if(p.chunk_enemy_team == "") {
                    player.sendMessage(ChatColor.YELLOW + "You have entered enemy terrain by " + ChatColor.GRAY + chunk_owner);
                    if(player_visible) message_team(chunk_owner, "someone has entered your terrain");
                }
                // player is in enemy terrain and enters new
                else if(!p.chunk_enemy_team.equals(chunk_owner)) {
                    player.sendMessage(ChatColor.YELLOW + "You left enemy terrain by " + ChatColor.GRAY + p.chunk_enemy_team + ChatColor.YELLOW + " and entered terrain by " + ChatColor.GRAY + chunk_owner);
                    
                    if(player_visible) {
                        message_team(chunk_owner, "someone has left your terrain");
                        message_team(p.chunk_enemy_team, "someone has entered your terrain");
                    }
                }
                // player is still in enemy terrain
                else if(p.chunk_enemy_team.equals(chunk_owner));
            }

            p.chunk_enemy_team = chunk_owner;
        }
    }


    private static void message_team(String team_name, String message) {
        Team team = DataStorage.team_get_by_name(team_name);
        if(team == null) return;
        DataStorage.team_log(team_name, message);
        for(TeamMember tm : team.getMembers()) {
            Player team_member = Bukkit.getPlayer(tm.name);
            if(team_member != null) team_member.sendMessage(ChatColor.YELLOW + message);
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
            if(!cc.team.equals(t.name)) return true;
        }
        return false;
    }


    public static String getOwnerTeam(Chunk chunk) {
        for(ClaimChunk cc : ChunkManager.chunks) if(cc.x == chunk.getX()) if(cc.z == chunk.getZ()) return cc.team;
        return "";
    }


    public static void claim_chunk(Player player, Chunk chunk) {
        if(chunk_claimed(chunk)) {
            player.sendMessage(ChatColor.RED + "Chunk already claimed");
            return;
        }
        if(!DataStorage.team_player_can_claim(player.getName())) {
            player.sendMessage(ChatColor.RED + "You cannot claim more chunks ore are not permitted to do so");
            return;
        }
        DataStorage.team_player_claim_chunk(player.getName());
        ChunkManager.add_chunk(new ClaimChunk(DataStorage.team_get_by_player(player.getName()).name, chunk.getX(), chunk.getZ()));
        DataStorage.team_log(ChunkManager.getOwnerTeam(chunk), player.getName() + " has claimed chunk [" + chunk.getX() + " " + chunk.getZ() + "]");
        player.sendMessage(ChatColor.GRAY + "claimed chunk [" + chunk.getX() + " " + chunk.getZ() + "]");
    }


    public static void unclaim_chunk(Player player, Chunk chunk) {
        if(!DataStorage.team_player_can_unclaim(player.getName())) {
            player.sendMessage(ChatColor.RED + "You are not permitted to unclaim chunks");
        }
        Team team = DataStorage.team_get_by_player(player.getName());
        if(team == null) return;
        ClaimChunk remove = null;
        for(ClaimChunk cc : ChunkManager.chunks) if(chunk.getX() == cc.x) if(chunk.getZ() == cc.z) if(cc.team.equals(team.name)) remove = cc;
        if(remove == null) {
            player.sendMessage(ChatColor.RED + "Chunk not claimed or does not belong to you");
            return;
        }

        DataStorage.team_log(ChunkManager.getOwnerTeam(chunk), player.getName() + " has unclaimed chunk [" + chunk.getX() + " " + chunk.getZ() + "]");
        DataStorage.team_player_unclaim_chunk(player.getName());
        ChunkManager.chunks.remove(remove);
        player.sendMessage(ChatColor.GRAY + "unclaimed chunk [" + chunk.getX() + " " + chunk.getZ() + "]");
    }


    public static void unclaim_all(Player player) {
        Team team = DataStorage.team_get_by_player(player.getName());
        if(team == null) {
            player.sendMessage(ChatColor.RED + "You are not in a team");
            return;
        }

        if(!DataStorage.team_player_can_unclaim(player.getName())) {
            player.sendMessage(ChatColor.RED + "You are not permitted to unclaim chunks");
            return;
        }

        ArrayList<ClaimChunk> remove = new ArrayList<ClaimChunk>();
        for(ClaimChunk cc : ChunkManager.chunks) if(cc.team.equals(team.name)) remove.add(cc);
        ChunkManager.chunks.removeAll(remove);
        player.sendMessage(ChatColor.GRAY + "Unclaimed " + remove.size() + " chunks");
    }


    public static void add_chunk(ClaimChunk chunk) {
        for(ClaimChunk cc : ChunkManager.chunks) if(chunk.x == cc.x) if(chunk.z == cc.z) return;
        ChunkManager.chunks.add(chunk);
    }


    public static void add_chunk(String team, String x, String z) {
        Team t = DataStorage.team_get_by_name(team);
        if(team == null) return;
        ChunkManager.chunks.add(new ClaimChunk(team, x, z));
        ++t.claimed_chunks;
    }


    public static ArrayList<String> get_claimed_chunk_positions(String team) {
        ArrayList<String> tmp = new ArrayList<String>();
        for(ClaimChunk cc : ChunkManager.chunks) if(cc.team.equals(team)) {
            tmp.add("chunk [" + cc.x + " " + cc.z + "] at [" + cc.x * 16 + " " + cc.z * 16 + "]");
        }
        return tmp;
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
