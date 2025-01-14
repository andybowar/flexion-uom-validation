package com.unitconverter.flexion.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TemperatureUnitOfMeasure {
    KELVIN("kelvin"),
    CELSIUS("celsius"),
    FAHRENHEIT("fahrenheit"), // default
    RANKINE("rankine");

    private final String displayName;
}
