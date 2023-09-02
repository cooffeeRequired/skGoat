package cz.coffee.goat.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockSpreadEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;


@Since("1.0")
@Name("Block spread")
@Description("It's called when block spreading")
@Examples("on block spread:")

@SuppressWarnings("unused")
public class EvtBlockSpread extends SkriptEvent {

    static {
        Skript.registerEvent("Block spread", EvtBlockSpread.class, BlockSpreadEvent.class, "block spread[ing]");
    }
    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, @NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return e instanceof BlockSpreadEvent;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "on block spreading";
    }
}
