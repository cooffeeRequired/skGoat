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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Future event inv")
@Examples("The future inventory")
@Since("1.0")


public class exprInvInstantMove extends EventValueExpression<Inventory> {
    static {
        Skript.registerExpression(exprInvInstantMove.class, Inventory.class, ExpressionType.SIMPLE,
                "[the] future [event-]inventory"
        );
    }

    public exprInvInstantMove() {
        super(Inventory.class);
    }


    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parser) {
        if (getParser().isCurrentEvent(InventoryClickEvent.class)) {
            return true;
        }
        return super.init(exprs, matchedPattern, isDelayed, parser);
    }

    @Override
    protected @Nullable Inventory @NotNull [] get(@NotNull Event e) {
        if (e instanceof InventoryClickEvent) {
           Inventory inv = ((InventoryClickEvent) e).getInventory();
           return new Inventory[]{inv};
        }
        return super.get(e);
    }
}
