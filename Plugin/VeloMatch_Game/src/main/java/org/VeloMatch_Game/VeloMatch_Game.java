package org.VeloMatch_Game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.time.LocalTime;

import org.VeloMatch_Game.File.CreateFile;
import static org.VeloMatch_Game.File.GetMinPort.getMinPort;
import static org.VeloMatch_Game.File.GetPrefix.getPrefix;

import static org.VeloMatch_Game.Request.Players.PlayerRequest;
import static org.VeloMatch_Game.Request.Lock.LockRequest;
import static org.VeloMatch_Game.Request.UnLock.UnLockRequest;
import static org.VeloMatch_Game.Request.SetOnline.IsOnline;
import static org.VeloMatch_Game.Request.Ended.IsEnded;
import static org.VeloMatch_Game.File.GetMin_MaxPlayer.*;

public final class VeloMatch_Game extends JavaPlugin implements Listener {


    private CreateFile file;
    private GameSystem GS;

    private static VeloMatch_Game instance;

    private String GameName;

    @Override
    public void onEnable() {
        file = new CreateFile();
        GS = new GameSystem(this);

        instance = this;

        file.createFile(getDataFolder());

        getServer().getPluginManager().registerEvents(this, this);



        // Start to build the name
        // INFO: if the server port is lower than the minium port you will se a negative number, so the prefix "GAME-" can be "GAME--1" if this port is 25598 and the minium port is 25600

        try {
            // Get the minium port on the config.yml file
            int minPort = getMinPort(getDataFolder());

            // Get the prefix on the config.yml file
            String prefix = getPrefix(getDataFolder());

            // Get the port of this server
            int port = Bukkit.getServer().getPort();

            // Build the server name, the "+ 1" is for not creating a "GAME-0", so the lowest port will be "GAME-1"
            String WhoAmI = prefix + (port - minPort + 1);

            // Write the server name in the name.yml file
            file.nameConfig.set("myname", WhoAmI);
            file.saveName();

            GameName = WhoAmI;

            IsOnline(getDataFolder(), GameName);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public static VeloMatch_Game getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {

        // This will not be used, but it's a check for not breaking the plugin

        try {
            IsEnded(getDataFolder(), GameName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        String playerName = event.getPlayer().getName();
        PlayerRequest(getDataFolder(), GameName);


        // Menage timer before starting the game
        if (!file.nameConfig.getBoolean("started")) {
            if (Bukkit.getOnlinePlayers().size() == GetMaxPlayer(getDataFolder())) {

                // If the player inside the server are equals to the player limit (in the config.yml)

                LockRequest(getDataFolder(), GameName);

                GS.StartShortTimer();

                GS.StopLong();

            } else if (Bukkit.getOnlinePlayers().size() < GetMaxPlayer(getDataFolder()) && Bukkit.getOnlinePlayers().size() >= GetMinPlayer(getDataFolder())) {
                UnLockRequest(getDataFolder(), GameName);

                // If the players in the game are more than the required player limit but less than the maximum player

                GS.StartLongTimer();

                GS.StopShort();

            } else if (Bukkit.getOnlinePlayers().size() < GetMinPlayer(getDataFolder())) {
                UnLockRequest(getDataFolder(), GameName);

                // If the player are less than the requirement

                GS.StopShort();
                GS.StopLong();
            }
        }




    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws IOException {
        String playerName = event.getPlayer().getName();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {
                PlayerRequest(getDataFolder(), GameName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (Bukkit.getOnlinePlayers().size() == 0) {
                try {
                    IsEnded(getDataFolder(), GameName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                if (Bukkit.getOnlinePlayers().size() < GetMinPlayer(getDataFolder())) {
                    UnLockRequest(getDataFolder(), GameName);

                    // If the players in the server are less than the requirement

                    GS.StopShort();
                    GS.StopLong();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 20L);



    }




}
