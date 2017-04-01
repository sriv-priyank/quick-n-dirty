package com.techtraversal.tools.backoff;


public final class ExponentialBackoff implements Backoff {

    @Override
    public final void reset() {

    }

    @Override
    public final long nextInterval() {
        return 0;
    }
}
