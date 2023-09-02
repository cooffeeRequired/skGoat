package cz.coffee.goat.skript.expressions.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockSpreadEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Source block - (On block spread)")
@Description("The block from which it spreading")
@Examples({"on block spread:",
        "\tsend event-source"
})
@Since("1.0")
@SuppressWarnings("unused")


public class exprBlockSpread extends EventValueExpression<Object> {

    static {
        Skript.registerExpression(exprBlockSpread.class, Object.class, ExpressionType.SIMPLE,
                "[the] [event-]source"
        );
    }

    public exprBlockSpread() {
        super(Block.class);
    }


    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parser) {
        if (getParser().isCurrentEvent(BlockSpreadEvent.class)) {
            return true;
        }
        return super.init(exprs, matchedPattern, isDelayed, parser);
    }

    @Override
    protected @Nullable Object @NotNull [] get(@NotNull Event e) {
        if (e instanceof BlockSpreadEvent) {
            return new Block[]{((BlockSpreadEvent) e).getSource()};
        }
        return super.get(e);
    }
}
