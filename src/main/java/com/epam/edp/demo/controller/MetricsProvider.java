package com.epam.edp.demo.controller;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * Task 11
 */
@Component
public class MetricsProvider {
    private final MeterRegistry meterRegistry;

    public MetricsProvider(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerCustomMetrics() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        Gauge.builder("jvm_memory_usage_647f9884", memoryMXBean, memory -> {
                    MemoryUsage heapMemoryUsage = memory.getHeapMemoryUsage();
                    return (double) heapMemoryUsage.getUsed() / heapMemoryUsage.getMax();
                })
                .description("JVM memory usage with fixed metric name")
                .register(meterRegistry);
    }
}
