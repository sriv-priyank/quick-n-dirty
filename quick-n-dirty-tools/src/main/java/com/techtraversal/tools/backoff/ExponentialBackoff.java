package com.techtraversal.tools.backoff;


public final class ExponentialBackoff implements Backoff {

    // all intervals are in milliseconds

    private static final long   DEFAULT_INITIAL_INTERVAL    = 500L;
    private static final double DEFAULT_FACTOR              = 1.5d;
    private static final long   DEFAULT_MAX_INTERVAL        = 60000l;

    private long initialInterval, maxInterval, currentInterval;
    private double factor;


    private ExponentialBackoff() {
        this.initialInterval = DEFAULT_INITIAL_INTERVAL;
        this.factor = DEFAULT_FACTOR;
        this.maxInterval = DEFAULT_MAX_INTERVAL;
    }

    @Override
    public final void reset() {
        this.currentInterval = this.initialInterval;
    }

    @Override
    public final long nextInterval() {
        if (this.currentInterval >= this.maxInterval / this.factor)
            this.currentInterval = this.maxInterval;
        else
            this.currentInterval *= this.factor;

        return this.currentInterval;
    }

    /////////////////////////////////////////////////////////////////

    public static ExponentialBackoff instance() {
        return new ExponentialBackoff();
    }

    public ExponentialBackoff initialInterval(final long initialInterval) {
        this.initialInterval = initialInterval;
        return this;
    }

    public ExponentialBackoff factor(final double factor) {
        this.factor = factor;
        return this;
    }

    public ExponentialBackoff maxInterval(final long maxInterval) {
        this.maxInterval = maxInterval;
        return this;
    }

    /////////////////////////////////////////////////////////////////

    public long getInitialInterval() {
        return this.initialInterval;
    }

    public double getFactor() {
        return this.factor;
    }

    public long getMaxInterval() {
        return this.maxInterval;
    }
}
