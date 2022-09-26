package io.github.derbejijing.claim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.derbejijing.claim.command.Claim;
import io.github.derbejijing.claim.command.ClaimInfo;
import io.github.derbejijing.claim.command.ClaimsMap;
import io.github.derbejijing.claim.command.ListClaims;
import io.github.derbejijing.claim.command.TeamCreate;
import io.github.derbejijing.claim.command.TeamInvite;
import io.github.derbejijing.claim.command.TeamKick;
import io.github.derbejijing.claim.command.TeamLeave;
import io.github.derbejijing.claim.command.TeamLog;
import io.github.derbejijing.claim.command.Unclaim;

public class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {

        this.getCommand("claim").setExecutor(new Claim());
        this.getCommand("claiminfo").setExecutor(new ClaimInfo());
        this.getCommand("claimsmap").setExecutor(new ClaimsMap());
        this.getCommand("listclaims").setExecutor(new ListClaims());
        this.getCommand("unclaim").setExecutor(new Unclaim());
        this.getCommand("teamcreate").setExecutor(new TeamCreate());
        this.getCommand("teaminvite").setExecutor(new TeamInvite());
        this.getCommand("teamkick").setExecutor(new TeamKick());
        this.getCommand("teamleave").setExecutor(new TeamLeave());
        this.getCommand("teamlog").setExecutor(new TeamLog());

    }

    @Override
    public void onDisable() {
    }

}
