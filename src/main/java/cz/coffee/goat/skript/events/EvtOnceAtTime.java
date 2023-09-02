package cz.coffee.goat.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Time;
import cz.coffee.goat.SkGoat;
import cz.coffee.goat.api.EventTimerOnce;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("unused")
public class EvtOnceAtTime extends SkriptEvent implements Comparable<EvtOnceAtTime> {

    static {
        Skript.registerEvent("Once at specific time", EvtOnceAtTime.class, EventTimerOnce.class,
                        "[the] once at [specific time] %time%")
                .description("It's called only once time at specific time")
                .examples("once at 6:00:")
                .since("1.0");
    }

    private static LocalDateTime exprTime;
    private static int TaskID = -1;
    private static final int CHECK_PERIOD = 20;
    private static final int DELAY = 0;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        String eTime = ((Literal<Time>) args[0]).getSingle().toString();
        LocalTime time = LocalTime.parse(eTime);
        LocalDate date = LocalDate.of(1, 1, 1);
        exprTime = LocalDateTime.of(date, time);
       return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEventPrioritySupported() {
        return false;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "the once at " + exprTime;
    }

    @Override
    public boolean postLoad() {
        registerListener();
        return true;
    }

    @Override
    public void unload() {
        unregisterListener();
    }

    private void registerListener() {
        EventTimerOnce et = new EventTimerOnce();
        TaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SkGoat.getInstance(), () -> {
            final LocalDateTime now = LocalDateTime.now();
            LocalDateTime exptLocalDateTime = now
                    .withHour(exprTime.getHour())
                    .withMinute(exprTime.getMinute())
                    .withSecond(0)
                    .withNano(0);

            if (now.getHour() == exptLocalDateTime.getHour() &&
                    now.getMinute() == exptLocalDateTime.getMinute() &&
                    now.getSecond() == exptLocalDateTime.getSecond()
            ) this.trigger.execute(et);
        }, DELAY, CHECK_PERIOD);
    }


    private static void unregisterListener() {
        Bukkit.getScheduler().cancelTask(TaskID);
    }

    @Override
    public int compareTo(@NotNull EvtOnceAtTime o) {
        return 0;
    }
}
