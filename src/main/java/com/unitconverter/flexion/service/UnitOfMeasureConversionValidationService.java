package com.unitconverter.flexion.service;

import com.unitconverter.flexion.domain.UnitOfMeasure;
import com.unitconverter.flexion.domain.UomType;
import com.unitconverter.flexion.dto.UnitOfMeasureConversionDto;
import com.unitconverter.flexion.dto.UnitOfMeasureConversionWriteDto;
import com.unitconverter.flexion.util.UnitConversionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnitOfMeasureConversionValidationService {

    /**
     Top-level method validates incoming data
     - If UOMs are of different types OR one or both UOMs are not valid UOMs, return "invalid"
     - If student response is not numerical, return "incorrect"

     For each UOM type, a UOM was chosen to be the "base" UOM (cubic inches for volume, Fahrenheit for temperature.
     Every incoming inputNumericalValue is converted to the "base UOM" according to the inputUom's type.
     That number is then passed to the private method for that UOM type, where it is then converted to the
     target UOM and compared to the studentResponse value.

     e.g. If the user's input is 243 Kelvin and the target UOM is Celsius, we immediately convert that to
     Fahrenheit, send that number into the validation method, and convert it to Celsius, effectively converting
     from Kelvin to Fahrenheit to Celsius. Then we check that number against the studentResponse number.

     This makes the code a bit more simple, as it prevents us from having to create a separate case for every
     possible input and target UOM combination.
     */
    public UnitOfMeasureConversionDto uomConversionValidation(UnitOfMeasureConversionWriteDto dto) {

        if (!UnitConversionUtil.isValidUom(dto.inputUom) || !UnitConversionUtil.isValidUom(dto.targetUom)) {
            return new UnitOfMeasureConversionDto("invalid");
        }

        var inputUom = UnitConversionUtil.parseUnitOfMeasure(dto.inputUom);
        var targetUom = UnitConversionUtil.parseUnitOfMeasure(dto.targetUom);

        if (!inputUom.getUomType().equals(targetUom.getUomType())) {
            return new UnitOfMeasureConversionDto("invalid");
        }

        // Returns "incorrect" if student answer can't be parsed as a number
        try {
            Double.parseDouble(dto.studentAnswer);
        } catch (NumberFormatException e) {
            log.error("Could not parse student answer to number format. Returning incorrect as answer evaluation.", e);
            return new UnitOfMeasureConversionDto("incorrect");
        }

        var correct = false;
        if (UomType.VOLUME.equals(inputUom.getUomType())) {
            // Calculate inputNumericalValue conversion to cubic inches
            BigDecimal numericalValueAsCubicInches = UnitConversionUtil.convertVolumeToCubicInches(inputUom, dto.inputNumericalValue);

            correct = validateVolumeConversion(numericalValueAsCubicInches, targetUom, BigDecimal.valueOf(Double.parseDouble(dto.studentAnswer)));
        } else {
            // Calculate inputNumericalValue conversion to base UOM
            BigDecimal numericalValueAsFahrenheit = UnitConversionUtil.convertTemperatureToFahrenheit(inputUom, dto.inputNumericalValue);

            correct = validateTemperatureConversion(numericalValueAsFahrenheit, targetUom, BigDecimal.valueOf(Double.parseDouble(dto.studentAnswer)));
        }

        var output = correct ? "correct" : "incorrect";
        return new UnitOfMeasureConversionDto(output);
    }

    private boolean validateVolumeConversion(BigDecimal inputNumericalValueAsCubicInches, UnitOfMeasure targetUom, BigDecimal studentAnswer) {
        BigDecimal numericalValueAsTargetUom = UnitConversionUtil.convertVolumeFromCubicInches(targetUom, inputNumericalValueAsCubicInches);
        return UnitConversionUtil.equalsIgnoreScale(numericalValueAsTargetUom.setScale(1, RoundingMode.CEILING), studentAnswer.setScale(1, RoundingMode.CEILING));
    }

    private boolean validateTemperatureConversion(BigDecimal inputNumericalValueAsFahrenheit, UnitOfMeasure targetUom, BigDecimal studentAnswer) {
        BigDecimal numericalValueAsTargetUom = UnitConversionUtil.convertTemperatureFromFahrenheit(targetUom, inputNumericalValueAsFahrenheit);
        return UnitConversionUtil.equalsIgnoreScale(numericalValueAsTargetUom.setScale(1, RoundingMode.HALF_UP), studentAnswer.setScale(1, RoundingMode.HALF_UP));
    }
}
