package org.VeloMatch_Game.Request;

import org.VeloMatch_Game.JSONReader.JSON;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static org.VeloMatch_Game.Request.FindDir.FindVeloMatchDir;

public class Lock {

    // This will change the state of the accessibility of the server, in this case it will block it

    public static void LockRequest(File PLFolder, String GameName) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File DATAJSON = new File(DIR, "GameInfo.json");
        if (!DATAJSON.exists()) {
            throw new IOException("GameInfo.json not found in " + DATAJSON.getAbsolutePath());
        }


        JSON data = new JSON(DIR, "GameInfo.json");

        data.set(GameName + ".locked", true);
        data.save();
    }


}
