package io.github.derbejijing.claim.storage;

import java.util.ArrayList;

public class Team {

    public String name;
    public String subtitle;
    public String domain;
    
    public boolean permission_invite;
    public boolean permission_promote;
    public boolean permission_kick;
    public boolean permission_claim;

    private ArrayList<TeamMember> members;


    public Team(String name) {
        this.name = name;
        this.subtitle = "";
        this.domain = "";
        this.members = new ArrayList<TeamMember>();
    }

    
    public Team(String name, String subtitle, String domain, String permission_invite, String permission_promote, String permission_kick, String permission_claim) {
        this.name = name.replace("-", " ");
        this.subtitle = subtitle.replace("-", " ");
        this.domain = domain.replace("-", " ");
        this.permission_invite = Boolean.parseBoolean(permission_invite);
        this.permission_promote = Boolean.parseBoolean(permission_promote);
        this.permission_kick = Boolean.parseBoolean(permission_kick);
        this.permission_claim = Boolean.parseBoolean(permission_claim);
        this.members = new ArrayList<TeamMember>();
    }

    public String getString() {
        return "TEAM " + this.name.replace(" ", "-") +
            " " + this.subtitle.replace(" ", "-") +
            " " + this.domain.replace(" ", "-") +
            " " + this.permission_invite +
            " " + this.permission_promote +
            " " + this.permission_kick +
            " " + this.permission_claim;
    }


    public ArrayList<String> getMembershipStrings() {
        ArrayList<String> tmp = new ArrayList<String>();
        for(TeamMember tm : this.members) tmp.add(tm.getString());
        return tmp;
    }


    public void addMember(String player, boolean leader) {
        for(TeamMember tm : this.members) if(tm.name.equals(player)) return;
        this.members.add(new TeamMember(player, leader, permission_invite, permission_promote, permission_kick, permission_claim));
    }


    public void setLeader(String player) {
        boolean found = false;
        for(TeamMember tm : this.members) if(tm.name.equals(player)) {
            found = true;
            tm.leader = true;
        }
        if(!found) return;

    }


    public ArrayList<TeamMember> getLeaders() {
        ArrayList<TeamMember> tmp = new ArrayList<TeamMember>();
        for(TeamMember tm : this.members) if(tm.leader) tmp.add(tm);
        return tmp;
    }


    public void addMember(TeamMember member) {
        for(TeamMember m : this.members) if(m.name.equals(member.name)) return;
        this.members.add(member);
    }


    public void removeMember(String name) {
        TeamMember remove = null;
        for(TeamMember tm : this.members) if(tm.name.equals(name)) remove = tm;
        this.members.remove(remove);
    }

    
    public int getMemberCount() {
        return this.members.size();
    }
    

    public ArrayList<TeamMember> getMembers() {
        return this.members;
    }
}
