package org.VeloMatch_Game;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

import static org.VeloMatch_Game.File.GetTimerTime.*;

public class GameSystem {

    // Create and cancel the timer for changing the state of the "started" variabile on the name.yml file

    private BukkitTask ShortTimer;
    private BukkitTask LongTimer;

    private final JavaPlugin plugin;

    private FileConfiguration config;
    private File nameFile;

    public GameSystem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void StartShortTimer() throws IOException {
        ShortTimer = plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

            // StartGame
            nameFile = new File(plugin.getDataFolder(), "name.yml");
            config = YamlConfiguration.loadConfiguration(nameFile);

            config.set("started", true);
            try {
                config.save(nameFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Bukkit.broadcastMessage("Game Started");

        }, 20L * GetShortTimer(plugin.getDataFolder()));
    }

    public void StartLongTimer() throws IOException {
        LongTimer = plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

            // StartGame
            nameFile = new File(plugin.getDataFolder(), "name.yml");
            config = YamlConfiguration.loadConfiguration(nameFile);

            config.set("started", true);
            try {
                config.save(nameFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //Bukkit.broadcastMessage("Game Started");

        }, 20L * GetLongTimer(plugin.getDataFolder()));
    }

    public void StopShort() {
        ShortTimer.cancel();
    }

    public void StopLong() {
        LongTimer.cancel();
    }
}
