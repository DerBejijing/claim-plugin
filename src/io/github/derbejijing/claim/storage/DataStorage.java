package io.github.derbejijing.claim.storage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class DataStorage {

    private static File storage;
	private static FileWriter storage_fw;
	private static Scanner storage_fr;
    private static boolean initialized = false;

    private static ArrayList<Team> teams = new ArrayList<Team>();


    public static void storage_initialize(String filename) {
        if(DataStorage.initialized) return;
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


    public static boolean team_player_in_team(String name) {
        if(DataStorage.team_get_by_player(name) == null) return false;
        return true;
    }

}
