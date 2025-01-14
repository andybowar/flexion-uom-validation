package com.unitconverter.flexion.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.unitconverter.flexion.domain.TemperatureUnitOfMeasure.*;
import static com.unitconverter.flexion.domain.VolumeUnitOfMeasure.*;

public class UnitConversionUtil {

    public static BigDecimal convertVolumeToCubicInches(String inputUom, BigDecimal inputNumericalValue) {
        BigDecimal numericalValueAsCubicInches = BigDecimal.valueOf(0);
        if (inputUom.equals(CUPS.getDisplayName())) {
            numericalValueAsCubicInches = inputNumericalValue.multiply(BigDecimal.valueOf(14.4375));
        } else if (inputUom.equals(LITERS.getDisplayName())) {
            numericalValueAsCubicInches = inputNumericalValue.multiply(BigDecimal.valueOf(61.023744));
        } else if (inputUom.equals(TABLESPOONS.getDisplayName())) {
            numericalValueAsCubicInches = inputNumericalValue.multiply(BigDecimal.valueOf(0.902344));
        } else if (inputUom.equals(CUBIC_INCHES.getDisplayName())) {
            numericalValueAsCubicInches = inputNumericalValue;
        } else if (inputUom.equals(CUBIC_FEET.getDisplayName())) {
            numericalValueAsCubicInches = inputNumericalValue.multiply(BigDecimal.valueOf(1728));
        } else if (inputUom.equals(GALLONS.getDisplayName())) {
            numericalValueAsCubicInches = inputNumericalValue.multiply(BigDecimal.valueOf(231));
        }
        return numericalValueAsCubicInches;
    }

    public static BigDecimal convertVolumeFromCubicInches(String targetUom, BigDecimal inputNumericalValueAsCubicInches) {
        BigDecimal numericalValueAsTargetUom = BigDecimal.valueOf(0);
        if (targetUom.equals(CUPS.getDisplayName())) {
            numericalValueAsTargetUom = inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(14.4375), RoundingMode.HALF_DOWN);
        } else if (targetUom.equals(LITERS.getDisplayName())) {
            numericalValueAsTargetUom = inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(61.023744), RoundingMode.HALF_DOWN);
        } else if (targetUom.equals(TABLESPOONS.getDisplayName())) {
            numericalValueAsTargetUom = inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(0.902344), RoundingMode.HALF_DOWN);
        } else if (targetUom.equals(CUBIC_INCHES.getDisplayName())) {
            numericalValueAsTargetUom = inputNumericalValueAsCubicInches;
        } else if (targetUom.equals(CUBIC_FEET.getDisplayName())) {
            numericalValueAsTargetUom = inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(1728), RoundingMode.HALF_DOWN);
        } else if (targetUom.equals(GALLONS.getDisplayName())) {
            numericalValueAsTargetUom = inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(231), RoundingMode.HALF_DOWN);
        }
        return numericalValueAsTargetUom;
    }

    public static BigDecimal convertTemperatureToFahrenheit(String inputUom, BigDecimal inputNumericalValue) {
        BigDecimal numericalValueAsFahrenheit = BigDecimal.valueOf(0);
        if (inputUom.equals(KELVIN.getDisplayName())) {
            // F = (K - 273.15) * 1.8 + 32
            numericalValueAsFahrenheit = (inputNumericalValue.subtract(BigDecimal.valueOf(273.15)).multiply(BigDecimal.valueOf(1.8)).add(BigDecimal.valueOf(32))).setScale(2, RoundingMode.HALF_DOWN);
        } else if (inputUom.equals(CELSIUS.getDisplayName())) {
            // F = (C * 1.8) + 32
            numericalValueAsFahrenheit = inputNumericalValue.multiply(BigDecimal.valueOf(1.8)).add(BigDecimal.valueOf(32)).setScale(2, RoundingMode.HALF_DOWN);
        } else if (inputUom.equals(FAHRENHEIT.getDisplayName())) {
            // F = F
            numericalValueAsFahrenheit = inputNumericalValue;
        } else if (inputUom.equals(RANKINE.getDisplayName())) {
            // F = R - 459.67
            numericalValueAsFahrenheit = inputNumericalValue.subtract(BigDecimal.valueOf(459.67)).setScale(2, RoundingMode.HALF_DOWN);
        }

        return numericalValueAsFahrenheit;
    }

    public static BigDecimal convertTemperatureFromFahrenheit(String targetUom, BigDecimal inputNumericalValueAsFahrenheit) {
        BigDecimal numericalValueAsTargetUom = BigDecimal.valueOf(0);
        if (targetUom.equals(KELVIN.getDisplayName())) {
            // K = (F + 459.67) / 1.8
            numericalValueAsTargetUom = (inputNumericalValueAsFahrenheit.add(BigDecimal.valueOf(459.67))).divide(BigDecimal.valueOf(1.8), 2, RoundingMode.HALF_DOWN);
        } else if (targetUom.equals(CELSIUS.getDisplayName())) {
            // C = (F - 32) / 1.8
            numericalValueAsTargetUom = (inputNumericalValueAsFahrenheit.subtract(BigDecimal.valueOf(32))).divide(BigDecimal.valueOf(1.8), 2, RoundingMode.HALF_DOWN);
        } else if (targetUom.equals(FAHRENHEIT.getDisplayName())) {
            // F = F
            numericalValueAsTargetUom = inputNumericalValueAsFahrenheit;
        } else if (targetUom.equals(RANKINE.getDisplayName())) {
            // R = F + 459.67
            numericalValueAsTargetUom = inputNumericalValueAsFahrenheit.add(BigDecimal.valueOf(459.67)).setScale(2, RoundingMode.HALF_DOWN);
        }
        return numericalValueAsTargetUom;
    }

    public static boolean equalsIgnoreScale(BigDecimal number1, BigDecimal number2) {
        if (number1 == null && number2 == null) {
            return true;
        } else if (number1 == null || number2 == null) {
            return false;
        }
        return number1.compareTo(number2) == 0;
    }
}
