package dk.bondegaard.achievements.playerdata;

import dk.bondegaard.achievements.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class APlayer {

    // Variables
    private final Player player;

    private FileConfiguration data;
    private long lastSave = System.currentTimeMillis() - 10000;


    // Constructor
    public APlayer(Player player) {
        this.player = player;
    }

    // Methods
    public APlayer load() {
        //Creates the folder Achivements/playerdata/ if it doesn't exist
        File folder = new File(Main.getInstance().getDataFolder(), "playerdata");
        folder.mkdirs();

        //Creates the unique data file if it doesn't exist
        File dataFile = new File(folder, player.getUniqueId() + ".yml");
        if (!dataFile.exists()) try {
            dataFile.createNewFile();
        } catch (IOException ex) {
        }

        //Loads the file as a bukkit config
        this.data = YamlConfiguration.loadConfiguration(dataFile);
        return this;
    }

    public void save() {
        save(false);
    }

    public void save(boolean force) {
        if (!force && System.currentTimeMillis() - lastSave < 10000) return; // Prevent oversaving (lag prevention)
        lastSave = System.currentTimeMillis();
        File folder = new File(Main.getInstance().getDataFolder(), "playerdata");
        File dataFile = new File(folder, player.getUniqueId() + ".yml");
        try {
            data.save(dataFile);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    // Getters
    public Player getPlayer() {
        return player;
    }

    public FileConfiguration getData() {
        return data;
    }

    // Setters
}
