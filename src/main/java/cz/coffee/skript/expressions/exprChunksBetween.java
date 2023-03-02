package cz.coffee.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;


@Name("All chunks between locations")
@Description("It's gets the chunks between those locations")
@Examples("command testchunk:\n" +
        "    trigger:\n" +
        "        set {_l2} to player's location\n" +
        "        add 10 to {_l2}'s x-coord\n" +
        "        add 10 to {_l2}'s z-coord\n" +
        "\n" +
        "        set {a::*} to all chunks between player's location and {_l2}\n" +
        "        send {a::*} to player")
@Since("1.0")

public class exprChunksBetween extends SimpleExpression<Chunk> {

    private Expression<Location> loc1, loc2;

    static {
        Skript.registerExpression(exprChunksBetween.class, Chunk.class, ExpressionType.SIMPLE,
                "all chunks between %location% and %location%"
        );
    }
    @Override
    protected Chunk @NotNull [] get(@NotNull Event e) {
        Set<Chunk> chunks = new HashSet<>();
        Location l1 = loc1.getSingle(e);
        Location l2 = loc2.getSingle(e);
        if (l1 == null || l2 == null) return new Chunk[0];

        int minX = Math.min(l1.getBlockX(), l2.getBlockX());
        int maxX = Math.max(l1.getBlockX(), l2.getBlockX());

        int minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                Chunk chunk = new Location(l1.getWorld(), x, 0, z).getChunk();
                chunks.add(chunk);
            }
        }


        return chunks.toArray(new Chunk[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends Chunk> getReturnType() {
        return Chunk.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "all chunks between "+loc1.toString(e, debug)+" and "+loc2.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        System.out.println("LOAD");
        loc1 = (Expression<Location>) exprs[0];
        loc2 = (Expression<Location>) exprs[1];
        return true;
    }
}
