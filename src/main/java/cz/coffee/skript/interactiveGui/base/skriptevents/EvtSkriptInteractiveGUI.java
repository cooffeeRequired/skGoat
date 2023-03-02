package cz.coffee.skript.interactiveGui.base.skriptevents;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import cz.coffee.skript.interactiveGui.events.BaseEventGUI;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Since("1.0")

public class EvtSkriptInteractiveGUI extends SkriptEvent {

    static {
        Skript.registerEvent("interactive gui use", EvtSkriptInteractiveGUI.class, BaseEventGUI.class,
                "interactive [g]ui use"
        );
    }

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, @NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "interactive gui use";
    }
}
