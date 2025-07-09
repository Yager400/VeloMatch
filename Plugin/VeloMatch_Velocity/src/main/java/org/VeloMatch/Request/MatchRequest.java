package org.VeloMatch.Request;

import com.google.common.io.ByteArrayDataInput;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.VeloMatch.JSONReader.JSON;
import org.VeloMatch.ServerMenager.GameListMenager;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;
import static org.VeloMatch.ServerMenager.SearchOpenServer.FindOpenServer;

public class MatchRequest {

    // Menage the player's request

    public void Request(ByteArrayDataInput in, boolean solo, Logger logger, ProxyServer server, String PLFolder, GameListMenager GLM) throws IOException {
        String type = in.readUTF();


        if (!type.equals("solo")) {
            solo = false;
        }


        List<String> playerNames = new ArrayList<>();


        if (solo) {
            String playerName = in.readUTF();
            playerNames.add(playerName);
        } else {
            int playerCount = in.readInt();
            for (int i = 0; i < playerCount; i++) {
                playerNames.add(in.readUTF());
            }
        }




        for (String playerName : playerNames) {
            Optional<Player> optionalPlayer = server.getPlayer(playerName);
            if (optionalPlayer.isEmpty()) {
                logger.warn("Warning: player not found: " + playerName);
                continue;
            }

            if (Boolean.parseBoolean(YamlRead("DEBUG"))) {
                Player player = optionalPlayer.get();
                player.sendMessage(Component.text("Request received, the selected player is: §6" + playerName + "\n§cType: §6" + type, NamedTextColor.GREEN));
            }



        }

        int PartySize = playerNames.size();

        String ServerNameFound = FindOpenServer(PLFolder, PartySize);

        if (!ServerNameFound.equals("NOTFOUND")) {

            // Teleport all the player found in the list "playerNames" in the founded game

            for (String playerName : playerNames) {
                Optional<Player> optionalPlayer = server.getPlayer(playerName);
                if (optionalPlayer.isEmpty()) {
                    logger.warn("Warning: player not found: " + playerName);
                    continue;
                }

                Path MainFolder = Paths.get(PLFolder);

                JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

                GameInfo.set(ServerNameFound + ".empty", false);
                GameInfo.save();

                Player player = optionalPlayer.get();
                Optional<RegisteredServer> serverOpt = server.getServer(ServerNameFound);
                player.createConnectionRequest(serverOpt.get()).connect();




            }


        } else {

            // If no server can ospitate the player/players

            for (String playerName : playerNames) {
                Optional<Player> optionalPlayer = server.getPlayer(playerName);
                if (optionalPlayer.isEmpty()) {
                    logger.warn("Warning: player not found: " + playerName);
                    continue;
                }

                Player player = optionalPlayer.get();
                player.sendMessage(Component.text(YamlRead("ErrorJoiningGameMessage"), NamedTextColor.RED));


            }
        }


    }

}
