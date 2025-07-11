# VeloMatch

<div align="center">

![VeloMatch full logo](https://cdn.modrinth.com/data/cached_images/b10a78530f049e4a6ae68b605acf734ab2c94e82.png)

</div>

Hi, this is VeloMatch, a matchmaking system plugin for proxys and servers, make sure to read all of this file and the INSTALL.md for setting up this plugin, now the presentation<br><br>
Let's start with the first thing, ***what is VeloMatch***: <br>
 - VeloMatch is a free and open-source plugin for small/medium servers that want to make a difference, how? By creating gamemodes that don't have the maximum player cap of every server software (for Paper it is around 150/200) and using multiple servers

Why do I need to choose this instead of other matchmaking systems? <br>
- I have made VeloMatch for small/medium servers that want to innovate and grow, if you expect a community of around 100 players I don't think that VeloMatch is right for you, but if you aim to become a big server network, VeloMatch is the right choice

Is it easy to install?
- Yeah, when I made this plugin I wanted it to be as much configure-friendly as possible, VeloMatch is (basically) a drag-and-drop plugin, yeah you need to change a few things like the port range and make a server template (go to the INSTALL.md to see that), but VeloMatch_Game and VeloMatch_Lobby are fully drag-and-drop

Do I need top hardware for this?
- Well it depends, if you are hosting like 2000 simultaneous players in your gamemode, you will need a lot of cores, threads and RAM, but if you are hosting a server with around 500 players, just a workbench CPU with around 32 threads will be fine

Can VeloMatch manage games on multiple dedicated servers?
- Unfortunately VeloMatch can manage and create games only on one machine

Where can VeloMatch run?
- I tested this on my PC and it can run very well, but if you are using Pterodactyl make sure to set a lot of RAM for the proxy because this plugin doesn't create a container for every server but it runs them with a limit of RAM, if you are hosting your network on a server without a panel, VeloMatch will work even if your proxy has 1GB of RAM as limit

Problems?
— Unfortunately, yes. Probably due to bad coding in that part, on the last 2 servers the plugin will wait around 3–4 seconds before letting a player join. Why? Because when VeloMatch needs to reset the games, it starts recreating games from the first port. Because of this, on the last game it will take about 3–4 seconds to let players join. However, if you use around 400 ports, this won’t be a noticeable problem.
