package io.github.derbejijing.claim.storage;

public class TeamMember {
    
    public final String name;
    public boolean leader;
    public boolean permission_invite;
    public boolean permission_promote;
    public boolean permission_kick;
    public boolean permission_claim;
    public boolean permission_log;


    public TeamMember(String name, boolean leader, boolean permission_invite, boolean permission_promote, boolean permission_kick, boolean permission_claim, boolean permission_log) {
        this.name = name;
        this.leader = leader;
        this.permission_invite = permission_invite;
        this.permission_promote = permission_promote;
        this.permission_kick = permission_kick;
        this.permission_claim = permission_claim;
        this.permission_log = permission_log;
    }


    public TeamMember(String name, String leader, String permission_invite, String permission_promote, String permission_kick, String permission_claim, String permission_log) {
        this.name = name;
        this.leader = Boolean.parseBoolean(leader);
        this.permission_invite = Boolean.parseBoolean(permission_invite);
        this.permission_promote = Boolean.parseBoolean(permission_promote);
        this.permission_kick = Boolean.parseBoolean(permission_kick);
        this.permission_claim = Boolean.parseBoolean(permission_claim);
        this.permission_log = Boolean.parseBoolean(permission_log);
    }


    public String getString() {
        return "MEMBER " + name + " " + leader + " " + permission_invite + " " + permission_promote + " " + permission_kick + " " + permission_claim + " " + permission_log;
    }

}
