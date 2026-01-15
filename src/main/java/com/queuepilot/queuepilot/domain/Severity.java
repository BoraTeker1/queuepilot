package com.queuepilot.queuepilot.domain;

/**
 * Enumeration of severity levels for events and incidents.
 * P0 is the highest severity, P3 is the lowest.
 */
public enum Severity {
    P0,  // Critical - immediate attention required
    P1,  // High - urgent attention required
    P2,  // Medium - attention required soon
    P3   // Low - can be handled during normal business hours
}
