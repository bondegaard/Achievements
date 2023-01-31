package dk.bondegaard.achievements.admintools;

import dk.bondegaard.achievements.Main;
import dk.bondegaard.achievements.admintools.command.AdminToolCommand;

public class AdminToolsHandler {

    public AdminToolsHandler() {
        Main.getInstance().getCommand("aach").setExecutor(new AdminToolCommand());
    }
}
