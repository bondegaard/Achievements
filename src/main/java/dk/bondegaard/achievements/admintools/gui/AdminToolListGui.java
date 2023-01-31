package dk.bondegaard.achievements.admintools.gui;

import dk.bondegaard.achievements.Main;
import dk.bondegaard.achievements.achievements.Achievements;
import dk.bondegaard.achievements.achievements.AchievementsType;
import dk.bondegaard.achievements.achievements.reward.Reward;
import dk.bondegaard.achievements.achievements.reward.RewardType;
import dk.bondegaard.achievements.utils.GUI;
import dk.bondegaard.achievements.utils.ItemBuilder;
import dk.bondegaard.achievements.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminToolListGui extends GUI {
    public AdminToolListGui(Player player) {
        super("&cAchievements - Admin", 6, player);

        addGuiItems();
    }

    private void addGuiItems() {
        page_items.clear();
        for (Achievements achievements:Main.getInstance().getAchivementsHandler().getAchivements()) {
            ItemBuilder itemBuilder;
            if (achievements.getType() == AchievementsType.BLOCK_BREAK) itemBuilder = new ItemBuilder(Material.DIAMOND_PICKAXE);
            else if (achievements.getType() == AchievementsType.BLOCK_PLACE) itemBuilder = new ItemBuilder(Material.DIAMOND_BLOCK);
            else if (achievements.getType() == AchievementsType.COMMAND_USE) itemBuilder = new ItemBuilder(Material.REDSTONE);
            else if (achievements.getType() == AchievementsType.PLAYER_EAT) itemBuilder = new ItemBuilder(Material.COOKED_BEEF);
            else itemBuilder = new ItemBuilder(Material.PAPER);
            itemBuilder.name(StringUtil.colorize("&f"+achievements.getName()));
            itemBuilder.addLore("&fGoal: "+ achievements.getType().name().toLowerCase());
            itemBuilder.addLore("&fAmount: "+ achievements.getAmountGoal());
            itemBuilder.addLore("&fItem Type: "+ achievements.getItemType().name());
            itemBuilder.addLore("&fRewards:");
            int amount = 0;
            for (Reward reward : achievements.getRewards()) {
                if (reward.getRewardType() == RewardType.COMMAND) itemBuilder.addLore("&c- Command:" +reward.getCommand()+ "!");
                else if (reward.getRewardType() == RewardType.ITEM) itemBuilder.addLore("&c- Item:" +reward.getItem().name()+ "!");
                else if (reward.getRewardType() == RewardType.MONEY) itemBuilder.addLore("&c- Money:" +reward.getAmount()+ "$");
                else continue;
                amount ++;
            }
            if (amount == 0) itemBuilder.addLore("&c- Nothing!");


            page_items.add(itemBuilder.colorizeAll().build());
            this.render();
        }
    }

    @Override
    public void click(int slot, ItemStack clickedItem, boolean shift) {}
}
