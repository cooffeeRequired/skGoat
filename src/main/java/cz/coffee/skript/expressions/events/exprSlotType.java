package cz.coffee.skript.expressions.events;

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
import cz.coffee.skript.interactiveGui.base.enums.SlotTypes;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

import static cz.coffee.SkGoat.GUI_MAP;


@Name("Get type of restriction of slot")
@Examples({"on inventory click:",
        "\tsend event-slottype to console"
})
@Description("")
@Since("1.0")

public class exprSlotType extends EventValueExpression<SlotTypes> {


    static {
        Skript.registerExpression(exprSlotType.class, SlotTypes.class, ExpressionType.SIMPLE,
                "[the] [event-]slottype"
        );
    }

    public exprSlotType() {
        super(SlotTypes.class);
    }


    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parser) {
        if (getParser().isCurrentEvent(InventoryClickEvent.class)) {
            return true;
        }
        return super.init(exprs, matchedPattern, isDelayed, parser);
    }

    @Override
    protected @Nullable SlotTypes @NotNull [] get(@NotNull Event e) {
        if (e instanceof InventoryClickEvent) {

            AtomicReference<SlotTypes> type = new AtomicReference<>(SlotTypes.NaN);

            GUI_MAP.forEach((id, igui) -> {
                if (igui.getInventory().equals(((InventoryClickEvent) e).getView().getTopInventory())) {
                    igui.getSlots().forEach(slot -> {
                        if (slot.getIndex() == ((InventoryClickEvent) e).getSlot()) {
                            type.set(slot.getSlotType());
                        }
                    });
                }
            });
            return new SlotTypes[]{type.get()};
        }
        return super.get(e);
    }
}
