package com.unitconverter.flexion.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UnitOfMeasure {
    KELVIN(UomType.TEMPERATURE),
    CELSIUS(UomType.TEMPERATURE),
    FAHRENHEIT(UomType.TEMPERATURE), // default
    RANKINE(UomType.TEMPERATURE),

    LITERS(UomType.VOLUME),
    TABLESPOONS(UomType.VOLUME),
    CUBIC_INCHES(UomType.VOLUME), //default
    CUPS(UomType.VOLUME),
    CUBIC_FEET(UomType.VOLUME),
    GALLONS(UomType.VOLUME);

    private final UomType uomType;
}
