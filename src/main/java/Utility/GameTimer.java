package Utility;

public class GameTimer {
    private double duration;
    private double remaining;
    private boolean repeating;
    private boolean running;

    public GameTimer(double duration, boolean repeating, boolean startRunning) {
        this.duration = Math.max(0.0, duration);
        this.repeating = repeating;
        this.remaining = this.duration;
        this.running = startRunning;
    }

    public static GameTimer countdown(double seconds) {
        return new GameTimer(seconds, false, true);
    }

    public static GameTimer every(double seconds) {
        return new GameTimer(seconds, true, true);
    }

    /**
     * Advances the timer by dt (seconds).
     * Returns true whenever the timer finishes (one-shot) or triggers a tick (repeating).
     */
    public boolean update(double dt) {
        if (!running || dt <= 0.0) {
            return false;
        }

        remaining -= dt;
        if (remaining > 0.0) {
            return false;
        }

        if (repeating) {
            if (duration > 0.0) {
                remaining += duration;
                // Guard against huge dt spikes firing dozens of bursts at once
                if (remaining < -duration) {
                    remaining = 0.0;
                }
            } else {
                remaining = 0.0;
            }
            return true;
        } else {
            remaining = 0.0;
            running = false;
            return true;
        }
    }

    public void reset() {
        this.remaining = this.duration;
        this.running = true;
    }

    public void restart(double newDuration) {
        this.duration = Math.max(0.0, newDuration);
        this.remaining = this.duration;
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public double getRemaining() {
        return remaining;
    }

    public double getProgress() {
        if (duration <= 0.0) return 1.0;
        return 1.0 - (remaining / duration);
    }
}