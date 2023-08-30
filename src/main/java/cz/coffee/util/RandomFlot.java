package cz.coffee.util;

import java.util.Random;

public class RandomFlot {
    private double currentValue;
    private final double minValue = 6.0;
    private final double maxValue = 20.0;
    private final Random random = new Random();

    public RandomFlot() {
        currentValue = random.nextDouble() * (maxValue - minValue) + minValue;
    }

    public double getNextValue() {
        double minChange = 0.1;
        double maxChange = 1.9;
        double change = random.nextDouble() * (maxChange - minChange) + minChange;
        if (random.nextBoolean()) {
            currentValue += change;
        } else {
            currentValue -= change;
        }
        if (currentValue > maxValue) {
            currentValue = 2 * maxValue - currentValue;
        } else if (currentValue < minValue) {
            currentValue = 2 * minValue - currentValue;
        }
        return currentValue;
    }
}
