package com.techtraversal.tools.backoff;


public interface Backoff {

    static final long STOP = -1L;

    /** Reset to initial state. */
    void reset();

    long nextInterval();
}
