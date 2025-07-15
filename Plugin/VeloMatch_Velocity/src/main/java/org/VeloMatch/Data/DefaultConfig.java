package org.VeloMatch.Data;

public class DefaultConfig {

    // Just return the default configuration


    public static String BaseConfig() {
        String defaultConfig = """
        # Hi, this is the main config of VeloMatch, you only need to put here simple values
        
        # INFO: this will be translated and corrected with chatgpt because i didn't know how to write in english good because i don't speak it, but i hope you like my plugin :)
        
        
        # If debug is set on true velomatch will show a few information, like if the proxy received the player matchmaking request, if the empty game are created and if it happen a few error
        DEBUG: true
        
        # WARNING: make sure that every port between them is open
        # Here you are going to put the minium port, this will be the lowest port that VeloMatch can use, it will not go under the port 25600 (default config)
        minPort: 25600
        
        # Same thing as the minium port but this time with the last port, so VeloMatch will not go over the port 26000 and will restart form the minPort if it reaches the maxium port
        maxPort: 26000
        
        
        # This will be the prefix of the game, followed by the number (Change only the prefix)
        prefix: "GAME-"
        
        
        # 256M (0.25 GB) --> 0-1
        # 512M (0.5 GB) --> 1-2
        # 1024M (1 GB) --> 5-10
        # 2048M (2 GB) --> 10-15
        # 4096M (4 GB) --> 20-40
        # 8192M (8 GB) --> 40-80
        # 16384M (16 GB) --> 80-150
        # 32768M (32 GB) --> 150-200
        
        # Please note that I can't test this information because I don't have a community or 32GB of RAM for a server, so I
        # asked ChatGPT for the average max players per amount of RAM using Purpur. I don't know if this information is true
        # or false, but I recommend using 2GB for games like solo BedWars (around 8 players) and 4GB for gamemodes like Hide 
        # and Seek or BedWars Duo, Trio, or Squad (12–16 players)
        # INFO: please note that the cpu can became the bottleneck and even if you use 256G of ram per server the maximum will be around 200
        
        # Please select your ram-per server in Mb format
        gameRAM: "2048M"
        
        # This will be the server software name, i think that purpur is the best but you can change it
        server-software-name: "purpur.jar"
        
        # This is important, how many server velomatch will need to have to work, if it has less server than this number it will
        # create some game so a player that join will not wait 6 seconds for the server to startup
        # WARNING: this can significantly reduce the amount of ram in your system for empty game, make sure to set this number low
        # INFO: if you set this to 2 you will not have the debug problem in the console, but please never make this at 0 or players will not be able to join in the games
        minium-server-requirement: 3
        
        
        # This is the time between every check for the minium server, i think that 10 seconds is the best
        
        # WARNING: under 10 seconds the plugin can glitch if your storage isn't super fast because it need to copy an entire server to another folder (around 150-200MB), 
        # so it depends on how fast your nvme is but mine is 3000M/s 
        # and it fail at the last server so with an hdd you will need to wait 20/25 seconds or use a void world                                                   
        
        # INFO: if you are thinking that this can slow down your server, it won't, probably only a server with 5.000 member can but 10 seconds is fine
        time-between-check: 10
        
       
        # This is the max player per-game count
        max-player: 8
        
        # This is the minium player required to start a game, when the player count hit this goal the game can start a longer timer
        required-player: 6
        
        
        # This will be the short timer, when the server reaches the "max-player" count it will start a timer, this number will be how long it will be
        # INFO: Those timer are only for telling the proxy that the game is started, and i won't use it for a gamemode
        short-timer: 10
        
        # This will be the long timer, when the server reaches the "required-player" count it will start a timer, this number will be how long it will be
        long-timer: 30
        
        
        # MESSAGE
        
        # When 0 server are open
        ErrorJoiningGameMessage: "Sorry but for now any server is full or we can't create any server, retry in a few seconds/minutes"
        """;

        return defaultConfig;
    }
}
