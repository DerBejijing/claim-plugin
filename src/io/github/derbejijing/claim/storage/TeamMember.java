package io.github.derbejijing.claim.storage;

public class TeamMember {
    
    public final String name;
    public boolean leader;
    public boolean permission_invite;
    public boolean permission_promote;
    public boolean permission_kick;
    public boolean permission_claim;


    public TeamMember(String name, boolean leader, boolean permission_invite, boolean permission_promote, boolean permission_kick, boolean permission_claim) {
        this.name = name;
        this.leader = leader;
        this.permission_invite = permission_invite;
        this.permission_promote = permission_promote;
        this.permission_kick = permission_kick;
        this.permission_claim = permission_claim;
    }

}
