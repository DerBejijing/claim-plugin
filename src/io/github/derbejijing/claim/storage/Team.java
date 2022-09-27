package io.github.derbejijing.claim.storage;

import java.util.ArrayList;

public class Team {

    public String name;
    public String subtitle;
    public String domain;
    
    public boolean permission_l_invite;
    public boolean permission_l_promote;
    public boolean permission_l_kick;
    public boolean permission_l_claim;
    public boolean permission_m_invite;
    public boolean permission_m_promote;
    public boolean permission_m_kick;
    public boolean permission_m_claim;

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
        if(leader) this.members.add(new TeamMember(name, leader, permission_l_invite, permission_l_promote, permission_l_kick, permission_l_claim));
        else this.members.add(new TeamMember(name, leader, permission_m_invite, permission_m_promote, permission_m_kick, permission_m_claim));
    }


    public void setLeader(String player) {
        boolean found = false;
        for(TeamMember tm : this.members) if(tm.name.equals(player)) {
            found = true;
            tm.leader = true;
            tm.permission_invite = permission_l_invite;
            tm.permission_promote = permission_l_promote;
            tm.permission_kick = permission_l_kick;
            tm.permission_claim = permission_l_claim;
        }
        if(!found) return;

    }


    public void addMember(TeamMember member) {
        for(TeamMember m : this.members) if(m.name.equals(member.name)) return;
        this.members.add(member);
    }


    
}
