package club.somc.base.commands;

import club.somc.base.Datastore;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SetBaseCommand implements CommandExecutor {

    private Datastore ds;
    private int cooldownSeconds;

    HashMap<UUID, Long> lastBaseSet = new HashMap<UUID, Long>();

    public SetBaseCommand(Datastore ds, int cooldownSeconds) {
        this.ds = ds;
        this.cooldownSeconds = cooldownSeconds;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if(lastBaseSet.containsKey(player.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - lastBaseSet.get(player.getUniqueId());
            if (timeElapsed < cooldownSeconds * 1000) {
                int timeLeft = (int) Math.ceil(cooldownSeconds - (timeElapsed/1000));
                player.sendMessage(ChatColor.RED + "Cooldown not expired: " + timeLeft + " seconds left");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 3F, 0.5F);
                return true;
            }
        }

        boolean re = this.ds.setBase(player);

        if (re) {
            player.sendMessage(ChatColor.GREEN + "Base location set.");
            player.playSound(player.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 3F, 0.5F);
        } else {
            player.sendMessage(ChatColor.RED + "Failed to set base location.");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 3F, 0.5F);
        }

        lastBaseSet.put(player.getUniqueId(), System.currentTimeMillis());

        return true;
    }
}
