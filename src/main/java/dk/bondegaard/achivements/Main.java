package dk.bondegaard.achivements;

import dk.bondegaard.achivements.achivements.AchivementsHandler;
import dk.bondegaard.achivements.listeners.ListenerHandler;
import dk.bondegaard.achivements.playerdata.APlayer;
import dk.bondegaard.achivements.playerdata.PlayerDataHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Economy econ = null;

    private PlayerDataHandler playerDataHandler;

    private AchivementsHandler achivementsHandler;

    public static Main getInstance() {
        return instance;
    }

    public static void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(getInstance(), runnable);
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();


        if (!setupEconomy()) {
            getLogger().log(Level.WARNING, "Unable to setup economy...! Running without.");
            econ = null;
        }

        playerDataHandler = new PlayerDataHandler(this);

        achivementsHandler = new AchivementsHandler().load();

        new ListenerHandler();
    }

    @Override
    public void onDisable() {
        achivementsHandler.saveConfig();
        for (APlayer aPlayer : PlayerDataHandler.getPlayers()) {
            aPlayer.save(true);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEcon() {
        return econ;
    }

    public PlayerDataHandler getPlayerDataHandler() {
        return playerDataHandler;
    }

    public AchivementsHandler getAchivementsHandler() {
        return achivementsHandler;
    }
}
