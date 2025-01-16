package com.unitconverter.flexion.util;

import ch.qos.logback.core.util.StringUtil;
import com.unitconverter.flexion.domain.UnitOfMeasure;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.unitconverter.flexion.domain.UnitOfMeasure.*;

@Slf4j
public class UnitConversionUtil {

    public static BigDecimal convertVolumeToCubicInches(UnitOfMeasure inputUom, BigDecimal inputNumericalValue) {
        return switch (inputUom) {
            case CUPS -> inputNumericalValue.multiply(BigDecimal.valueOf(14.4375));
            case LITERS -> inputNumericalValue.multiply(BigDecimal.valueOf(61.023744));
            case TABLESPOONS -> inputNumericalValue.multiply(BigDecimal.valueOf(0.902344));
            case CUBIC_INCHES -> inputNumericalValue;
            case CUBIC_FEET -> inputNumericalValue.multiply(BigDecimal.valueOf(1728));
            case GALLONS -> inputNumericalValue.multiply(BigDecimal.valueOf(231));
            default -> throw new RuntimeException("Input UOM is not a volume UOM");
        };
    }

    public static BigDecimal convertVolumeFromCubicInches(UnitOfMeasure targetUom, BigDecimal inputNumericalValueAsCubicInches) {
        return switch (targetUom) {
            case CUPS -> inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(14.4375), RoundingMode.HALF_UP);
            case LITERS -> inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(61.023744), RoundingMode.HALF_UP);
            case TABLESPOONS -> inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(0.902344), RoundingMode.HALF_UP);
            case CUBIC_INCHES -> inputNumericalValueAsCubicInches;
            case CUBIC_FEET -> inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(1728), RoundingMode.HALF_UP);
            case GALLONS -> inputNumericalValueAsCubicInches.divide(BigDecimal.valueOf(231), RoundingMode.HALF_UP);
            default -> throw new RuntimeException("Target UOM is not a volume UOM");
        };
    }

    public static BigDecimal convertTemperatureToFahrenheit(UnitOfMeasure inputUom, BigDecimal inputNumericalValue) {
        BigDecimal numericalValueAsFahrenheit = BigDecimal.valueOf(0);
        if (KELVIN.equals(inputUom)) {
            // F = (K - 273.15) * 1.8 + 32
            numericalValueAsFahrenheit = (inputNumericalValue.subtract(BigDecimal.valueOf(273.15)).multiply(BigDecimal.valueOf(1.8)).add(BigDecimal.valueOf(32))).setScale(2, RoundingMode.HALF_UP);
        } else if (CELSIUS.equals(inputUom)) {
            // F = (C * 1.8) + 32
            numericalValueAsFahrenheit = inputNumericalValue.multiply(BigDecimal.valueOf(1.8)).add(BigDecimal.valueOf(32)).setScale(2, RoundingMode.HALF_UP);
        } else if (FAHRENHEIT.equals(inputUom)) {
            // F = F
            numericalValueAsFahrenheit = inputNumericalValue;
        } else if (RANKINE.equals(inputUom)) {
            // F = R - 459.67
            numericalValueAsFahrenheit = inputNumericalValue.subtract(BigDecimal.valueOf(459.67)).setScale(2, RoundingMode.HALF_UP);
        }

        return numericalValueAsFahrenheit;
    }

    public static BigDecimal convertTemperatureFromFahrenheit(UnitOfMeasure targetUom, BigDecimal inputNumericalValueAsFahrenheit) {
        BigDecimal numericalValueAsTargetUom = BigDecimal.valueOf(0);
        if (KELVIN.equals(targetUom)) {
            // K = (F + 459.67) / 1.8
            numericalValueAsTargetUom = (inputNumericalValueAsFahrenheit.add(BigDecimal.valueOf(459.67))).divide(BigDecimal.valueOf(1.8), 2, RoundingMode.HALF_UP);
        } else if (CELSIUS.equals(targetUom)) {
            // C = (F - 32) / 1.8
            numericalValueAsTargetUom = (inputNumericalValueAsFahrenheit.subtract(BigDecimal.valueOf(32))).divide(BigDecimal.valueOf(1.8), 2, RoundingMode.HALF_UP);
        } else if (FAHRENHEIT.equals(targetUom)) {
            // F = F
            numericalValueAsTargetUom = inputNumericalValueAsFahrenheit;
        } else if (RANKINE.equals(targetUom)) {
            // R = F + 459.67
            numericalValueAsTargetUom = inputNumericalValueAsFahrenheit.add(BigDecimal.valueOf(459.67)).setScale(2, RoundingMode.HALF_UP);
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

    /**
     * Throw IllegalArgumentException if given string is not valid
     */
    public static UnitOfMeasure parseUnitOfMeasure(String unitOfMeasure) {
        if (StringUtil.notNullNorEmpty(unitOfMeasure)) {
            var normalizedString = unitOfMeasure.toUpperCase().replace(" ", "_");
            return UnitOfMeasure.valueOf(normalizedString);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Boolean isValidUom(String unitOfMeasure) {
        try {
            parseUnitOfMeasure(unitOfMeasure);
        } catch (IllegalArgumentException e) {
            log.info("Could not parse given unit of measure {} as Unit of Measure", unitOfMeasure);
            return false;
        }
        return true;
    }
}
