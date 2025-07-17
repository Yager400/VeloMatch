package org.VeloMatch.ServerMenager;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GameListMenager {

    private final Map<String, Process> ProcessActiveNow = new HashMap<>();


    // Kill a specific game process by his name and remove it from the list
    public void KillProcess(String GameName) {
        Process process = ProcessActiveNow.get(GameName);
        if (process != null && process.isAlive()) {
            process.destroyForcibly();
        }
        ProcessActiveNow.remove(GameName);
    }

    // Start a game process and register the server in the proxy
    public void AddProcess(ProxyServer proxy, String GameName, String RAM, String ServerJAR, int port, String PLFolder) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "java",
                "-Xmx" + RAM,
                "-Xms" + RAM,
                "-jar",
                ServerJAR,
                "nogui"
        );

        
        builder.directory(new File(PLFolder + "active_Games/" + GameName));

        Process process = builder.start();
        ProcessActiveNow.put(GameName, process);

        ServerInfo serverInfo = new ServerInfo(GameName, new InetSocketAddress("127.0.0.1", port));
        proxy.registerServer(serverInfo);
    }

    public Process GetProcess(String gameName) {
        return ProcessActiveNow.get(gameName);
    }

    // Kill al the process that are in "ProcessActiveNow"
    public void ShutdownAllProcess() {

        System.out.println("Killing game process");

        for (Process process : ProcessActiveNow.values()) {
            process.destroyForcibly();

        }
    }
}
