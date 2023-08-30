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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Name("Edges of chunk")
@Description("You can get the block's of edges of the Chunk")
@Examples({
        "command get chunk border:",
        "\ttrigger:",
        "\t\tloop border of player's chunk:",
        "\t\t\tloop-value"
})
@Since("1.0")

public class exprChunksEdge extends SimpleExpression<Block> {

    static {
        Skript.registerExpression(exprChunksEdge.class, Block.class, ExpressionType.SIMPLE,
                "[the] (border|edges) of %chunk% [%player%]"
        );
    }

    private Expression<Chunk> exprChunk;
    private Expression<Player> exprPlayer;

    @Override
    protected @Nullable Block @NotNull [] get(@NotNull Event e) {
        Chunk chunk = exprChunk.getSingle(e);
        Player p = exprPlayer.getSingle(e);
        if (p == null) return new Block[0];

        int y = p.getLocation().getBlockY();

        Set<Block> blocks = new HashSet<>();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (z == 15 || z == 0 || x == 15 || x == 0) {
                    assert chunk != null;
                    blocks.add(chunk.getBlock(x, y, z));
                }
            }
        }
        return blocks.toArray(new Block[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends Block> getReturnType() {
        return Block.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "edges of " + exprChunk.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        exprChunk = (Expression<Chunk>) exprs[0];
        exprPlayer = (Expression<Player>) exprs[1];
        return true;
    }
}
