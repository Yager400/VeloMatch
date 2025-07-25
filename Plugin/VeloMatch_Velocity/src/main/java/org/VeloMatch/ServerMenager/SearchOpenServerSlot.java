package org.VeloMatch.ServerMenager;

import org.VeloMatch.JSONReader.JSON;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;

public class SearchOpenServerSlot {

    // Search a slot for the game to create

    public static String FindServerSlot(String PLFolder, int PlayerSize) {

        Path MainFolder = Paths.get(PLFolder);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        int TotalGameToCheck = maxPort - minPort;

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {

            boolean CanTheyEnter = false;

            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsOnline = GameInfo.getBoolean(GameNameNOW + ".online");
            boolean IsEnded = GameInfo.getBoolean(GameNameNOW + ".ended");
            boolean IsWaiting = GameInfo.getBoolean(GameNameNOW + ".waiting");
            int PlayerInServer = GameInfo.getInt(GameNameNOW + ".player");

            int OpenSlot = PlayerInServer + PlayerSize;

            if (OpenSlot <= Integer.parseInt(YamlRead("max-player"))) {
                CanTheyEnter = true;
            }

            if (!IsOnline && !IsEnded && CanTheyEnter && !IsWaiting) {

                GameInfo.set(GameNameNOW + ".online", true);
                GameInfo.save();

                return GameNameNOW;
            }
        }

        return "NOTFOUND";
    }

    // Find next server slot after a creation of a game

    public static int FindNextServerSlot(String PLFolder) {

        Path MainFolder = Paths.get(PLFolder);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        int TotalGameToCheck = maxPort - minPort;

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {

            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsOnline = GameInfo.getBoolean(GameNameNOW + ".online");
            boolean IsEnded = GameInfo.getBoolean(GameNameNOW + ".ended");
            boolean IsWaiting = GameInfo.getBoolean(GameNameNOW + ".waiting");


            if (!IsOnline && !IsEnded && !IsWaiting) {

                return GameCount + 1;
            }
        }

        // Tips so i don't forget this and you (devs) can actually use this part of the code
        // This is another for that redo the same thing as the one on top, but instead of
         // returning GameCount + 1 it will return just GameCount, so the program will be forced
         // to change even the first game and not skipping that, but realistically, this part is not good and
         // i hope that someone interested in this project will change this :)

        for (int GameCount = 0; GameCount <= TotalGameToCheck; GameCount++) {

            String GameNameNOW = YamlRead("prefix") + (GameCount + 1);

            boolean IsOnline = GameInfo.getBoolean(GameNameNOW + ".online");
            boolean IsEnded = GameInfo.getBoolean(GameNameNOW + ".ended");
            boolean IsWaiting = GameInfo.getBoolean(GameNameNOW + ".waiting");


            if (!IsOnline && !IsEnded && IsWaiting) {

                return GameCount;
            }
        }

        return 0;
    }
}
