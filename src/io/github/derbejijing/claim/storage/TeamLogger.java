package io.github.derbejijing.claim.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;

public class TeamLogger {

    public String team;

    private String file_path;
    private File file;
    
    private LocalDateTime log_day;
    private DateTimeFormatter dtf_file = DateTimeFormatter.ofPattern("yy-MM-dd");
    private DateTimeFormatter dtf_log = DateTimeFormatter.ISO_LOCAL_TIME;


    public TeamLogger(String directory, String team) {
        try {
            this.team = team;
            this.log_day = LocalDateTime.now();
            this.file_path = directory + File.separator + team + File.separator;
            this.file = new File(this.file_path + this.log_day.format(this.dtf_file) + ".log");
            if(!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
            }
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
        this.log("logger started: " + this.file.getPath());
    }
    

    public void log(String text) {
        try {
            FileWriter log_fw = new FileWriter(this.file, true);
            BufferedWriter log_bw = new BufferedWriter(log_fw);
            
            String prefix = "[" + LocalDateTime.now().format(this.dtf_log) + "]: ";

            log_bw.write(prefix + text + "\n");
            log_bw.close();
            log_fw.close();
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }


    public ArrayList<String> getLog(int daysBeforeNow) {
        try {
            ArrayList<String> log = new ArrayList<String>();
            File f = null;

            if(daysBeforeNow == 0) {
                f = this.file;
            } else {
                LocalDateTime date = this.log_day.minusDays(daysBeforeNow);
                f = new File(this.file_path + date.format(this.dtf_file) + ".log");
                if(!f.exists()) return new ArrayList<String>();
            }

            Scanner reader = new Scanner(f);
            while(reader.hasNextLine()) {
                log.add(reader.nextLine());
            }

            reader.close();

            return log;
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
        return null;
    }


    public void tick() {
        try {
            LocalDateTime now = LocalDateTime.now();
            if(now.getDayOfMonth() != log_day.getDayOfMonth()) {
                this.file = new File(this.file_path + now.format(this.dtf_file) + ".log");
                this.log_day = LocalDateTime.now();
            }
        } catch(Exception e) {
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
    }

}
