package org.VeloMatch.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.VeloMatch.Data.DefaultConfig.BaseConfig;
import static org.VeloMatch.GameInfo.WriteAllPort.Write;

public class CreateFiles {

    // Create base file, like the folders, the config file, the json file, and write on the .json and .yml file

    public static void CreateBaseFile(String pluginFolderPath) throws IOException {
        Path pluginFolder = Paths.get(pluginFolderPath);
        Path templatesFolder = pluginFolder.resolve("templates");
        Path activeGamesFolder = pluginFolder.resolve("active_Games");
        Path configFile = pluginFolder.resolve("config.yml");
        Path GameInfo = pluginFolder.resolve("GameInfo.json");

        // Create the 'map folder' for the server template
        if (!Files.exists(templatesFolder)) {
            Files.createDirectories(templatesFolder);
            System.out.println("Folder created: " + templatesFolder);
        }


        if (!Files.exists(activeGamesFolder)) {
            Files.createDirectories(activeGamesFolder);
            System.out.println("Folder created: " + activeGamesFolder);
        }

        if (!Files.exists(configFile)) {
            Files.writeString(configFile, BaseConfig());
        }

        if(!Files.exists(GameInfo)) {
            Files.writeString(GameInfo, "{}");
        } else {
            Files.write(
                    Paths.get(pluginFolderPath + "GameInfo.json"),
                    "{}".getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        }

        Write("GameInfo.json", pluginFolder.toFile());
    }


}
