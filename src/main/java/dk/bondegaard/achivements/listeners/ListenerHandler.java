package dk.bondegaard.achivements.listeners;

import dk.bondegaard.achivements.Main;
import dk.bondegaard.achivements.listeners.listener.BlockBreakListener;
import org.bukkit.event.Listener;

public class ListenerHandler {

    public ListenerHandler() {
        registerEvent(new BlockBreakListener());
    }

    public void registerEvent(Listener eventClass) {
        Main.getInstance().getServer().getPluginManager().registerEvents(eventClass, Main.getInstance());
    }
}
