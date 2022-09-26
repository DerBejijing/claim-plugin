package io.github.derbejijing.claim.dialogue;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public abstract class Dialogue {
    
    private String header_title;
    private Player player;
    private int stage;
    private int length;
    private boolean active;

    private final int header_length = 40;

    public Dialogue(Player player, int length, String title) {
        this.player = player;
        this.stage = 0;
        this.length = length;
        this.active = true;

        int title_length = (title.length() % 2 == 0 ? title.length() : title.length() + 1);
        int spacers_per_side = (this.header_length - title_length) / 2 - 3;
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < spacers_per_side; ++i) sb.append("-");
        sb.append(" [ ");
        sb.append(title);
        sb.append(" ] ");
        for(int i = 0; i < spacers_per_side; ++i) sb.append("-");

        this.header_title = sb.toString();
    }

    public abstract void initialPrompt();

    public abstract void parseAnswer(String answer);
    
    private void sendMessageHeader() {
        this.player.sendMessage("--------- [ create your team ] ---------");
    }

    private void sendMessageFooter() {
        StringBuilder progress_bar = new StringBuilder();
        int progress = this.stage / this.length * 16;
        progress_bar.append("---------- [ ");
        progress_bar.append(ChatColor.GREEN);

        for(int i = 0; i < progress; ++i) progress_bar.append("#");
        progress_bar.append(ChatColor.RED);
        for(int i = 0; i < 16 - progress; ++i) progress_bar.append("#");

        progress_bar.append(ChatColor.RESET);
        progress_bar.append(" ] ----------");

        this.player.sendMessage(progress_bar.toString());
    }

    public void cancel() {

    }

    public boolean active() {
        return this.active;
    }

}
