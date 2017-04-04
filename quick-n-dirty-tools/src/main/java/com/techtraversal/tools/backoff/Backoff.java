package com.techtraversal.tools.backoff;

public interface Backoff {

    long STOP = -1L;

    void reset();

    long nextInterval();

    Backoff NONE = new Backoff() {

        public void reset() {}

        public long nextInterval() {
            return 0;
        }
    };
}
