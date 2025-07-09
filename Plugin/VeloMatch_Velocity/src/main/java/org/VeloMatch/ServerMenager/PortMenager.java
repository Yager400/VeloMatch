package org.VeloMatch.ServerMenager;

import org.VeloMatch.JSONReader.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PortMenager {

    // This is the game port menager

    // Get the server port from the json file
    public static int GetPortForServer(String GameName, String PLFolder) {

        Path MainFolder = Paths.get(PLFolder);

        JSON GameInfo = new JSON(MainFolder.toFile(), "GameInfo.json");

        int port = GameInfo.getInt(GameName + ".port");



        return port;
    }

    // Change the game port in the server.properties file
    public static void ChangeServerPort(String GameName, int port, String PLFolder) {
        File serverProperties = new File(PLFolder + "active_Games/"+ GameName +"/server.properties");

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(serverProperties)) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        props.setProperty("server-port", String.valueOf(port));

        try (FileOutputStream out = new FileOutputStream(serverProperties)) {
            props.store(out, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
