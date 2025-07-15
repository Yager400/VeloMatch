# INSTALL

### The start  
To start, you need to put the VeloMatch_Velocity plugin into your proxy’s plugin folder and start the proxy. It will give you an error, but that’s okay — just turn off the proxy and continue.

### Configuration  
Open the ***config.yml*** file and check if everything is set the way you want. Make sure the port range is correct, you like the prefix, the amount of RAM per game, the server software name, and the other settings.

### Templates  
For the templates, you need to copy an entire server into it. How? Go to "https://purpurmc.org/download/purpur", download the jar file, and start it. Accept the EULA, and **connect that server to your proxy like a normal paper/purpur server**, then put the **VeloMatch_Game plugin into the server’s plugin folder**. From then on, you just need to drag-and-drop the server’s folder into the templates folder.

### Lobby  
Register a server in the proxy and name it whatever you want, then drag-and-drop the VeloMatch_Lobby plugin into that server’s plugin folder.

### DEBUG  
If you are testing, debug mode can give you useful info, like whether requests reach the proxy. For a final product, turn debug off in the config.yml file.

### Other files  
Please **do NOT touch the GameInfo.json or the active_Games folder** — these are critical files used by the proxy and the game to communicate and verify things. If you accidentally change something here, delete the file or folder and restart your proxy.

### Tutorial and support  
For support, you can go to [github](https://github.com/Yager400/VeloMatch/issues) and post your problem to get help. Or, if you don’t want to open an issue on GitHub, you can go here on [discord](https://discord.gg/nsSuW87aMx) to get help from staffer or open a discussion

