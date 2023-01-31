package dk.bondegaard.achievements.achievements;

import dk.bondegaard.achievements.achievements.reward.Reward;
import dk.bondegaard.achievements.playerdata.APlayer;
import dk.bondegaard.achievements.utils.PlayerUtils;
import dk.bondegaard.achievements.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Achievements {

    // Given name of achivement (Is Unique)
    private final String name;

    // Type of Achivement
    private final AchievementsType type;

    // How much a player has to do for a specific task
    private long amountGoal;

    // Rewards
    private List<Reward> rewards = new ArrayList<>();

    // For tasks that requires a specific item to be broken, placed, eaten, etc...
    private Material itemType = null;

    private String completePlayerMessage = "";

    private String completePlayerBroadcast = "";


    public Achievements(String name, AchievementsType type, long amountGoal, Material itemType, List<Reward> rewards, String completePlayerMessage, String completePlayerBroadcast) {
        this.name = name.split("\\.")[0];
        this.type = type;
        this.amountGoal = amountGoal;
        this.itemType = itemType;
        this.rewards = rewards;
        this.completePlayerMessage = completePlayerMessage;
        this.completePlayerBroadcast = completePlayerBroadcast;
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public AchievementsType getType() {
        return type;
    }

    public long getAmountGoal() {
        return amountGoal;
    }

    //SETTERS
    public void setAmountGoal(long amountGoal) {
        this.amountGoal = amountGoal;
    }

    public Material getItemType() {
        return itemType;
    }

    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public void giveReward(Player player) {
        for (Reward reward : rewards) {
            reward.giveReward(player);
        }
        if (!completePlayerMessage.equals("")) PlayerUtils.sendMessage(player, completePlayerMessage);
        if (!completePlayerBroadcast.equals(""))
            Bukkit.broadcastMessage(StringUtil.colorize(completePlayerBroadcast.replace("{PLAYER}", player.getName())));
    }

    public void addProgress(APlayer aPlayer) {
        if (aPlayer == null) return;

        if (aPlayer.getData().contains(name + "-completed")) return;

        // ADD PROGRESS TO PLAYER
        long progress = 1;
        if (aPlayer.getData().contains(name + "-progress")) {
            progress = aPlayer.getData().getLong(name + "-progress");
            aPlayer.getData().set(name + "-progress", progress + 1);
        } else aPlayer.getData().set(name + "-progress", progress);

        // CHECK IF ACHIVEMENT WAS COMPLETED
        if (progress + 1 >= amountGoal) {
            aPlayer.getData().set(name + "-completed", new Timestamp(System.currentTimeMillis()).toString());
            giveReward(aPlayer.getPlayer());
        }
        aPlayer.save();
    }
}
