package cz.coffee.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;


@Since("1.0")

public class EvtInventoryDrag extends SkriptEvent {

    static {
        Skript.registerEvent("Inventory Drag", EvtInventoryDrag.class, InventoryDragEvent.class,
                "inventory drag[ing]")
                .description("It's called when you drag item from / to inventory.")
                .examples("on inventory drag:")
                .since("1.0");
    }

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, @NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return (e instanceof InventoryDragEvent dragEvent);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "on inventory drag";
    }
}
