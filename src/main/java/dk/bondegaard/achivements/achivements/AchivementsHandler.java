package dk.bondegaard.achivements.achivements;

import dk.bondegaard.achivements.Main;
import dk.bondegaard.achivements.achivements.reward.Reward;
import dk.bondegaard.achivements.achivements.reward.RewardType;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class AchivementsHandler {

    private final List<Achivements> achivements = new ArrayList<>();

    public AchivementsHandler load() {
        achivements.clear();

        File folder = new File(Main.getInstance().getDataFolder(), "achivements");
        folder.mkdir();

        File[] files = folder.listFiles();

        assert files != null;
        for (File file : files) {
            try {
                if (!file.exists()) continue;
                FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                if (isAchievement(file.getName())) {
                    Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement " + file.getName() + ": Achivement name is already used!");
                    continue;
                }

                // ############ //
                //  LOAD TYPE   //
                // ############ //
                if (!data.contains("type")) continue;
                AchivementsType type = AchivementsType.UNKNOWN;
                try {
                    type = AchivementsType.valueOf(data.getString("type").toUpperCase());
                } catch (Exception ex) {
                    Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement " + file.getName() + ": Invalid type in config");
                }

                //  LOAD AMOUNT  //
                long amount = data.contains("amount") ? data.getLong("amount") : 1; // DEFAULT = 1
                // ############### //
                //  LOAD MATERIAL  //
                // ############### //
                Material material = Material.AIR;
                try {
                    material = data.contains("itemType") ? Material.getMaterial(data.getString("itemType")) : null; // DEFAULT = null
                } catch (Exception ex) {
                    Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement " + file.getName() + ": Invalid itemType in config");
                }

                // ############## //
                //  LOAD REWARDS  //
                // ############## //

                List<Reward> rewards = new ArrayList<>();
                if (data.contains("rewards")) {
                    for (String rewardsKey : data.getConfigurationSection("rewards").getKeys(false)) {
                        ConfigurationSection rewardSection = data.getConfigurationSection("rewards." + rewardsKey);
                        // ############ //
                        //  LOAD TYPE   //
                        // ############ //
                        if (!rewardSection.contains("type")) continue;
                        RewardType rewardType = RewardType.UNKNOWN;
                        try {
                            rewardType = RewardType.valueOf(rewardSection.getString("type").toUpperCase());
                        } catch (Exception ex) {
                            Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement reward " + file.getName() + "| reward." + rewardsKey + ": Invalid type in config");
                        }
                        long rewardAmount = rewardSection.contains("amount") ? rewardSection.getLong("amount") : 1; // DEFAULT = 1
                        String rewardCommand = rewardSection.contains("command") ? rewardSection.getString("command") : ""; // DEFAULT = 1
                        Material rewardMaterial = Material.AIR;
                        try {
                            rewardMaterial = rewardSection.contains("item") ? Material.getMaterial(rewardSection.getString("item")) : null; // DEFAULT = null
                        } catch (Exception ex) {
                            Main.getInstance().getLogger().log(Level.WARNING, "Could not load achievement reward " + file.getName() + "| reward." + rewardsKey + ": Invalid item in config");
                        }
                        rewards.add(new Reward(rewardType, rewardAmount, rewardCommand, rewardMaterial));
                    }
                }
                String completePlayerMessage = data.contains("complete-player-message") ? data.getString("complete-player-message") : "";
                String completePlayerBroadcast = data.contains("complete-player-broadcast") ? data.getString("complete-player-broadcast") : "";

                achivements.add(new Achivements(file.getName().toLowerCase(), type, amount, material, rewards, completePlayerMessage, completePlayerBroadcast));
            } catch (NullPointerException ex) {
                Main.getInstance().getLogger().log(Level.INFO, "Could not load achievement: " + file.getName());
            }
        }
        Main.getInstance().getLogger().log(Level.INFO, "Loaded achievement: " + achivements.size());

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


}
