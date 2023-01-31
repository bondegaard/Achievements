package dk.bondegaard.achievements.admintools.gui;

import dk.bondegaard.achievements.utils.GUI;
import org.bukkit.entity.Player;

public class AdminToolAddGui extends GUI {
    public AdminToolAddGui(Player player) {
        super("&cAchievements - Admin", 6, player);
    }
}
