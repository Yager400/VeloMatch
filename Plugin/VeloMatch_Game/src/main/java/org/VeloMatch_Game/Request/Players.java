package org.VeloMatch_Game.Request;

import org.VeloMatch_Game.JSONReader.JSON;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

import static org.VeloMatch_Game.Request.FindDir.FindVeloMatchDir;

public class Players {

    // Change the player that are in this server, so the proxy won't send any other one in this game in case of parties (this code can also break if a group of player join at the exact same tick of the game but is very rare considering a tick is 0.05 seconds)

    public static void PlayerRequest(File PLFolder, String GameName) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File DATAJSON = new File(DIR, "GameInfo.json");
        if (!DATAJSON.exists()) {
            throw new IOException("GameInfo.json not found in " + DATAJSON.getAbsolutePath());
        }


        JSON data = new JSON(DIR, "GameInfo.json");

        data.set(GameName + ".player", Bukkit.getOnlinePlayers().size());
        data.save();
        Bukkit.getLogger().warning("Data player salvato, percorso: " + DIR);

    }
}
