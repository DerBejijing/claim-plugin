package io.github.derbejijing.claim;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.derbejijing.claim.chunk.ChunkManager;
import io.github.derbejijing.claim.command.Claim;
import io.github.derbejijing.claim.command.ClaimInfo;
import io.github.derbejijing.claim.command.ClaimsMap;
import io.github.derbejijing.claim.command.ClaimsList;
import io.github.derbejijing.claim.command.TeamCreate;
import io.github.derbejijing.claim.command.TeamInfo;
import io.github.derbejijing.claim.command.TeamInvite;
import io.github.derbejijing.claim.command.TeamKick;
import io.github.derbejijing.claim.command.TeamLeave;
import io.github.derbejijing.claim.command.TeamList;
import io.github.derbejijing.claim.command.TeamLog;
import io.github.derbejijing.claim.command.Unclaim;
import io.github.derbejijing.claim.dialogue.DialogueManager;
import io.github.derbejijing.claim.event.ChatEvent;
import io.github.derbejijing.claim.event.PlayerEvent;
import io.github.derbejijing.claim.storage.DataStorage;

public class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {

        DataStorage.storage_initialize("claim_data.txt");
        DataStorage.storage_load();

        for(Player player : Bukkit.getOnlinePlayers()) ChunkManager.add_player(player);

        this.getCommand("claim").setExecutor(new Claim());
        this.getCommand("claiminfo").setExecutor(new ClaimInfo());
        this.getCommand("claimsmap").setExecutor(new ClaimsMap());
        this.getCommand("claimslist").setExecutor(new ClaimsList());
        this.getCommand("unclaim").setExecutor(new Unclaim());
        this.getCommand("teamcreate").setExecutor(new TeamCreate());
        this.getCommand("teaminfo").setExecutor(new TeamInfo());
        this.getCommand("teaminvite").setExecutor(new TeamInvite());
        this.getCommand("teaminviteaccept").setExecutor(new TeamInvite());
        this.getCommand("teaminvitedecline").setExecutor(new TeamInvite());
        this.getCommand("teamkick").setExecutor(new TeamKick());
        this.getCommand("teamleave").setExecutor(new TeamLeave());
        this.getCommand("teamlist").setExecutor(new TeamList());
        this.getCommand("teamlog").setExecutor(new TeamLog());

        this.getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvent(), this);

        new BukkitRunnable() {       
            @Override
            public void run() {
                DialogueManager.tick();
                DataStorage.tick();
            }
        }.runTaskTimer(this, 0, 20);

    }

    @Override
    public void onDisable() {
        DataStorage.storage_save();
    }

}
