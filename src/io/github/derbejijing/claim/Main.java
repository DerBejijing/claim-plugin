package io.github.derbejijing.claim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.derbejijing.claim.command.Claim;
import io.github.derbejijing.claim.command.ClaimInfo;
import io.github.derbejijing.claim.command.ClaimsMap;
import io.github.derbejijing.claim.command.ListClaims;
import io.github.derbejijing.claim.command.Unclaim;

public class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {

        this.getCommand("claim").setExecutor(new Claim());
        this.getCommand("claiminfo").setExecutor(new ClaimInfo());
        this.getCommand("claimsmap").setExecutor(new ClaimsMap());
        this.getCommand("listclaims").setExecutor(new ListClaims());
        this.getCommand("unclaim").setExecutor(new Unclaim());
    }

    @Override
    public void onDisable() {
    }

}
