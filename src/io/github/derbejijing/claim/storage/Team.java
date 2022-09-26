package io.github.derbejijing.claim.storage;

import java.util.ArrayList;

public class Team {

    private String name;
    private String description;
    private ArrayList<TeamMember> members;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<TeamMember>();
    }

    public void addMember(TeamMember member) {
        for(TeamMember m : this.members) if(m.getName().equals(member.getName())) return;
        this.members.add(member);
    }


    
}
