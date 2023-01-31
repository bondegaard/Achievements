package dk.bondegaard.achievements.listeners.listener;

import dk.bondegaard.achievements.Main;
import dk.bondegaard.achievements.achievements.Achievements;
import dk.bondegaard.achievements.achievements.AchievementsType;
import dk.bondegaard.achievements.playerdata.APlayer;
import dk.bondegaard.achievements.playerdata.PlayerDataHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandUseListener implements Listener {


    @EventHandler
    public void onCommandUse(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;

        APlayer aPlayer = PlayerDataHandler.getAPlayer(event.getPlayer());
        if (aPlayer == null) return;

        for (Achievements achievements : Main.getInstance().getAchivementsHandler().getAchivements()) {
            if (achievements.getType() != AchievementsType.COMMAND_USE) continue;
            if (achievements.getItemType() == null) continue;

            achievements.addProgress(aPlayer);
        }
    }
}
