package cz.coffee.skript.condition;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;


@Name("Click affects to top or bottom inventory only")
@Description("You can specify top or bottom inventory")
@Examples({
        "on inventory click:",
        "\tif event-inventory = (metadata tag \"test\" of player):",
        "\t\tif event-slot affects the top event-inventory:",
        "\t\t\tcancel event"
})
@Since("1.0")


public class CondInvClickEvent extends Condition {

    static {
        Skript.registerCondition(CondInvClickEvent.class,
                "%slot% affects [the] (:top|:bot[tom]) %inventory%",
                "%slot% does(n't| not) affects [the] (:top|:bot[tom]) %inventory%"
        );
    }

    private boolean negatted;
    private boolean bot, top;

    @Override
    public boolean check(@NotNull Event e) {

        boolean cancelled = false;

        InventoryClickEvent event = (InventoryClickEvent) e;
        if (bot) {
            if (event.getClickedInventory() instanceof PlayerInventory) {
                cancelled = true;
            }
        }

        if (top) {
            if (!(event.getClickedInventory() instanceof PlayerInventory)) {
                cancelled = true;
            }
        }

        return (!negatted) == cancelled;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        if (getParser().isCurrentEvent(InventoryClickEvent.class)) {
            negatted = matchedPattern == 1;
            bot = parseResult.hasTag("bot");
            top = parseResult.hasTag("top");
            setNegated(negatted);
            return true;
        }
        Skript.error("the condition you can use only for 'on inventory click or on inventory instant move'");
        return false;
    }
}
