package com.unitconverter.flexion.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum VolumeUnitOfMeasure {
    LITERS("liters"),
    TABLESPOONS("tablespoons"),
    CUBIC_INCHES("cubic_inches"), //default
    CUPS("cups"),
    CUBIC_FEET("cubic_feet"),
    GALLONS("gallons");

    private final String displayName;
}


