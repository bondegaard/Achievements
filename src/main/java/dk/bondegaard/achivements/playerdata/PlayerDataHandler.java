package dk.bondegaard.achivements.playerdata;

import dk.bondegaard.achivements.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDataHandler implements Listener {

    private static final List<APlayer> players = new ArrayList<>();

    public PlayerDataHandler(Main instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }


    public static List<APlayer> getPlayers() {
        return players;
    }

    public static APlayer getAPlayer(Player player) {
        return getAPlayer(player.getUniqueId().toString());
    }

    public static APlayer getAPlayer(UUID uuid) {
        return getAPlayer(uuid.toString());
    }

    public static APlayer getAPlayer(String uuid) {
        for (APlayer aPlayer : players) {
            if (aPlayer.getPlayer() == null) continue;
            if (!aPlayer.getPlayer().getUniqueId().toString().equals(uuid)) continue;
            return aPlayer;
        }
        return null;
    }

    public static APlayer getAPlayerByName(Player player) {
        return getAPlayerByName(player.getName());
    }

    public static APlayer getAPlayerByName(String username) {
        for (APlayer aPlayer : players) {
            if (aPlayer.getPlayer() == null) continue;
            if (!aPlayer.getPlayer().getName().equals(username)) continue;
            return aPlayer;
        }
        return null;
    }

    public static boolean hasAPlayerByName(String username) {
        return getAPlayerByName(username) != null;
    }

    public static boolean hasAPlayerByName(Player player) {
        return getAPlayerByName(player.getName()) != null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (hasPlayer(event.getPlayer())) return;
        players.add(new APlayer(event.getPlayer()).load());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        APlayer aPlayer = getAPlayer(event.getPlayer());
        if (aPlayer == null) return;
        aPlayer.save(true);
        players.remove(aPlayer);
    }

    public boolean hasPlayer(String uuid) {
        return getAPlayer(uuid) != null;
    }

    public boolean hasPlayer(UUID uuid) {
        return getAPlayer(uuid.toString()) != null;
    }

    public boolean hasPlayer(Player player) {
        return getAPlayer(player.getUniqueId().toString()) != null;
    }
}
