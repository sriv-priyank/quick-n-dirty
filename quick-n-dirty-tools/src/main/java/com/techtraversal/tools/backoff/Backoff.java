package com.techtraversal.tools.backoff;

import com.techtraversal.tools.tmp.BackOff;


public interface Backoff {

    long STOP = -1L;

    void reset();

    long nextInterval();

    BackOff NONE = new BackOff() {

        public void reset() {}

        public long nextBackOffMillis() {
            return 0;
        }
    };
}
