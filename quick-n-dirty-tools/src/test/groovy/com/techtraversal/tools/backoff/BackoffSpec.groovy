package com.techtraversal.tools.backoff

import spock.lang.Specification


final class BackoffSpec extends Specification {

    def "test Backoff.NONE instance"() {

        Backoff backoff = Backoff.NONE

        expect:
        0l == backoff.nextInterval()
        0l == backoff.nextInterval()
    }
}
