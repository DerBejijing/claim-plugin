package io.github.derbejijing.claim.chunk;

import org.bukkit.Chunk;

public class ChunkPlayer {

    public String name;
    public String team;
    public String chunk_enemy_team;
    public boolean claiming;
    public boolean in_enemy_terrain;
    public Chunk enemy_chunk;


    public ChunkPlayer(String name, String team) {
        this.name = name;
        this.team = team;
        this.chunk_enemy_team = "";
        this.claiming = false;
        this.in_enemy_terrain = false;
        this.enemy_chunk = null;
    }

}
