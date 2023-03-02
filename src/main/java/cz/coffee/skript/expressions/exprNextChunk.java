package cz.coffee.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Name("Chunks next to chunk at location")
@Since("1.0")
@Examples("on player move:\n" +
        "    execute player command \"chunk\"\n" +
        "\n" +
        "command setCH:\n" +
        "    trigger:\n" +
        "        set {chunks::*} to chunks next directly attached to player's chunk\n" +
        "\n" +
        "\n" +
        "command chunk:\n" +
        "    trigger:\n" +
        "        {chunks::*} contains player's chunk:\n" +
        "            send \"&acorrect\"\n" +
        "            send player's chunk\n" +
        "        else:\n" +
        "            send \"&cincorrect &f%player's chunk%\""
)

public class exprNextChunk extends SimpleExpression<Chunk> {

    static {
        Skript.registerExpression(exprNextChunk.class, Chunk.class, ExpressionType.SIMPLE,
                "[all] chunks (:directly attached|:next [directly attached]) to %chunk%"
        );
    }

    private static Chunk[] getDAChunks(Chunk chunk) {
        if (chunk == null) return null;
        int mX = chunk.getX();
        int mZ = chunk.getZ();

        return new Chunk[]{
                chunk.getWorld().getChunkAt(mX+1, mZ),
                chunk.getWorld().getChunkAt(mX, mZ+1),
                chunk.getWorld().getChunkAt(mX-1, mZ),
                chunk.getWorld().getChunkAt(mX, mZ-1)
        };
    }

    private static Chunk[] getNChunks(Chunk chunk) {
        if (chunk == null) return null;
        int mX = chunk.getX();
        int mZ = chunk.getZ();

        return new Chunk[]{
                chunk.getWorld().getChunkAt(mX+1, mZ+1),
                chunk.getWorld().getChunkAt(mX+1, mZ-1),
                chunk.getWorld().getChunkAt(mX-1, mZ+1),
                chunk.getWorld().getChunkAt(mX-1, mZ-1)
        };
    }


    private Expression<Chunk> mChunk;
    private boolean isNext = false, isAttached = false;

    @Override
    protected @Nullable Chunk @NotNull [] get(@NotNull Event e) {
        Chunk mainChunk = mChunk.getSingle(e);
        Set<Chunk> chunks = new HashSet<>();
        if (mainChunk == null) return new Chunk[0];

        if (isAttached) {
            chunks.addAll(Arrays.asList(getDAChunks(mainChunk)));
        } else if (isNext) {
            chunks.addAll(Arrays.asList(getDAChunks(mainChunk)));
            chunks.addAll(Arrays.asList(getNChunks(mainChunk)));
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
        return "chunks chunks " + (isNext ? "next" : "") + (isAttached ? "directly attached" : "") + " to " + mChunk.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parseResult) {
        isNext = parseResult.hasTag("next");
        isAttached = parseResult.hasTag("directly attached");
        mChunk = (Expression<Chunk>) exprs[0];
        return true;
    }
}
