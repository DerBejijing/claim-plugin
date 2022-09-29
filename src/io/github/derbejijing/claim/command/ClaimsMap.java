package io.github.derbejijing.claim.command;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.claim.chunk.ChunkManager;
import io.github.derbejijing.claim.storage.DataStorage;
import io.github.derbejijing.claim.storage.Team;
import net.md_5.bungee.api.ChatColor;

public class ClaimsMap implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            Team team = DataStorage.team_get_by_player(player.getName());
            String player_team = team == null ? "" : team.name;

            player.sendMessage(ChatColor.GREEN + "------- [WEST] -------");

            for(int x = -3; x <= 3; ++x) {
                StringBuilder sb = new StringBuilder();
                for(int z = -10; z <= 10; ++z) {
                    Chunk test = player.getWorld().getChunkAt(player.getLocation().add(16*x, 0, 16*z));
                    String owner_team = ChunkManager.getOwnerTeam(test);
                    if(x == 0 && z == 0) sb.append(ChatColor.DARK_PURPLE + "+" + ChatColor.RESET);
                    else if(owner_team == "") sb.append("-");
                    else if(player_team.equals(owner_team)) sb.append(ChatColor.GREEN + "#" + ChatColor.RESET);
                    else sb.append(ChatColor.RED + "#" + ChatColor.RESET);
                }
                player.sendMessage(sb.toString());
            }

            player.sendMessage(ChatColor.GREEN + "---------------------");

        }
        return true;
    }

}
