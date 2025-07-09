package org.VeloMatch_Game.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateFile {

    // Create the name.yml file for storing the game name and it it's started or not

    private File nameFile;
    public FileConfiguration nameConfig;

    public void createFile(File PLFolder) {

        nameFile = new File(PLFolder, "name.yml");
        if (!nameFile.exists()) {
            try {

                nameFile.getParentFile().mkdirs();

                nameFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        nameConfig = YamlConfiguration.loadConfiguration(nameFile);
        nameConfig.set("myname", "none");
        nameConfig.set("started", false);
        saveName();
    }

    public void saveName() {
        try {
            nameConfig.save(nameFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
