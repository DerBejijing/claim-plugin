package io.github.derbejijing.claim.util;

public class UserPrompt {

    public String player;
    public String result;
    public boolean answered;
    
    public UserPrompt(String player) {
        this.player = player;
        this.result = "";
    }

    public void answer() {
        this.answered = true;
    }

}
