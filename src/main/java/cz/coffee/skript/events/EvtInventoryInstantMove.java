package cz.coffee.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;


@Since("1.0")

public class EvtInventoryInstantMove extends SkriptEvent {

    static {
        Skript.registerEvent("Inventory Instant move", EvtInventoryInstantMove.class, InventoryClickEvent.class,
                        "inventory instant move[ing]")
                .description("It's called when you drag shift clicked item to move.")
                .examples("on inventory instant move:")
                .since("1.0");
    }

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, @NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        if (e instanceof InventoryClickEvent event) {
            return event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY);
        }
        return false;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "null";
    }
}
