package org.VeloMatch.ServerMenager;

import org.VeloMatch.JSONReader.JSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;

public class GetAllGamesToDelete {

    // Find the game that are going to get deleted

    public static List<String> FindGameToDelete(String PLFolder) {

        Path MainFolder = Paths.get(PLFolder);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        int TotalGameToCheck = maxPort - minPort;

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        List<String> validServers = new ArrayList<>();

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {


            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsOnline = GameInfo.getBoolean(GameNameNOW + ".online");
            boolean IsEnded = GameInfo.getBoolean(GameNameNOW + ".ended");
            int PlayerInServer = GameInfo.getInt(GameNameNOW + ".player");


            if (IsOnline && IsEnded && PlayerInServer == 0) {
                validServers.add(GameNameNOW);
            }
        }

        return validServers;
    }
}
