package cz.coffee.goat.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import cz.coffee.goat.api.CropBreakEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtCropBreak extends SkriptEvent {

    static {
        Skript.registerEvent("Crop break", EvtCropBreak.class, CropBreakEvent.class, "crop break[ing]");
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof CropBreakEvent;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "on crop break";
    }
}
