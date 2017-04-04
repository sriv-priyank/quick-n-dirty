package com.techtraversal.tools.backoff

import spock.lang.Specification
import spock.lang.Unroll


@Unroll
final class ExponentialBackoffSpec extends Specification {

    def "test default backoff"() {
        ExponentialBackoff backoff = ExponentialBackoff.instance()
        def expected = [ 500l, 750l, 1_125l, 1_687l, 2_530l, 3_795l, 5_692l, 8_538l,
                         12_807l, 19_210l, 28_815l, 43_222l, 60_000l, 60_000l ]
        def computed = []

        when:
        expected.each {
            computed << backoff.nextInterval()
        }

        then:
        computed == expected
    }


    def "test backoff with custom values"() {
        ExponentialBackoff backoff = ExponentialBackoff.instance()
                .initialInterval(initialInterval)
                .factor(factor)
                .maxInterval(maxInterval)
                .maxElapsedTime(maxElapsedTime)

        def computed = []

        when:
        expected.each {
            computed << backoff.nextInterval()
        }

        then:
        computed == expected

        where:
        initialInterval  |  factor  |  maxInterval  |  maxElapsedTime  ||  expected
        1000l            |  2.0     |  15_000l      |  180_000l        ||  [ 1000l, 2_000l, 4_000l, 8_000l, 15_000l, 15_000l ]
        1000l            |  2.5     |  5_000l       |  15_000l         ||  [ 1000l, 2_500l, 5_000l, 5_000l, Backoff.STOP ]
    }


    def "test backoff reset"() {
        ExponentialBackoff backoff = ExponentialBackoff.instance()
        def expected = [ 500l, 750l ]
        def computed = []

        when:
        (1..2).each { backoff.nextInterval() }

        then:
        1_125l == backoff.getCurrentInterval()

        when:
        backoff.reset()
        expected.each {
            computed << backoff.nextInterval()
        }

        then:
        computed == expected
    }


    def "test backoff with interval overflow"() {
        long initialInterval = Long.MAX_VALUE / 2
        ExponentialBackoff backoff = ExponentialBackoff.instance()
                .initialInterval(initialInterval)
                .factor(2.3)
                .maxInterval(Long.MAX_VALUE)

        when:
        backoff.nextInterval()

        then:
        Long.MAX_VALUE == backoff.getCurrentInterval()
    }
}
