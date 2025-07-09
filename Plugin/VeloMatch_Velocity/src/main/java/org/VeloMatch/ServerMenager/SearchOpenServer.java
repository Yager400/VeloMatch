package org.VeloMatch.ServerMenager;

import org.VeloMatch.JSONReader.JSON;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;

public class SearchOpenServer {

    // Search a server for a player/group of players to join

    public static String FindOpenServer(String PLFolder, int PlayerSize) {

        Path MainFolder = Paths.get(PLFolder);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        int TotalGameToCheck = maxPort - minPort;

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        // Part to calculate if the game are the last standing

        int calcLastGameNum = maxPort - minPort;

        //int LastGameToCheck = maxPort - minPort - calcLastGameNum;

        boolean changed = false;

        for (int LastGameCount = calcLastGameNum; LastGameCount <= (maxPort - minPort); LastGameCount++) {
            String LastGamesName = YamlRead("prefix") + LastGameCount;

            boolean CanTheyEnter = false;

            boolean IsOnline = GameInfo.getBoolean(LastGamesName + ".online");
            boolean IsEnded = GameInfo.getBoolean(LastGamesName + ".ended");
            boolean IsPrivate = GameInfo.getBoolean(LastGamesName + ".private");
            boolean IsLocked = GameInfo.getBoolean(LastGamesName + ".locked");
            int PlayerInServer = GameInfo.getInt(LastGamesName + ".player");

            int OpenSlot = PlayerInServer + PlayerSize;

            if (OpenSlot <= Integer.parseInt(YamlRead("max-player"))) {
                CanTheyEnter = true;
            }

            if (IsOnline && !IsEnded && !IsPrivate && CanTheyEnter && !IsLocked) {
                //Esegui altro codice
                if (!changed) {
                    for (int Count = 0; Count <= calcLastGameNum; Count++) {

                        String GameNameToChange = YamlRead("prefix") + (Count + 1);

                        GameInfo.set(GameNameToChange + ".waiting", false);

                        GameInfo.save();

                    }

                    changed = true;
                }


                return LastGamesName;
            }
        }

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {

            boolean CanTheyEnter = false;

            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsOnline = GameInfo.getBoolean(GameNameNOW + ".online");
            boolean IsEnded = GameInfo.getBoolean(GameNameNOW + ".ended");
            boolean IsPrivate = GameInfo.getBoolean(GameNameNOW + ".private");
            boolean IsLocked = GameInfo.getBoolean(GameNameNOW + ".locked");
            int PlayerInServer = GameInfo.getInt(GameNameNOW + ".player");

            int OpenSlot = PlayerInServer + PlayerSize;

            if (OpenSlot <= Integer.parseInt(YamlRead("max-player"))) {
                CanTheyEnter = true;
            }

            if (IsOnline && !IsEnded && !IsPrivate && CanTheyEnter && !IsLocked) {

                if (GameCount + 1 == calcLastGameNum - 2 - Integer.parseInt(YamlRead("minium-server-requirement"))) {
                    for (int LastGameCount = calcLastGameNum; LastGameCount <= (maxPort - minPort); LastGameCount++) {
                        String LastGamesName = YamlRead("prefix") + LastGameCount;

                        GameInfo.set(LastGamesName + ".online", false);
                        GameInfo.set(LastGamesName + ".locked", false);
                        GameInfo.set(LastGamesName + ".ended", false);
                        GameInfo.set(LastGamesName + ".private", false);
                        GameInfo.set(LastGamesName + ".empty", false);
                        GameInfo.set(LastGamesName + ".waiting", false);
                        GameInfo.set(LastGamesName + ".player", 0);

                        GameInfo.save();
                    }
                }

                return GameNameNOW;
            }
        }

        return "NOTFOUND";
    }
}
