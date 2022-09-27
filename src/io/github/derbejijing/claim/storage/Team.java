package io.github.derbejijing.claim.storage;

import java.util.ArrayList;

import org.bukkit.Bukkit;

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

    
    public Team(String name, String subtitle, String domain) {
        this.name = name;
        this.subtitle = subtitle;
        this.domain = domain;
        this.members = new ArrayList<TeamMember>();
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


    public ArrayList<String> getLeaders() {
        ArrayList<String> tmp = new ArrayList<String>();
        for(TeamMember tm : this.members) if(tm.leader) tmp.add(tm.name);
        return tmp;
    }


    public void addMember(TeamMember member) {
        for(TeamMember m : this.members) if(m.name.equals(member.name)) return;
        this.members.add(member);
    }


    
}
