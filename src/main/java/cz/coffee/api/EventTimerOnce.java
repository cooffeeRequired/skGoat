package cz.coffee.api;

import cz.coffee.skript.events.EvtOnceAtTime;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

/**
 * Copyright coffeeRequired nd contributors
 * <p>
 * Created: středa (30.08.2023)
 */
public class EventTimerOnce extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
