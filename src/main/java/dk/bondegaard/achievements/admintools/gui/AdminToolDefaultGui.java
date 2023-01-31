package dk.bondegaard.achievements.admintools.gui;

import dk.bondegaard.achievements.utils.GUI;
import dk.bondegaard.achievements.utils.ItemBuilder;
import dk.bondegaard.achievements.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminToolDefaultGui extends GUI {
    public AdminToolDefaultGui(Player player) {
        super(StringUtil.colorize("&cAchievements - Admin"), 5, player);

        this.layout.put(19, new ItemBuilder(Material.PAPER).name("&fAchievements List").lore("&fClick to see a list of all", "&fAchievements currently on the server.").colorizeAll().build());
        this.layout.put(21, new ItemBuilder(Material.EMPTY_MAP).name("&fAchievements Edit").lore("&fClick to edit an Achievements", "&fthat is already created.").colorizeAll().build());
        this.layout.put(23, new ItemBuilder(Material.EMERALD).name("&fAchievements Add").lore("&fClick to add a new Achievements", "&fto the server.").colorizeAll().build());
    }

    @Override
    public void click(int slot, ItemStack clickedItem, boolean shift) {
        if (slot == 19) new AdminToolListGui(player).open(player);
        else if (slot == 21) new AdminToolEditGui(player).open(player);
        else if (slot == 23) new AdminToolAddGui(player).open(player);
    }
}

// 0  1  2  3  4  5  6  7  8
// 9  10 11 12 13 14 15 16 17
// 18 19 20 21 22 23 24 25 26
// 27 28 29 30 31 32 33 34 35
// 36 37 38 39 40 41 42 43 44


// 45 46 47 48 49 50 51 52 53