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
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Name("whitelisted slots - on Inventory Drag")
@Description("You can check if the dragged slots contains your specified whitelisted slots.")
@Examples({
        "on inventory drag:",
        "\tevent-slots contain slots 1, 2, 3 of event-inventory:",
        "\t\tsend true to event-player"
})
@Since("1.0")

public class CondInvDragEvent extends Condition {

    static {
        Skript.registerCondition(CondInvDragEvent.class,
                "%integers% exclude slots %integers% of %inventory%",
                "%integers% include slots %integers% of %inventory%"
        );
    }

    Expression<Integer> whitelist;
    Expression<Integer> evtSlots;
    Expression<Inventory> evtInventory;
    private boolean negatted;

    @Override
    public boolean check(@NotNull Event e) {
        List<Integer> whitelisted = Arrays.asList(whitelist.getAll(e));
        Integer[] evtSlots = this.evtSlots.getAll(e);
        Inventory inventory = ((InventoryDragEvent) e).getInventory();
        ArrayList<Integer> menuSize = new ArrayList<>();
        int INV_SIZE_PO = 35;


        for (int i = 0; i < (INV_SIZE_PO + inventory.getSize()); i++) {
            int finalI = i;
            if (whitelisted.stream().noneMatch(n -> Objects.equals(finalI, n))) {
                menuSize.add(finalI);
            }
        }


        boolean C1 = false, C2 = false;

        for (int eventSlot : evtSlots) {
            if (negatted) {
                if (whitelisted.contains(eventSlot)) {
                    C1 = true;
                    break;
                }
            } else {
                if (!whitelisted.contains(eventSlot)) {
                    C1 = true;
                    break;
                }
            }
        }
        for (int menuSlot : menuSize) {
            if (!negatted) {
                if (Arrays.asList(evtSlots).contains(menuSlot)) {
                    C2 = true;
                    break;
                }
            } else {
                if (!Arrays.asList(evtSlots).contains(menuSlot)) {
                    C2 = true;
                    break;
                }
            }
        }


        return (C1 && C2);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return evtSlots.toString(e, debug) + (negatted ? ("include ") : ("exclude ")) + "slots " + whitelist.toString(e, debug) + " of " + evtInventory.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        if (getParser().isCurrentEvent(InventoryDragEvent.class)) {
            negatted = matchedPattern == 1;
            whitelist = (Expression<Integer>) exprs[1];
            evtSlots = (Expression<Integer>) exprs[0];
            evtInventory = (Expression<Inventory>) exprs[2];
            setNegated(negatted);
            return true;
        }
        Skript.error("the condition you can use only for 'on inventory drag'");
        return false;
    }
}
