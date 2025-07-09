package org.VeloMatch.GameInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.VeloMatch.Data.ReadFromYaml.YamlRead;
import org.VeloMatch.JSONReader.JSON;

public class WriteAllPort {

    // Write on the json file every server, so the proxy and game server can communicate

    public static void Write(String fileName, File PLFolder) {



        JSON GameInfo = new JSON(PLFolder, fileName);

        int minPort = Integer.parseInt(YamlRead("minPort"));
        int maxPort = Integer.parseInt(YamlRead("maxPort"));

        String prefix = YamlRead("prefix");


        int count = 1;

        for (int selectedPort = minPort; selectedPort <= maxPort; selectedPort++) {

            GameInfo.set(prefix + count + ".online", false);
            GameInfo.set(prefix + count + ".locked", false);
            GameInfo.set(prefix + count + ".ended", false);
            GameInfo.set(prefix + count + ".private", false);
            GameInfo.set(prefix + count + ".empty", false);
            GameInfo.set(prefix + count + ".waiting", false);
            GameInfo.set(prefix + count + ".port", minPort + count - 1);
            GameInfo.set(prefix + count + ".player", 0);

            count++;
        }

        GameInfo.save();
    }
}
