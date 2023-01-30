package dk.bondegaard.achivements.achivements;

import dk.bondegaard.achivements.Main;
import dk.bondegaard.achivements.achivements.reward.Reward;
import dk.bondegaard.achivements.achivements.reward.RewardType;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class AchivementsHandler {

    private final List<Achivements> achivements = new ArrayList<>();
    private FileConfiguration data;

    public AchivementsHandler load() {
        achivements.clear();
        loadConfig();

        if (!data.contains("achivements")) data.createSection("achivements");

        for (String key : data.getConfigurationSection("achivements").getKeys(false)) {
            ConfigurationSection section = data.getConfigurationSection("achivements." + key);
            if (isAchievement(key)) {
                Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement " + key + ": Achivement name is already used!");
                continue;
            }

            // ############ //
            //  LOAD TYPE   //
            // ############ //
            if (!section.contains("type")) continue;
            AchivementsType type = AchivementsType.UNKNOWN;
            try {
                type = AchivementsType.valueOf(section.getString("type").toUpperCase());
            } catch (Exception ex) {
                Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement " + key + ": Invalid type in config");
            }

            //  LOAD AMOUNT  //
            long amount = section.contains("amount") ? section.getLong("amount") : 1; // DEFAULT = 1
            // ############### //
            //  LOAD MATERIAL  //
            // ############### //
            Material material = Material.AIR;
            try {
                material = section.contains("itemType") ? Material.getMaterial(section.getString("itemType")) : null; // DEFAULT = null
            } catch (Exception ex) {
                Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement " + key + ": Invalid itemType in config");
            }

            // ############## //
            //  LOAD REWARDS  //
            // ############## //

            List<Reward> rewards = new ArrayList<>();
            if (section.contains("rewards")) {
                for (String rewardsKey : section.getConfigurationSection("rewards").getKeys(false)) {
                    ConfigurationSection rewardSection = section.getConfigurationSection("rewards." + rewardsKey);
                    // ############ //
                    //  LOAD TYPE   //
                    // ############ //
                    if (!rewardSection.contains("type")) continue;
                    RewardType rewardType = RewardType.UNKNOWN;
                    try {
                        rewardType = RewardType.valueOf(rewardSection.getString("type").toUpperCase());
                    } catch (Exception ex) {
                        Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement reward " + key + ".reward." + rewardsKey + ": Invalid type in config");
                    }
                    long rewardAmount = rewardSection.contains("amount") ? rewardSection.getLong("amount") : 1; // DEFAULT = 1
                    String rewardCommand = rewardSection.contains("command") ? rewardSection.getString("command") : ""; // DEFAULT = 1
                    Material rewardMaterial = Material.AIR;
                    try {
                        rewardMaterial = rewardSection.contains("itemType") ? Material.getMaterial(rewardSection.getString("itemType")) : null; // DEFAULT = null
                    } catch (Exception ex) {
                        Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement reward " + key + ".reward." + rewardsKey + ": Invalid item in config");
                    }
                    rewards.add(new Reward(rewardType, rewardAmount, rewardCommand, rewardMaterial));
                }
            }

            achivements.add(new Achivements(key.toLowerCase(), type, amount, material, rewards));
        }

        return this;
    }

    public List<Achivements> getAchivements() {
        return achivements;
    }

    public boolean isAchievement(String name) {
        for (Achivements a : achivements) {
            if (!a.getName().equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }


    private void loadConfig() {

        //Creates the unique data file if it doesn't exist
        File dataFile = new File("achivements.yml");
        if (!dataFile.exists()) try {
            dataFile.createNewFile();
        } catch (IOException ex) {
        }

        //Loads the file as a bukkit config
        this.data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveConfig() {
        File dataFile = new File("achivements.yml");
        try {
            data.save(dataFile);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
