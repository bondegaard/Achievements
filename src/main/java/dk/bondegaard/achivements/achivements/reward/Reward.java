package dk.bondegaard.achivements.achivements.reward;

import dk.bondegaard.achivements.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Reward {

    private final RewardType rewardType;

    private final long amount;

    private String command;

    private Material item;

    public Reward(RewardType rewardType, long amount, String command, Material item) {
        this.rewardType = rewardType;
        this.amount = amount;
        this.command = command;
        this.item = item;
    }

    private static void addItem(Player player, ItemStack itemStack, int amount) {
        while (amount > 0) {
            int addAmount = Math.min(amount, 64);
            itemStack.setAmount(addAmount);
            if (player.getInventory().firstEmpty() != -1) player.getInventory().addItem(itemStack);
            else player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            amount -= addAmount;
        }
    }

    public Reward giveReward(Player player) {
        if (rewardType == RewardType.MONEY && Main.getInstance().getEcon() != null) {
            Main.getInstance().getEcon().depositPlayer(player, amount);
        } else if (rewardType == RewardType.COMMAND) {
            String c = command.replace("{PLAYER}", player.getName());
            if (c.startsWith("/") && c.length() > 1) c = c.substring(1);
            Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(), "/" + c);
        } else if (rewardType == RewardType.ITEM) {
            if (item != null && amount > 0) addItem(player, new ItemStack(item), (int) amount);
        }
        return this;
    }
}
