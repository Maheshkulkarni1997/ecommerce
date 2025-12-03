package org.ecommerce.enums;

import lombok.Getter;

@Getter
public enum PolicyType {
    HEALTH("Health"),
    DENTAL("Dental"),
    VISION("Vision"),
    CRITICAL_ILLNESS("Critical Illness"),
    MATERNITY("Maternity"),
    TRAVEL("Travel"),
    ACCIDENT("Accident"),
    EMERGENCY("Emergency"),
    WELLNESS("Wellness"),
    OTHER("Other");

    private final String displayName;

    PolicyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
