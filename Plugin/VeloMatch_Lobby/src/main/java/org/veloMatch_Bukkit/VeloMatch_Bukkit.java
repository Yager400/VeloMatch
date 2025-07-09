package org.veloMatch_Bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class VeloMatch_Bukkit extends JavaPlugin implements CommandExecutor {


    // PLEASE READ TTHIS
    // This bukkit plugin is meant to be used only for testing, please add this code in
      // your main lobby plugin or just add a command that call this void "SendMatchmakingRequest"




    @Override
    public void onEnable() {

        // Register the matchmaking channel, make sure that no other plugin are using this channel
        // If yes make sure to change the channel here and the velocity plugin
        getServer().getMessenger().registerOutgoingPluginChannel(this, "matchmaking:channel");

        //
        getCommand("matchmaking").setExecutor(this);

    }


    // Void for sending the player username to the proxy, this void is very important

    // This plugin for now doesn't have a party system, but it can support it, for using that you need to set the 'solo boolean' on 'false', and then send a list with more that 1 player
    // This is an example for using party:
    // SendMatchmakingRequest(List.of(leader, member1, member2, member3 ect...), false);
    //
    // Or if you have already a list you can send this
    // List<Player> TestList = new ArrayList<>();
    // SendMatchmakingRequest(TestList, false);
    public void SendMatchmakingRequest(List<Player> player, boolean solo) {

        if (player == null || player.isEmpty()) {
            throw new IllegalArgumentException("Error: player list is empty");
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("MatchRequest");
        out.writeUTF(solo ? "solo" : "team");

        if (solo) {
            out.writeUTF(player.get(0).getName());
        } else {
            out.writeInt(player.size());
            for (Player p : player) {
                out.writeUTF(p.getName());
            }
        }


        player.get(0).sendPluginMessage(this, "matchmaking:channel", out.toByteArray());
    }


    //This is just a testing command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("matchmaking")) {



            if (sender instanceof Player) {
                Player player = (Player) sender;


                SendMatchmakingRequest(List.of(player), true);

                player.sendMessage("§cHi, if you are reading this message, probably your proxy is slow or the plugin doesn't work properly\nMake sure that everything is ok and the proxy can find the server template\nIf you don't want so see this message use the basic version instead of the debug one");
            }



            return true;
        }



        return false;
    }
}
