package cz.coffee.skript.interactiveGui.base;

import org.bukkit.inventory.Inventory;

import java.util.Set;

public class IGUI {

    private final Set<GUISlot> slots;
    private final Inventory inventory;


    public IGUI(Inventory i, Set<GUISlot> slots) {
        inventory = i;
        this.slots = slots;
    }

    public Set<GUISlot> getSlots() {
        return slots;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
