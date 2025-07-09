package org.VeloMatch_Game.Request;

import org.VeloMatch_Game.JSONReader.JSON;

import java.io.File;
import java.io.IOException;

import static org.VeloMatch_Game.Request.FindDir.FindVeloMatchDir;

public class SetOnline {

    // This is for telling the proxy if this server is online or not, so it won't use the same port and the same name

    public static void IsOnline(File PLFolder, String GameName) throws IOException {
        File DIR = FindVeloMatchDir(PLFolder);
        if (DIR == null) {
            throw new IOException("Folder 'velomatch' not found from " + PLFolder.getAbsolutePath());
        }

        File DATAJSON = new File(DIR, "GameInfo.json");
        if (!DATAJSON.exists()) {
            throw new IOException("GameInfo.json not found in " + DATAJSON.getAbsolutePath());
        }


        JSON data = new JSON(DIR, "GameInfo.json");

        data.set(GameName + ".online", true);
        data.save();
    }

}
