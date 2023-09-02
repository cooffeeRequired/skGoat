package cz.coffee.goat.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Copyright coffeeRequired nd contributors
 * <p>
 * Created: středa (30.08.2023)
 */
public class EventTimerOnce extends Event {

    private static final HandlerList handlers = new HandlerList();

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
