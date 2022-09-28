package io.github.derbejijing.claim.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.util.FileUtil;


public class DataStorage {

    private static File storage;
    private static FileWriter storage_fw;
    private static Scanner storage_fr;
    private static boolean initialized = false;

    private static String team_log_dir = "team_logs";

    private static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<TeamLogger> team_logs = new ArrayList<TeamLogger>();
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
                    DataStorage.team_add(currentTeam, false);
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


    public static boolean delete_dir(File directory) {
        File[] contents = directory.listFiles();
        if(contents != null) {
            for(File f : contents) delete_dir(f);
        }
        return directory.delete();
    }


    public static ArrayList<Team> team_get_list() {
        return DataStorage.teams;
    }


    public static int team_get_count() {
        return DataStorage.teams.size();
    }


    public static void team_add(Team team, boolean overwriteLogs) {
        for(Team t : DataStorage.teams) if(t.name.equals(team.name)) return;
        DataStorage.teams.add(team);

        if(overwriteLogs) {
            try {
                if(Files.isDirectory(Paths.get(team_log_dir + File.separator + team.name))) {
                    File directory = new File(team_log_dir + File.separator + team.name);
                    delete_dir(directory);
                }
            } catch(Exception e) {
                e.printStackTrace();
                Bukkit.getServer().shutdown();
            }
        }

        DataStorage.team_logs.add(new TeamLogger(team_log_dir, team.name));
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

        DataStorage.team_log(team.name, name + " left the team");

        if(deleteTeam) {
            DataStorage.team_delete(team.name);
        }
    }


    public static void team_delete(String teamName) {
        Team team = null;
        TeamLogger teamLogger = null;

        for(Team t : DataStorage.teams) if(t.name.equals(teamName)) team = t;
        for(TeamLogger tl : DataStorage.team_logs) if(tl.team.equals(teamName)) teamLogger = tl;

        if(team == null || teamLogger == null) return;

        teamLogger.log("Team has been deleted");

        DataStorage.teams.remove(team);
        DataStorage.team_logs.remove(teamLogger);
    }


    public static boolean team_exists(String team) {
        for(Team t : DataStorage.teams) if(t.name.equals(team)) return true;
        return false;
    }


    public static void team_player_join(String name, String team) {
        Team teamJoin = team_get_by_name(team);
        if(teamJoin == null) return;
        teamJoin.addMember(name, false);

        DataStorage.team_log(team, name + " joined the team");
    }


    public static boolean team_player_in_team(String name) {
        if(DataStorage.team_get_by_player(name) == null) return false;
        return true;
    }


    public static void team_log(String team, String text) {
        for(TeamLogger tl : DataStorage.team_logs) if(tl.team.equals(team)) tl.log(text);
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
