package org.VeloMatch_Game.Request;

import java.io.File;

public class FindDir {

    // A simple algorithm to go down from path to reach the proxy plugin folder

    public static File FindVeloMatchDir(File start) {
        String FullPath = start.getAbsolutePath();

        String[] parts = FullPath.split("active_Games");


        if (parts.length > 0) {
            String basePath = parts[0];
            return new File(basePath);
        } else {
            return null;
        }
    }


}
