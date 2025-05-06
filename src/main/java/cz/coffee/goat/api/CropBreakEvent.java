package cz.coffee.goat.api;

import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CropBreakEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final BlockBreakEvent eventBlockBreakEvent;
    @Getter
    private final PlayerInteractEvent playerInteractEvent;

    public CropBreakEvent(BlockBreakEvent event) {
        this.eventBlockBreakEvent = event;
        this.playerInteractEvent = null;
    }

    public CropBreakEvent(PlayerInteractEvent event) {
        this.eventBlockBreakEvent = null;
        this.playerInteractEvent = event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        if (this.eventBlockBreakEvent != null) {
            return this.eventBlockBreakEvent.isCancelled();
        } else if (this.playerInteractEvent != null) {
            return this.playerInteractEvent.isCancelled();
        }
        return false;
    }

    @Override
    public void setCancelled(boolean b) {
        if (this.eventBlockBreakEvent != null) {
            this.eventBlockBreakEvent.setCancelled(b);
        } else if (this.playerInteractEvent != null) {
            this.playerInteractEvent.setCancelled(b);
        }
    }
}
