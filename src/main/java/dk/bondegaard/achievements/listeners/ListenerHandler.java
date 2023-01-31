package dk.bondegaard.achievements.listeners;

import dk.bondegaard.achievements.Main;
import dk.bondegaard.achievements.listeners.listener.BlockBreakListener;
import org.bukkit.event.Listener;

public class ListenerHandler {

    public ListenerHandler() {
        registerEvent(new BlockBreakListener());
    }

    public void registerEvent(Listener eventClass) {
        Main.getInstance().getServer().getPluginManager().registerEvents(eventClass, Main.getInstance());
    }
}
