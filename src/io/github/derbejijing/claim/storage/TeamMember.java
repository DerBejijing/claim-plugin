package io.github.derbejijing.claim.storage;

public class TeamMember {
    
    private String name;
    private boolean may_invite_players;
    private boolean may_kick_players;
    private boolean may_claim_chunks;

    public TeamMember(String name, boolean may_invite_players, boolean may_kick_players, boolean may_claim_chunks) {
        this.name = name;
        this.may_invite_players = may_invite_players;
        this.may_kick_players = may_kick_players;
        this.may_claim_chunks = may_claim_chunks;
    }

    public String getName() {
        return this.name;
    }

}
