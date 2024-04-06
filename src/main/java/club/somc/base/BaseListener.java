package club.somc.base;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;


public class BaseListener implements Listener {

    private HashMap<String, Integer> cache = new HashMap<>();
    private Datastore ds;

    public BaseListener(Datastore ds) {
        this.ds = ds;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

        int now = ds.playerInBase(event.getPlayer());
        Integer prev = cache.get(event.getPlayer().getUniqueId().toString());
        cache.put(event.getPlayer().getUniqueId().toString(), now);
        if (prev == null) prev = 0;
        //if (now == 0) event.getPlayer().setAllowFlight(false);
        event.getPlayer().setAllowFlight(now > 0);

        /*event.getPlayer().sendMessage(Integer.toString(now) +
                        (prev < 25 && now >= 25 ? "Y" : "N") +
                        (prev >= 25 && now < 25 ? "Y" : "N") +
                        (prev != 0 && now == 0 ? "Y" : "N"));*/

        if (prev < 25 && now >= 25) {
            // welcome home
            /*Bukkit.getServer().getPluginManager().callEvent(
                    new IsHomeEvent(event.getPlayer(),
                            true, false));*/
            event.getPlayer().sendTitle(
                    ChatColor.GREEN + "Welcome Home",
                    "", 1, 20, 1);
            //event.getPlayer().setAllowFlight(true);
        } else if (prev >= 25 && now < 25) {
            // leaving home
            /*Bukkit.getServer().getPluginManager().callEvent(
                    new IsHomeEvent(event.getPlayer(),
                            true, true));*/
            event.getPlayer().sendTitle(
                    ChatColor.YELLOW + "Leaving Home",
                    "", 1, 20, 1);
        } /*else if (prev != 0 && now == 0) {
            // left home
            /*Bukkit.getServer().getPluginManager().callEvent(
                    new IsHomeEvent(event.getPlayer(),
                            false, false));* /
            event.getPlayer().setAllowFlight(false);
        }*/
    }
}
