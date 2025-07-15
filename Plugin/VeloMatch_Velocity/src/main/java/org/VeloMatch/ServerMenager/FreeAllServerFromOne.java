package org.VeloMatch.ServerMenager;

import org.VeloMatch.JSONReader.JSON;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;

public class FreeAllServerFromOne {

    // Yeah bad name, and also, this void is bad and not optimized, but this it was the only way i can do it

    // Start from a server, and close any game over that one

    public static void WriteFromSpecificServer(String PLFolder, int You) {

        Path MainFolder = Paths.get(PLFolder);

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        String prefix = YamlRead("prefix");


        //int count = WhereToStart;

        for (int count = You; count <= maxPort - minPort; count++) {

            if (GameInfo.getBoolean(prefix + count + ".online") && GameInfo.getBoolean(prefix + count + ".empty")) {
                GameInfo.set(prefix + count + ".online", true);
                GameInfo.set(prefix + count + ".locked", false);
                GameInfo.set(prefix + count + ".ended", true);
                GameInfo.set(prefix + count + ".private", false);
                GameInfo.set(prefix + count + ".empty", false);
                GameInfo.set(prefix + count + ".waiting", false);

            }


            count++;
        }

        GameInfo.save();



    }


}
