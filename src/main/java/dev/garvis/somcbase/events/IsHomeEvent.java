package dev.garvis.somcbase.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class IsHomeEvent extends Event {

    private boolean isHome;
    private boolean isOutskirts;
    private Player player;

    /**
     * Create a new event to say if a player is home or not.
     * @param isHome Is the player within their home.
     * @param isOutskirts Is the player near the border of their home.
     */
    public IsHomeEvent(Player player, boolean isHome, boolean isOutskirts) {
        this.isHome = isHome;
        this.isOutskirts = isOutskirts;
        this.player = player;
    }

    public boolean isHome() {
        return this.isHome;
    }
    public boolean isOutskirts() {
        return this.isOutskirts;
    }
    public Player getPlayer() {
        return this.player;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerrList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
