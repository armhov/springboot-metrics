package com.epam.edp.demo.controller;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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
    private final String studentName;

    public MetricsProvider(MeterRegistry meterRegistry, @Value("${student.name:default}") String studentName) {
        this.meterRegistry = meterRegistry;
        this.studentName = studentName;
    }

    @PostConstruct
    public void registerCustomMetrics() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        Gauge.builder("jvm_memory_usage_" + studentName, memoryMXBean, memory -> {
                    MemoryUsage heapMemoryUsage = memory.getHeapMemoryUsage();
                    return (double) heapMemoryUsage.getUsed() / heapMemoryUsage.getMax();
                })
                .description("JVM memory usage with student name")
                .register(meterRegistry);
    }
}
