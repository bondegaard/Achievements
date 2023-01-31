package dk.bondegaard.achievements.listeners.listener;

import dk.bondegaard.achievements.Main;
import dk.bondegaard.achievements.achievements.Achievements;
import dk.bondegaard.achievements.achievements.AchievementsType;
import dk.bondegaard.achievements.playerdata.APlayer;
import dk.bondegaard.achievements.playerdata.PlayerDataHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Material material = event.getBlock().getType();
        APlayer aPlayer = PlayerDataHandler.getAPlayer(event.getPlayer());
        if (aPlayer == null) return;

        for (Achievements achievements : Main.getInstance().getAchivementsHandler().getAchivements()) {
            if (achievements.getType() != AchievementsType.BLOCK_PLACE) continue;
            if (!(achievements.getItemType() != null && achievements.getItemType() == material)) continue;

            achievements.addProgress(aPlayer);
        }
    }
}
