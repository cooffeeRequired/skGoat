package cz.coffee.skript.interactiveGui.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cz.coffee.skript.interactiveGui.sections.SecCustomGUI;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@Since("1.0")

public class ExprSectionInv extends EventValueExpression<Inventory> {

    static {
        Skript.registerExpression(ExprSectionInv.class, Inventory.class, ExpressionType.SIMPLE,
                "[the] [sec[tion]-]inventory"
        );
    }

    @SuppressWarnings("unused")
    public ExprSectionInv() {
        super(Inventory.class);
    }


    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parser) {
        if (getParser().isCurrentSection(SecCustomGUI.class)) {
            return true;
        }
        return super.init(exprs, matchedPattern, isDelayed, parser);
    }

    @Override
    protected @Nullable Inventory @NotNull [] get(@NotNull Event e) {
        System.out.println(Arrays.toString(e.getHandlers().getRegisteredListeners()));
        return super.get(e);
    }
}
