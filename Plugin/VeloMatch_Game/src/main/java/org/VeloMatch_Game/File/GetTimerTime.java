package org.VeloMatch_Game.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static org.VeloMatch_Game.Request.FindDir.FindVeloMatchDir;

public class GetTimerTime {

    // Get information from the config.yml (in the proxy plugin folder), in this case the timer's info

    public static int GetShortTimer(File PLFolder) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File configFile = new File(DIR, "config.yml");
        if (!configFile.exists()) {
            throw new IOException("config.yml not found in " + configFile.getAbsolutePath());
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        int ShortTimer = config.getInt("short-timer");

        return ShortTimer;

    }

    public static int GetLongTimer(File PLFolder) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File configFile = new File(DIR, "config.yml");
        if (!configFile.exists()) {
            throw new IOException("config.yml not found in " + configFile.getAbsolutePath());
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        int LongTimer = config.getInt("long-timer");

        return LongTimer;

    }
}
