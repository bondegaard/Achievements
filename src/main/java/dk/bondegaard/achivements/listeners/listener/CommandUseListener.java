package dk.bondegaard.achivements.listeners.listener;

import dk.bondegaard.achivements.Main;
import dk.bondegaard.achivements.achivements.Achivements;
import dk.bondegaard.achivements.achivements.AchivementsType;
import dk.bondegaard.achivements.playerdata.APlayer;
import dk.bondegaard.achivements.playerdata.PlayerDataHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandUseListener implements Listener {


    @EventHandler
    public void onCommandUse(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;

        APlayer aPlayer = PlayerDataHandler.getAPlayer(event.getPlayer());
        if (aPlayer == null) return;

        for (Achivements achivements : Main.getInstance().getAchivementsHandler().getAchivements()) {
            if (achivements.getType() != AchivementsType.COMMAND_USE) continue;
            if (achivements.getItemType() == null) continue;

            achivements.addProgress(aPlayer);
        }
    }
}
