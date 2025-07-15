package org.VeloMatch.ServerMenager;

import org.VeloMatch.JSONReader.JSON;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;

public class SearchEmptyServerSlot {

    /*
    public static String FindEmptyServerSlot(String PLFolder) {

        Path MainFolder = Paths.get(PLFolder);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        int TotalGameToCheck = maxPort - minPort;

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {

            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsOnline = GameInfo.getBoolean(GameNameNOW + ".online");
            boolean IsEnded = GameInfo.getBoolean(GameNameNOW + ".ended");
            boolean IsEmpty = GameInfo.getBoolean(GameNameNOW + ".empty");


            if (!IsOnline && !IsEnded && IsEmpty) {
                return GameNameNOW;
            }
        }

        return "NOTFOUND";
    }
    */


    // Count how many server there are with the variabile "empty"
    public static int CountEmptyServers(String PLFolder) {

        Path MainFolder = Paths.get(PLFolder);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        int TotalGameToCheck = maxPort - minPort;

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        int emptyCount = 0;

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {

            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsEmpty = GameInfo.getBoolean(GameNameNOW + ".empty");

            if (IsEmpty) {
                emptyCount++;
            }
        }

        return emptyCount;
    }

}
