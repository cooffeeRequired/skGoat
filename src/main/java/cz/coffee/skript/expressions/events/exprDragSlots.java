package cz.coffee.skript.expressions.events;


import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;


@Name("Dragged Slots")
@Examples("The dragged inventory slots")
@Since("1.0")

public class exprDragSlots extends EventValueExpression<Integer> {


    private boolean isArray;

    static {
        Skript.registerExpression(exprDragSlots.class, Integer.class, ExpressionType.SIMPLE,
                "[the] [event-]slots",
                "[the] [event-]slot"
        );
    }

    public exprDragSlots() {
        super(Integer.class);
    }


    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parser) {
        isArray = matchedPattern == 0;
        if (getParser().isCurrentEvent(InventoryDragEvent.class)) {
            return true;
        }
        return super.init(exprs, matchedPattern, isDelayed, parser);
    }

    @Override
    protected @Nullable Integer @NotNull [] get(@NotNull Event e) {
        if (e instanceof InventoryDragEvent) {
            Set<Integer> set = ((InventoryDragEvent) e).getRawSlots();
            ArrayList<Integer> iw = new ArrayList<>(set);
            if (isArray) {
                return iw.toArray(new Integer[0]);
            } else {
                return new Integer[]{iw.get(0)};
            }
        }
        return super.get(e);
    }
}
