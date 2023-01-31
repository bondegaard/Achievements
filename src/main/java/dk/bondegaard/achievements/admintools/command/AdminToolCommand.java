package dk.bondegaard.achievements.admintools.command;

import dk.bondegaard.achievements.Main;
import dk.bondegaard.achievements.admintools.gui.AdminToolDefaultGui;
import dk.bondegaard.achievements.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminToolCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("achievements.admin")) {
            PlayerUtils.sendMessage(sender, "&cYou do not have permission to use this command!");
            return true;
        }
        if (args.length < 1) {
            sendHelpMessage(sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            Main.getInstance().reloadConfig();
            Main.getInstance().getAchivementsHandler().load();
            PlayerUtils.sendMessage(sender, "&cPlugin Reloaded!");
            return true;
        }

        else if (args[0].equalsIgnoreCase("edit")) {
            if (!(sender instanceof Player)) {
                PlayerUtils.sendMessage(sender, "&cThis command can only be used by a player!");
                return true;
            }
            Player player = (Player)sender;
            new AdminToolDefaultGui(player).open(player);
            return true;
        }


        return true;
    }

    private static void sendHelpMessage(CommandSender sender) {
        PlayerUtils.sendMessage(sender, "&cUse &l/aach reload&c to reload plugin!");
        PlayerUtils.sendMessage(sender, "&cUse &l/aach edit&c to edit/remove/add achievements!");
    }
}
