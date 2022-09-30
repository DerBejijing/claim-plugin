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
