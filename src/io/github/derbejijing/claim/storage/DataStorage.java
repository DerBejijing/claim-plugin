package io.github.derbejijing.claim.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;


public class DataStorage {

    private static File storage;
    private static boolean initialized = false;

    private static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<TeamInviteRequest> requests = new ArrayList<TeamInviteRequest>();


    public static void storage_initialize(String filename) {
        if(DataStorage.initialized) return;
        try {
            DataStorage.storage = new File(filename);
            if(!DataStorage.storage.exists()) DataStorage.storage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }


    public static void team_add(Team team) {
        for(Team t : DataStorage.teams) if(t.name.equals(team.name)) return;
        DataStorage.teams.add(team);
    }

    
    public static Team team_get_by_name(String name) {
        for(Team t : DataStorage.teams) if(t.name.equals(name)) return t;
        return null;
    }


    public static Team team_get_by_player(String name) {
        for(Team t : DataStorage.teams) {
            for(TeamMember tm : t.getMembers()) if(tm.name.equals(name)) return t;
        }
        return null;
    }


    public static void team_player_leave(String name) {
        Team team = null;
        TeamMember member = null;
        boolean deleteTeam = false;

        for(Team t : DataStorage.teams) {
            for(TeamMember tm : t.getMembers()) if(tm.name.equals(name)) {
                team = t;
                member = tm;
                if(t.getMemberCount() == 0) deleteTeam = true;
            }
        }

        team.removeMember(member.name);

        if(deleteTeam) DataStorage.teams.remove(team);
    }


    public static void team_player_join(String name, String team) {
        Team teamJoin = team_get_by_name(team);
        if(teamJoin == null) return;
        teamJoin.addMember(name, false);
    }


    public static boolean team_player_in_team(String name) {
        if(DataStorage.team_get_by_player(name) == null) return false;
        return true;
    }


    public static void request_add(String from, String to) {
        if(DataStorage.request_already_made(from)) return;
        DataStorage.requests.add(new TeamInviteRequest(from, to));
    }


    public static boolean request_already_made(String from) {
        for(TeamInviteRequest tir : DataStorage.requests) if(tir.from.equals(from)) return true;
        return false;
    }


    public static boolean request_exists(String from, String to) {
        for(TeamInviteRequest tir : DataStorage.requests) if(tir.from.equals(from)) if(tir.to.equals(to)) return true;
        return false;
    }


    public static void request_remove(String from) {
        TeamInviteRequest remove = null;
        for(TeamInviteRequest tir : DataStorage.requests) if(tir.from.equals(from)) remove = tir;
        DataStorage.requests.remove(remove);
    }


    public static void tick() {
        ArrayList<TeamInviteRequest> requests_remove = new ArrayList<TeamInviteRequest>();
        for(TeamInviteRequest tir : DataStorage.requests) if(tir.timeout_reached()) requests_remove.add(tir);
        DataStorage.requests.removeAll(requests_remove);
    }

}
