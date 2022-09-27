package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public abstract class Dialogue {
    
    public final Player player;

    protected String header_title;
    protected boolean active;
    protected int stages;
    protected int stage;

    protected final int header_length = 40;


    public Dialogue(Player player, int stages, String title) {
        this.player = player;
        this.stage = 0;
        this.stages = stages;
        this.active = true;

        int title_length = (title.length() % 2 == 0 ? title.length() : title.length() + 1);
        int spacers_per_side = (this.header_length - title_length) / 2 - 2;
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < spacers_per_side; ++i) sb.append("-");
        sb.append(" [ ");
        sb.append(title);
        sb.append(" ] ");
        for(int i = 0; i < spacers_per_side; ++i) sb.append("-");

        this.header_title = sb.toString();
        
        this.initialPrompt();
    }


    public abstract void initialPrompt();


    public abstract void parseAnswer(String answer);
    

    public abstract void cancelMessage();


    public abstract void successfulFinish();


    protected void sendMessageHeader() {
        this.sendMessage(this.header_title);
    }


    protected void sendMessageFooter() {
        StringBuilder progress_bar = new StringBuilder();
        float progress = ((float)this.stage / (float)this.stages) * 16.0f;
        progress_bar.append(ChatColor.GRAY);
        progress_bar.append("---------- [");
        progress_bar.append(ChatColor.GREEN);

        for(int i = 0; i < progress; ++i) progress_bar.append("#");
        progress_bar.append(ChatColor.RED);
        for(int i = 0; i < 16 - progress; ++i) progress_bar.append("#");

        progress_bar.append(ChatColor.GRAY);
        progress_bar.append("] ----------");

        this.sendMessageColored(progress_bar.toString());
    }


    protected void sendMessage(String message) {
        this.player.sendMessage(ChatColor.GRAY + message);
    }


    protected void sendMessageColored(String message) {
        this.player.sendMessage(message);
    }


    protected void cancel() {
        this.cancelMessage();
        this.active = false;
    }
    
    protected void finish() {
        this.successfulFinish();
        this.active = false;
    }

    public boolean active() {
        return this.active;
    }

}
