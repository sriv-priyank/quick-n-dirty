package com.techtraversal.tools.backoff

import spock.lang.Specification
import spock.lang.Unroll


@Unroll
final class ExponentialBackoffBuilderSpec extends Specification {

    def "test instance defaults"() {
        ExponentialBackoff backoff = ExponentialBackoff.instance()

        expect:
        ExponentialBackoff.DEFAULT_INITIAL_INTERVAL == backoff.getInitialInterval()
        ExponentialBackoff.DEFAULT_INITIAL_INTERVAL == backoff.getCurrentInterval()
        ExponentialBackoff.DEFAULT_FACTOR == backoff.getFactor()
        ExponentialBackoff.DEFAULT_MAX_INTERVAL == backoff.getMaxInterval()
        ExponentialBackoff.DEFAULT_MAX_ELAPSED_TIME == backoff.getMaxElapsedTime()
        0l == backoff.getElapsedTime()
    }

    def "test instance with custom values"() {
        ExponentialBackoff backoff = ExponentialBackoff.instance()
                .initialInterval(initialInterval)
                .factor(factor)
                .maxInterval(maxInterval)
                .maxElapsedTime(maxElapsedTime)

        expect:
        initialInterval == backoff.getInitialInterval()
        initialInterval == backoff.getCurrentInterval()
        factor == backoff.getFactor()
        maxInterval == backoff.getMaxInterval()
        maxElapsedTime == backoff.getMaxElapsedTime()
        0l == backoff.getElapsedTime()

        where:
        initialInterval  |  factor  |  maxInterval  |  maxElapsedTime
        1000l            |  2.0     |  30_000l      |  900_000l
    }

    def "test instance with custom values (invalid input)"() {
        when:
        ExponentialBackoff.instance()
                .initialInterval(initialInterval)
                .factor(factor)

        then:
        def e = thrown IllegalArgumentException
        exMessage.equalsIgnoreCase e.message

        where:
        initialInterval  |  factor  |  exMessage
        1000l            |  0.5     |  "Factor must be greater than or eq. to 1.0"
        0l               |  1.5     |  "Initial Interval must be greater than zero"
    }
}
