# Football-Stats

Request Mapping : /footstats/standings/{countryName}/{leagueName}/{teamName}
All the three path variables are mandatory.

Deployed into PCF :

PCF URL to test : 

https://football-standings-stats.cfapps.io/footstats/standings/{countryName}/{leagueName}/{teamName}

example : https://football-standings-stats.cfapps.io/footstats/standings/England/Premier League/Manchester City

Response :

{
"41": "England",
"148": "Premier League",
"2626": "Manchester City",
"overall_league_position": "6"
}

