package dev.garvis.somcbase;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReturnToBaseCommand implements CommandExecutor {

    Datastore ds;

    public ReturnToBaseCommand(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }
        Location loc = this.ds.getBase(player);

        if (loc == null) {
            player.sendMessage(ChatColor.RED + "No base found.");
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 3F, 0.5F);
            return true;
        }

        player.teleport(loc);
        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 3F, 0.5F);
        player.spawnParticle(Particle.REVERSE_PORTAL, loc, 3);

        return true;
    }
}
