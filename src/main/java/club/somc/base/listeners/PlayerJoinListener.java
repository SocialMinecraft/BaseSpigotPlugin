package club.somc.base.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // When a player joins, we need to lookup if they are the main
        // and have permission to use /setbase.

        // We also need to lookup the user id (somc / discord one) and store
        // that to use when looking up / setting base locations.
    }
}
