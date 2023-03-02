package cz.coffee.skript;

import ch.njol.skript.doc.Since;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import cz.coffee.skript.interactiveGui.events.BaseEventGUI;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


@Since("1.0")

public class EvtValues {
    static {
        EventValues.registerEventValue(InventoryDragEvent.class, Inventory.class,
                new Getter<>() {
                    @Override
                    public Inventory get(InventoryDragEvent event) {
                        return event.getInventory();
                    }
                }, 0);
        EventValues.registerEventValue(InventoryDragEvent.class, Player.class,
                new Getter<>() {
                    @Override
                    public Player get(InventoryDragEvent event) {
                        return (Player) event.getWhoClicked();
                    }
                }, 0);
        EventValues.registerEventValue(InventoryDragEvent.class, ItemStack.class,
                new Getter<>() {
                    @Override
                    public ItemStack get(InventoryDragEvent event) {
                        return event.getOldCursor();
                    }
                }, 0
        );

        EventValues.registerEventValue(BlockBreakBlockEvent.class, Block.class,
                new Getter<>() {
                    @Override
                    public Block get(BlockBreakBlockEvent event) {
                        return event.getBlock();
                    }
                }, 0
        );

        EventValues.registerEventValue(BlockBreakBlockEvent.class, Block.class,
                new Getter<>() {
                    @Override
                    public Block get(BlockBreakBlockEvent event) {
                        return event.getBlock();
                    }
                }, 0
        );

        EventValues.registerEventValue(BaseEventGUI.class, Inventory.class,
                new Getter<>() {
                    @Override
                    public Inventory get(BaseEventGUI event) {
                        return event.getInventory();
                    }
                }, 0
        );
    }

}
