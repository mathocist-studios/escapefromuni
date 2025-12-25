package com.mathochiststudios.escapefromuni;

public enum GameDifficulty {

    EASY(7.0, 3.0, 1.5, 7.0),
    NORMAL(7.0, 5.0, 1.0, 5.0),
    HARD(7.0, 7.0, 0.75, 5.0),
    IMPOSSIBLE(5.0, 10.0, 0.5, 3.0);

    private final double baseMovementSpeed;
    private final double deanSpeed;
    private final double speedBuffMultiplier;
    private final double friendSpeed;

    GameDifficulty(double baseMovementSpeed, double deanSpeed, double speedBuffMultiplier, double friendSpeed) {
        this.baseMovementSpeed = baseMovementSpeed;
        this.deanSpeed = deanSpeed;
        this.speedBuffMultiplier = speedBuffMultiplier;
        this.friendSpeed = friendSpeed;
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

    public float getFriendSpeed() {
        return (float) friendSpeed;
    }
}
