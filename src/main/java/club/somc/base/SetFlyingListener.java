package club.somc.base;

import club.somc.base.events.IsHomeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SetFlyingListener implements Listener {

    @EventHandler
    public void onBaseChange(IsHomeEvent event) {
        event.getPlayer().setAllowFlight(event.isHome());
        event.getPlayer().sendMessage(event.isHome() ? "T":"F");
    }
}
