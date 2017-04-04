package com.techtraversal.tools.backoff;


public final class ExponentialBackoff implements Backoff {

    // all intervals are in milliseconds

    public static final long   DEFAULT_INITIAL_INTERVAL    = 500l;     // .5 second
    public static final double DEFAULT_FACTOR              = 1.5d;
    public static final long   DEFAULT_MAX_INTERVAL        = 60_000l;  //  1 minute
    public static final long   DEFAULT_MAX_ELAPSED_TIME    = 600_000l; // 10 minutes

    private long initialInterval, maxInterval, currentInterval;
    private double factor;
    private long elapsedTime, maxElapsedTime;


    private ExponentialBackoff() {
        this.initialInterval = DEFAULT_INITIAL_INTERVAL;
        this.factor = DEFAULT_FACTOR;
        this.maxInterval = DEFAULT_MAX_INTERVAL;
        this.maxElapsedTime = DEFAULT_MAX_ELAPSED_TIME;
        reset();
    }

    @Override
    public final void reset() {
        this.currentInterval = this.initialInterval;
        this.elapsedTime = 0l;
    }

    @Override
    public final long nextInterval() {
        if (this.elapsedTime >= this.maxElapsedTime) {
            return STOP;
        }

        long curr = this.currentInterval;
        if (this.currentInterval >= this.maxInterval / this.factor)
            this.currentInterval = this.maxInterval;
        else
            this.currentInterval *= this.factor;

        this.elapsedTime += this.currentInterval;
        return curr;
    }

    /////////////////////////////////////////////////////////////////

    public static ExponentialBackoff instance() {
        return new ExponentialBackoff();
    }

    public ExponentialBackoff initialInterval(final long initialInterval) {
        if (initialInterval <= 0)
            throw new IllegalArgumentException("Initial Interval must be greater than zero");
        this.initialInterval = initialInterval;
        this.currentInterval = initialInterval;
        return this;
    }

    public ExponentialBackoff factor(final double factor) {
        if (factor < 1.0)
            throw new IllegalArgumentException("Factor must be greater than or eq. to 1.0");
        this.factor = factor;
        return this;
    }

    public ExponentialBackoff maxInterval(final long maxInterval) {
        this.maxInterval = maxInterval;
        return this;
    }

    public ExponentialBackoff maxElapsedTime(final long maxElapsedTime) {
        this.maxElapsedTime = maxElapsedTime;
        return this;
    }

    /////////////////////////////////////////////////////////////////

    public long getInitialInterval() {
        return this.initialInterval;
    }

    public long getCurrentInterval() {
        return this.currentInterval;
    }

    public double getFactor() {
        return this.factor;
    }

    public long getMaxInterval() {
        return this.maxInterval;
    }

    public long getMaxElapsedTime() {
        return this.maxElapsedTime;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }
}
