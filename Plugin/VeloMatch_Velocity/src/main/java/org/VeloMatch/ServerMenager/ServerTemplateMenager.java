package org.VeloMatch.ServerMenager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class ServerTemplateMenager {

    // Server template menager

    // Get a random server template (map) and copy it in the active_Games folder
    public static void GenerateServerTemplate(String GameName, String PLFolder) {
        Path templatesDir = Paths.get(PLFolder + "templates");
        Path activeGamesDir = Paths.get(PLFolder + "active_Games");

        File[] folders = templatesDir.toFile().listFiles(File::isDirectory);
        if (folders == null || folders.length == 0) {
            System.out.println("No templates found in: " + templatesDir);
            return;
        }

        Random random = new Random();
        File randomFolder = folders[random.nextInt(folders.length)];



        Path destDir = activeGamesDir.resolve(GameName);

        try {
            Files.walk(randomFolder.toPath())
                    .forEach(source -> {
                        Path destination = destDir.resolve(randomFolder.toPath().relativize(source));
                        try {
                            if (Files.isDirectory(source)) {
                                if (!Files.exists(destination)) {
                                    Files.createDirectories(destination);
                                }
                            } else {
                                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException("");
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    // Delete a server from the active_Games folder
    public static void DeleteGameFromFolder(String GameName, String PLFolder) {
        Path folderToDelete = Paths.get(PLFolder + "active_Games", GameName);

        if (!Files.exists(folderToDelete)) {
            throw new RuntimeException("Game not found: " + folderToDelete);
        }

        try {
            Files.walk(folderToDelete)
                    .sorted((p1, p2) -> p2.compareTo(p1))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException("Error while deleting the game: " + e.getMessage(), e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
