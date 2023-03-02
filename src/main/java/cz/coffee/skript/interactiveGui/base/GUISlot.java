package cz.coffee.skript.interactiveGui.base;

import cz.coffee.skript.interactiveGui.base.enums.SlotTypes;

public class GUISlot {
    final private int index;
    final private SlotTypes slotType;

    public int getIndex() {
        return index;
    }

    public SlotTypes getSlotType() {
        return slotType;
    }

    public GUISlot(int i, SlotTypes s) {
        this.index = i;
        this.slotType = s;
    }
}
