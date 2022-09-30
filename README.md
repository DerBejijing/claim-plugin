# claim-plugin
another made-to-order minecraft plugin

# About

If you do not know what this is about, then this won't be relevant to you.  
It is a minecraft plugin I was requested to program.  
Players can create Teams (more like Nations) and claim chunks. This is purely symbolic and does not affect the gameplay.  

<br>

# User commands  
### team commands
`/teamcreate`  
Will start a chat dialogue where the player is prompted for information to create a nation.  
It asks for a name, a short subtitle, what type of domain it is (dictatorship, council, etc.) (this is also purely for status and has no real effect besides influencing the maximum number of leaders) and what permissions non-leaders have. By default the player creating the team, becomes the leader.  

`/teaminvite <player>`  
Sends an invitation to <player> to join your team.  
This action will appear in the team log.  
The player sending the message must have been given permission by the team leader (see team permissions section).  
The request will timeout after 2 minutes, during which the player may not send any more requests.  

`/teamkick <player>`  
Will kick <player> from the team.  
This action will appear in the team log.  
The player sending the message must have been given permission by the team leader (see team permissions section).  

`/teamleave`  
Leave the team. If you are the last player in the team, it will be deleted upon leaving. The user is reminded about this and is prompted to confirm this action by writing "ok" in chat.  
This action will appear in the team log.

`/teamlist`  
Get a list of all teams.  

`/teaminfo [team]`   
If [team] is set, the player will recieve a message with all public information about the team (name, subtitle, domain, members and leaders). This does not include the member permissions, which will only be shown if the supplied team equals the one by the player requesting the information.  
Supplying no team name will attempt to get all information about the player's team.  

`/teampromotion <action> <player>`  
Will change the leader(s) of the team. The first argument must be "promote", "degrade" or "transfer", while the second one is a team member/leader.  
"Promote" adds the specified player to the leaders.  
"Degrade" remvokes the players leader status.  
"Transfer" will transfer leadership to the specified player.  
This action will appear in the team log.  
The player sending the message must have been given permission by the team leader (see team permissions section).  

`/teamlog [days ago]`  
Will display the team's log file from, if supplied, <days ago> days ago.  
The player sending the message must have been given permission by the team leader (see team permissions section).  
  
### claim commands  
`/claim [action]`  
Will claim the chunk the player is currently in. "Action" can be "start" or "stop" to continuously claim multiple chunks without running the command over and over again. The chunk cannot already be claimed, and a set limit of claimable chunks may not be exceeded. It is determinable by multiplying the team member count by 50.  
This action will appear in the team log.  
The player sending the message must have been given permission by the team leader (see team permissions section).  
  
`/unclaim [all]`  
Will unclaim the chunk the player is currently in. If the first arguments equals "all", all chunks claimed by the team will be unclaimed.  
This action will appear in the team log.  
The player sending the message must have been given permission by the team leader (see team permissions section).  

`/claimslist`  
Will list the locations of all chunks claimed by the team.  

`/claimsmap`  
Displays a map with all nearby chunks. Those claimed by other teams are colored in red.  
  
`/claiminfo`  
Will return a link to this repository.  

<br>
  
# Team permissions  
The following permissions for members must be set when creating a team. It is not yet possible to change them without messing with the data storage system:  
  - invite new players  
  - kick players  
  - promote players / transfer leadership  
  - claim new chunks  
  - view the team log  

<br>
  
# Files  
All data is store in "claim_data.txt", except for the log files which are stored in "/team_logs/<team_name>/*.log".  

<br>
  
# Developer info  
Building the plugin can either be done by setting up a minecraft plugin development workspace for spigot 1.16, or by using the build file.  
To build it using the build.sh file, you will need the spigot-API for minecraft 1.19. You can either obtain it manually by running the [Buildtools](https://www.spigotmc.org/wiki/buildtools/) by Spigot, naming the API spigot-api.jar and putting it in the project's root (From my experience only the shaded version works).  
You can also run the build file with "--setup-env" and it will automatically install the BuildTools (if not present) and build the latest version of the API.  
Note that you may need to temporarily switch your Java version!  
  
From then on, you run the file normally, without any arguments and it will build a file named "Claim.jar"
