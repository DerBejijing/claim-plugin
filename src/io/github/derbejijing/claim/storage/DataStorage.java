package io.github.derbejijing.claim.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;


public class DataStorage {

    private static File storage;
    private static FileWriter storage_fw;
    private static Scanner storage_fr;
    private static boolean initialized = false;

    private static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<TeamInviteRequest> requests = new ArrayList<TeamInviteRequest>();


    public static void storage_initialize(String filename) {
        if(DataStorage.initialized) return;
        try {
            DataStorage.storage = new File(filename);
            DataStorage.storage_fw = null;
            DataStorage.storage_fr = null;
            if(!DataStorage.storage.exists()) DataStorage.storage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }


    public static void storage_save() {
        try {
            DataStorage.storage.delete();
            DataStorage.storage.createNewFile();
            DataStorage.storage_fw = new FileWriter(DataStorage.storage);
            for(Team m : DataStorage.teams) {
                storage_write_line(m.getString());
                for(String s : m.getMembershipStrings()) storage_write_line(s);
            }
            DataStorage.storage_fw.close();
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }


    public static void storage_load() {
        try {
            DataStorage.storage_fr = new Scanner(DataStorage.storage);
            Team currentTeam = null;

            while(DataStorage.storage_fr.hasNextLine()) {
                String line = DataStorage.storage_fr.nextLine();
                String line_unspaced = line.replace(" ", "");
                int spaces = line.length() - line_unspaced.length();
                String[] data = line.split(" ");

                if(line.startsWith("TEAM") && spaces == 7) {
                    currentTeam = new Team(data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                    DataStorage.team_add(currentTeam);
                }

                if(line.startsWith("MEMBER") && spaces == 6) {
                    currentTeam.addMember(new TeamMember(data[1], data[2], data[3], data[4], data[5], data[6]));
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }


    public static void storage_write_line(String line) throws IOException {
        DataStorage.storage_fw.write(line + "\n");
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
                if(t.getMemberCount() == 1) deleteTeam = true;
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
