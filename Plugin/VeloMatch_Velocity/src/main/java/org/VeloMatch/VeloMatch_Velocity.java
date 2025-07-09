package org.VeloMatch;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

import java.io.IOException;

// Request
import org.VeloMatch.Request.*;
import org.VeloMatch.Data.CreateFiles;
import static org.VeloMatch.GameInfo.ClearOldGames.ClearAll;
import org.VeloMatch.ServerMenager.GameListMenager;
import static org.VeloMatch.ServerMenager.MiniumServerChecker.StartCheckingMiniumServer;

@Plugin(id = "velomatch", name = "VeloMatch", version = "1.0", authors = {"Yager400"})
public class VeloMatch_Velocity {


    @Inject private Logger logger;
    @Inject private ProxyServer server;

    private static VeloMatch_Velocity instance;

    private MatchRequest Match;

    private GameListMenager GLM;

    // If you want to change this go in the "ReadFromYaml" and change that path with your name
    public String PLFolder = "plugins/velomatch/";


    @Inject
    public VeloMatch_Velocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }


    // Here you will need to put the same messaging channel that the bukkit server will use, this is the classic channel that the plugin
      // use, but make sure you change this if you want to use more than 1 velomatch system
    public static String channel = "matchmaking:channel";






    private static final MinecraftChannelIdentifier CHANNEL_ID = MinecraftChannelIdentifier.from(channel);

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) throws IOException {
        instance = this;

        server.getChannelRegistrar().register(CHANNEL_ID);

        server.getEventManager().register(this, new PluginMessageListener());

        System.out.println("Velomatch listening on channel -> " + channel);

        Match = new MatchRequest();
        ClearAll(PLFolder);

        GLM = new GameListMenager();

        CreateFiles.CreateBaseFile(PLFolder);


        // Start to check the empty server and if there are a few of them
        System.out.println("Started to checking the minium server");
        StartCheckingMiniumServer(server, this, GLM, PLFolder, logger);


    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {

        // Kill all the process started by the proxy
        GLM.ShutdownAllProcess();
    }

    /*
    public static VeloMatch_Velocity getInstance() {
        return instance;
    }

     */


    // See if the proxy receive a pluginMessage
    public class PluginMessageListener {
        @Subscribe
        public void onPluginMessage(PluginMessageEvent event) {

            if (!event.getIdentifier().getId().equals(channel)) return;
            if (!(event.getSource() instanceof ServerConnection connection)) return;

            try {
                ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
                boolean solo = true;



                // Get the SubChannel
                String subChannel = in.readUTF();

                // Read the subchannel and make sure that the request is ok
                if (subChannel.equals("MatchRequest")) {

                    // Start managing the request
                    Match.Request(in, solo, logger, server, PLFolder, GLM);
                }

                else {
                    logger.warn("Subchannel not found in the channel " + channel + " , please make sure that the request is ok");
                }


            } catch (Exception e) {
                logger.error("Error managing the request, make sure that this is the right channel: " + channel + "\nMore info about the error: " + e);
            }
        }
    }
}
