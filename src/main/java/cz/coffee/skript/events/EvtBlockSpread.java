package cz.coffee.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockSpreadEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;


@Since("1.0")

public class EvtBlockSpread extends SkriptEvent {

    static {
        Skript.registerEvent("Block spread", EvtBlockSpread.class, BlockSpreadEvent.class, "block spread[ing]")
                .since("1.0")
                .examples("on block spread:")
                .description("It's called when block spreading");
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
