package io.github.derbejijing.claim.chunk;

public class ChunkPlayer {

    public String name;
    public String team;
    public boolean claiming;
    public boolean in_enemy_terrain;


    public ChunkPlayer(String name, String team) {
        this.name = name;
        this.team = team;
        this.claiming = false;
        this.in_enemy_terrain = false;
    }

}
