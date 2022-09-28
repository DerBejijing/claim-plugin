package io.github.derbejijing.claim.chunk;

public class ClaimChunk {
    
    public String team;
    public int x;
    public int z;

    
    public ClaimChunk(String team, int x, int z) {
        this.team = team;
        this.x = x;
        this.z = z;
    }


    public ClaimChunk(String team, String x, String z) {
        this.team = team.replace("-", " ");
        this.x = Integer.parseInt(x);
        this.z = Integer.parseInt(z);
    }


    public String getString() {
        return "CHUNK " + this.team.replace(" ", "-") + " " + this.x + " " + this.z;
    }

}
