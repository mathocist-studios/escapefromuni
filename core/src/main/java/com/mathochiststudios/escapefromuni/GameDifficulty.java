package com.mathochiststudios.escapefromuni;

public enum GameDifficulty {

    EASY(10.0, 3.0, 1.5),
    NORMAL(10.0, 5.0, 1.0),
    HARD(10.0, 7.0, 0.75),
    IMPOSSIBLE(8.0, 10.0, 0.5);

    public final double baseMovementSpeed;
    public final double deanSpeed;
    public final double speedBuffMultiplier;

    GameDifficulty(double baseMovementSpeed, double deanSpeed, double speedBuffMultiplier) {
        this.baseMovementSpeed = baseMovementSpeed;
        this.deanSpeed = deanSpeed;
        this.speedBuffMultiplier = speedBuffMultiplier;
    }

    public double getBaseMovementSpeed() {
        return baseMovementSpeed;
    }

    public float getDeanSpeed() {
        return (float) deanSpeed;
    }

    public double getSpeedBuffMultiplier() {
        return speedBuffMultiplier;
    }
}
