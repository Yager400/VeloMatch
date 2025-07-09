package org.VeloMatch_Game.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import static org.VeloMatch_Game.Request.FindDir.FindVeloMatchDir;

import java.io.File;
import java.io.IOException;

public class GetMinPort {

    // Get information from the config.yml (in the proxy plugin folder), in this case the minium port

    public static int getMinPort(File PLFolder) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File configFile = new File(DIR, "config.yml");
        if (!configFile.exists()) {
            throw new IOException("config.yml not found in " + configFile.getAbsolutePath());
        }


        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        int minPort = config.getInt("minPort");

        if (minPort <= 1) {
            throw new IOException("'minPort' is not a valid number, value: " + minPort);
        }
        return minPort;

    }



}
