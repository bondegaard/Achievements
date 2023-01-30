package dk.bondegaard.achivements.listeners.listener;

import dk.bondegaard.achivements.Main;
import dk.bondegaard.achivements.achivements.Achivements;
import dk.bondegaard.achivements.achivements.AchivementsType;
import dk.bondegaard.achivements.playerdata.APlayer;
import dk.bondegaard.achivements.playerdata.PlayerDataHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {


    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Material material = event.getBlock().getType();
        APlayer aPlayer = PlayerDataHandler.getAPlayer(event.getPlayer());
        if (aPlayer == null) return;

        for (Achivements achivements : Main.getInstance().getAchivementsHandler().getAchivements()) {
            if (achivements.getType() != AchivementsType.BLOCK_PLACE) continue;
            if (!(achivements.getItemType() != null && achivements.getItemType() == material)) continue;

            achivements.addProgress(aPlayer);
        }
    }
}
