package org.VeloMatch.GameInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class ClearOldGames {

    // Delete all the game's folders in "active_Games"

    public static void ClearAll(String pluginFolderPath) {
        Path dir = Paths.get(pluginFolderPath + "/active_Games");

        if (!Files.exists(dir)) {
            return;
        }

        try {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {

                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void CrearTarget(String pluginFolderPath, String GameName) {
        Path dir = Paths.get(pluginFolderPath + "/active_Games/" + GameName);

        try {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */
}
