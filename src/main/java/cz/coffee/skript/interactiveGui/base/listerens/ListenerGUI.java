package cz.coffee.skript.interactiveGui.base.listerens;

import cz.coffee.SkGoat;
import cz.coffee.skript.interactiveGui.base.IGUI;
import cz.coffee.skript.interactiveGui.events.BaseEventGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

import static cz.coffee.SkGoat.GUI_MAP;
import static cz.coffee.util.SimpleUtils.color;

public class ListenerGUI implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        for (Map.Entry<String, IGUI> map: GUI_MAP.entrySet()) {
            if (map.getValue().getInventory().equals(event.getView().getTopInventory())) {
                System.out.println(color("&aRegistred"));
                SkGoat.getInstance().getPm().callEvent(new BaseEventGUI(
                        event.getView(),
                        event.getSlotType(),
                        event.getSlot(),
                        event.getClick(),
                        event.getAction()
                ));
                break;
            }
        }
    }

}
