
package cz.coffee.skript.interactiveGui.events;

import cz.coffee.skript.interactiveGui.base.IGUI;
import cz.coffee.skript.interactiveGui.base.enums.SlotTypes;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import static cz.coffee.SkGoat.GUI_MAP;
import static cz.coffee.skript.interactiveGui.sections.SecCustomGUI.INTERACTIVE_GUI_EVENT_LOAD;

public class BukkitEvtOnClick extends Event implements Listener {
    private final HandlerList handlerList = new HandlerList();
    private static void executeRestriction(Event e, InventoryView i, int slot) {
        if (e instanceof InventoryClickEvent) {
            GUI_MAP.forEach((w, s) -> {
                if((s.getInventory().equals(i.getTopInventory()))) {
                    IGUI ig = GUI_MAP.get(w);
                    InventoryAction action = ((InventoryClickEvent) e).getAction();
                    ig.getSlots().forEach(guiSlot -> {
                        SlotTypes st = guiSlot.getSlotType();
                        int sIndex = guiSlot.getIndex();
                        if (sIndex == slot) {
                            if (st.equals(SlotTypes.NaN)) {
                                ((InventoryClickEvent) e).setCancelled(true);
                            } else if (st.equals(SlotTypes.PUT_IN)) {
                                if (!(action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE || action == InventoryAction.PLACE_SOME || action == InventoryAction.SWAP_WITH_CURSOR)) {
                                    ((InventoryClickEvent) e).setCancelled(true);
                                }
                            } else if (st.equals(SlotTypes.TAKE_OUT)) {
                                if (!(action == InventoryAction.PICKUP_ALL || action == InventoryAction.PICKUP_HALF || action == InventoryAction.PICKUP_ONE || action == InventoryAction.PICKUP_SOME)) {
                                    ((InventoryClickEvent) e).setCancelled(true);
                                }
                            } else if (st.equals(SlotTypes.INTERACTIVE_SLOT)) {
                                if (!(action == InventoryAction.PICKUP_HALF || action == InventoryAction.PICKUP_ALL || action == InventoryAction.PICKUP_ONE ||
                                        action == InventoryAction.PICKUP_SOME || action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE || action == InventoryAction.PLACE_SOME)) {
                                    ((InventoryClickEvent) e).setCancelled(true);
                                }
                            } else if (st.equals(SlotTypes.DRAGGABLE_SLOT)) {
                                ((InventoryClickEvent) e).setCancelled(true);
                            }
                        }
                    });
                }
            });
        } else if (e instanceof InventoryDragEvent) {
            GUI_MAP.forEach((w, s) -> {
                if (s.getInventory().equals(i.getTopInventory())) {
                    IGUI ig = GUI_MAP.get(w);
                    ig.getSlots().forEach(guiSlot -> {
                        SlotTypes st = guiSlot.getSlotType();
                        if (slot == guiSlot.getIndex()) {
                            if (st.equals(SlotTypes.CLICKABLE_SLOT)) {
                                ((InventoryDragEvent) e).setCancelled(true);
                            } else if (st.equals(SlotTypes.NaN)) {
                                ((InventoryDragEvent) e).setCancelled(true);
                            } else if (st.equals(SlotTypes.TAKE_OUT)){
                                ((InventoryDragEvent) e).setCancelled(true);
                            }
                        }
                    });
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteractiveGuiClick(InventoryClickEvent event) {
        if (INTERACTIVE_GUI_EVENT_LOAD) {
            InventoryAction action;
            int slot = event.getSlot();
            action = event.getAction();

            if (event.getRawSlot() < event.getView().getTopInventory().getSize()-1) {
                if ( (
                        action == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
                                action == InventoryAction.DROP_ALL_CURSOR ||
                                action == InventoryAction.COLLECT_TO_CURSOR
                ))
                    event.setCancelled(true);
                executeRestriction(event, event.getView(), slot);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteractiveGuiDrag(InventoryDragEvent e) {
        if (INTERACTIVE_GUI_EVENT_LOAD) {
            Integer[] slots = e.getRawSlots().toArray(new Integer[0]);
            if (slots[0] < e.getView().getTopInventory().getSize()-1 || slots[slots.length-1] < e.getView().getTopInventory().getSize()-1) {
                int slot = slots[slots.length-1];
                executeRestriction(e, e.getView(), slot);
            }
        }
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return this.handlerList;
    }
}
