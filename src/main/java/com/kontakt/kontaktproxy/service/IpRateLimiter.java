package com.kontakt.kontaktproxy.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IpRateLimiter {
    private final Map<String, Bucket> rateLimits = new ConcurrentHashMap<>();

    @Value("${rate.limiter.requests-per-second}")
    private int requestsPerSecond;

    public boolean allowRequest(String ipAddress) {
        rateLimits.putIfAbsent(ipAddress, createBucket());

        Bucket bucket = rateLimits.get(ipAddress);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        System.out.println(ipAddress + " - " + probe.getRemainingTokens());

        return probe.isConsumed();
    }

    private Bucket createBucket() {
        Bandwidth limitBandwidth = Bandwidth.classic(requestsPerSecond, Refill.intervally(requestsPerSecond, Duration.ofSeconds(1)));
        return Bucket.builder().addLimit(limitBandwidth).build();
    }
}


