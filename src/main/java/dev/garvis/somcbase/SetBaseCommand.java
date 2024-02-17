package dev.garvis.somcbase;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBaseCommand implements CommandExecutor {

    private Datastore ds;

    public SetBaseCommand(Datastore ds) {
        this.ds = ds;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }
        boolean re = this.ds.setBase(player);

        if (re) {
            player.sendMessage(ChatColor.GREEN + "Base location set.");
            player.playSound(player.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 3F, 0.5F);
        } else {
            player.sendMessage(ChatColor.RED + "Failed to set base location.");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 3F, 0.5F);
        }

        return true;
    }
}
