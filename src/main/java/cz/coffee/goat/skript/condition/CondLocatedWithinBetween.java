package cz.coffee.goat.skript.condition;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cz.coffee.goat.api.LocationUtils;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondLocatedWithinBetween extends Condition {

    static {
        Skript.registerCondition(CondLocatedWithinBetween.class,
                "%location% [is] located within %location% and %location%",
                "%location% [is] located between %location% and %location%",
                "%location% (isn't|is not) located within %location% and %location%",
                "%location% (isn't|is not) located between %location% and %location%"
        );
    }

    private Expression<Location> loc1, loc2, loc3;
    protected boolean isBetween;
    protected boolean negated;

    @Override
    public boolean check(Event event) {
        Location l1 = loc1.getSingle(event);
        Location l2 = loc2.getSingle(event);
        Location l3 = loc3.getSingle(event);

        if (l1 == null || l2 == null || l3 == null) return false;

        if (isBetween) {
            return (!negated) == LocationUtils.isLocationBetween(l1, l2, l3);
        } else {
            return (!negated) == LocationUtils.isLocationWithin(l1, l2, l3);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "%s is located %s %s and %s".formatted(loc1.toString(event, b), isBetween ? "between" : "within", loc2.toString(event, b), loc3.toString(event, b));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        isBetween = parseResult.mark == 1 || parseResult.mark == 3;
        negated = parseResult.mark == 3 || parseResult.mark == 4;
        setNegated(negated);
        loc1 = (Expression<Location>) expressions[0];
        loc2 = (Expression<Location>) expressions[1];
        loc3 = (Expression<Location>) expressions[2];
        return true;
    }
}
