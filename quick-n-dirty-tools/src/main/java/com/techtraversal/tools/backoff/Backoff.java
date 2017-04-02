package com.techtraversal.tools.backoff;


import com.techtraversal.tools.tmp.BackOff;

import java.io.IOException;

public interface Backoff {

    static final long STOP = -1L;

    void reset();

    long nextInterval();

    BackOff NONE = new BackOff() {

        public void reset() {}

        public long nextBackOffMillis() {
            return 0;
        }
    };
}
