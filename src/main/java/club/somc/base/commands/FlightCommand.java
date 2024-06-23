package club.somc.base.commands;

import club.somc.base.Datastore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlightCommand implements CommandExecutor {

    Datastore ds;

    public FlightCommand(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }
        if (args.length == 0 || !(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))) {
            player.sendMessage(ChatColor.RED + "Usage: /baseflight <on|off>");
            return false;
        }
        this.ds.setFlight(player, args[0].equalsIgnoreCase("on"));
        StringBuilder response = new StringBuilder();
        response.append(ChatColor.GRAY + "Base Flight: ");
        if (this.ds.getFlight(player))
            response.append(ChatColor.GREEN + "ON");
        else
            response.append(ChatColor.RED + "OFF");
        player.sendMessage(response.toString());
        return true;
    }
}
