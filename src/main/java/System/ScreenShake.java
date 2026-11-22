package System;

public final class ScreenShake {
    private double elapsed;
    private double remaining;
    private double duration;
    private double strength;

    public void trigger(double duration, double strength) {
        this.duration = duration;
        this.remaining = duration;
        this.strength = strength;
        this.elapsed = 0;
    }

    public void update(double dt) {
        if (remaining <= 0) return;

        elapsed += dt;
        remaining = Math.max(0, remaining - dt);
    }

    public double getX() {
        double fade = duration <= 0 ? 0 : remaining / duration;
        return Math.sin(elapsed * 90) * strength * fade;
    }

    public double getY() {
        double fade = duration <= 0 ? 0 : remaining / duration;
        return Math.sin(elapsed * 113 + 1) * strength * fade;
    }
}