package org.VeloMatch_Game.Request;

import org.VeloMatch_Game.JSONReader.JSON;

import java.io.File;
import java.io.IOException;

import static org.VeloMatch_Game.Request.FindDir.FindVeloMatchDir;

public class Ended {

    // This is for telling the proxy that the game on this server is ended, this is important so the proxy can free a server slot

    public static void IsEnded(File PLFolder, String GameName) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File DATAJSON = new File(DIR, "GameInfo.json");
        if (!DATAJSON.exists()) {
            throw new IOException("GameInfo.json not found in " + DATAJSON.getAbsolutePath());
        }


        JSON data = new JSON(DIR, "GameInfo.json");

        data.set(GameName + ".ended", true);
        data.save();
    }
}
