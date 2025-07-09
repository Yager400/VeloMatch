package org.VeloMatch.ServerMenager;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import net.kyori.adventure.text.format.NamedTextColor;
import org.VeloMatch.JSONReader.JSON;
import org.VeloMatch.VeloMatch_Velocity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

import static org.VeloMatch.ServerMenager.FreeAllServerFromOne.WriteFromSpecificServer;
import static org.VeloMatch.ServerMenager.PortMenager.ChangeServerPort;
import static org.VeloMatch.ServerMenager.PortMenager.GetPortForServer;
//import static org.VeloMatch.ServerMenager.SearchEmptyServerSlot.FindEmptyServerSlot;
import static org.VeloMatch.ServerMenager.SearchOpenServerSlot.FindNextServerSlot;
import static org.VeloMatch.ServerMenager.SearchOpenServerSlot.FindServerSlot;
//import static org.VeloMatch.ServerMenager.ServerNumber.GetTotalGameNumber;
import static org.VeloMatch.Data.ReadFromYaml.YamlRead;
import static org.VeloMatch.ServerMenager.ServerTemplateMenager.DeleteGameFromFolder;
import static org.VeloMatch.ServerMenager.ServerTemplateMenager.GenerateServerTemplate;
import static org.VeloMatch.ServerMenager.SearchEmptyServerSlot.CountEmptyServers;
import static org.VeloMatch.ServerMenager.GetAllGamesToDelete.FindGameToDelete;

public class MiniumServerChecker {

    // This is the checker for the empty server and server to delete

    public static void StartCheckingMiniumServer(ProxyServer server, VeloMatch_Velocity plugin, GameListMenager GLM, String PLFolder, Logger logger) {

        int SecondsForEveryUpdate = Integer.parseInt(YamlRead("time-between-check"));

        Path MainFolder = Paths.get(PLFolder);


        server.getScheduler().buildTask(plugin, () -> {

            //int TotalServerNumber = GetTotalGameNumber();

            JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

            int ServerMiniumOnYaml = Integer.parseInt(YamlRead("minium-server-requirement"));


            List<String> GameToDelete = FindGameToDelete(PLFolder);

            for (String GameSelectedAndInList : GameToDelete) {

                int port = GameInfo.getInt(GameSelectedAndInList + ".port");

                ServerInfo serverInfo = new ServerInfo(GameSelectedAndInList, new InetSocketAddress("127.0.0.1", port));
                server.unregisterServer(serverInfo);

                GLM.KillProcess(GameSelectedAndInList);

                DeleteGameFromFolder(GameSelectedAndInList, PLFolder);

                GameInfo.set(GameSelectedAndInList + ".waiting", true);
                GameInfo.set(GameSelectedAndInList + ".ended", false);
                GameInfo.set(GameSelectedAndInList + ".online", false);
                GameInfo.save();
            }


            if (CountEmptyServers(PLFolder) < ServerMiniumOnYaml) {

                String ServerToCreate = FindServerSlot(PLFolder, 0);

                try {
                    GenerateServerTemplate(ServerToCreate, PLFolder);

                    int port = GetPortForServer(ServerToCreate, PLFolder);

                    ChangeServerPort(ServerToCreate, port, PLFolder);

                    GameInfo.set(ServerToCreate + ".empty", true);
                    GameInfo.set(ServerToCreate + ".online", true);
                    GameInfo.set(ServerToCreate + ".waiting", false);
                    GameInfo.save();

                    /*
                    if (ServerToCreate.equals(YamlRead("prefix") + 1)) {

                    }

                     */

                    GLM.AddProcess(server, ServerToCreate, YamlRead("gameRAM"), YamlRead("server-software-name"), port, PLFolder);

                    if (FindNextServerSlot(PLFolder) == 0) {
                        logger.error("CRITICAL ERROR: VELOMATCH CAN'T CREATE MORE GAME BECAUSE THE MAXUMIM PORT IS TO LOW, PLEASE ADD MORE NUMBER IN THE PARAM 'maxPort' IN THE 'config.yml' AND RESTART THE PROXY");
                    } else {
                        String nextGameName = YamlRead("prefix") + FindNextServerSlot(PLFolder);

                        System.out.println("Next game from " + ServerToCreate + "is " + nextGameName);

                        if (GameInfo.getBoolean(nextGameName + ".waiting")) {
                            GameInfo.set(nextGameName + ".waiting", false);
                            GameInfo.save();
                        }

                        WriteFromSpecificServer(PLFolder, FindNextServerSlot(PLFolder));


                    }

                } catch (RuntimeException | IOException e) {
                    throw new RuntimeException(e + "\n\n\n\n\n---------------------------------------\n\nPlease note that this is a bug that i can't resolve, this is happen when your drive is slow and the 'time-between-check' is low, so don't worry, it's not a critical error and the plugin will still works fine\n\n---------------------------------------\n\n\n\n\n");

                }

                System.out.println(ServerToCreate + " created");

            }





        }).repeat(SecondsForEveryUpdate, TimeUnit.SECONDS).schedule();
    }


}
