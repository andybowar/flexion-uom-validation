package com.unitconverter.flexion.service;

import com.unitconverter.flexion.domain.TemperatureUnitOfMeasure;
import com.unitconverter.flexion.domain.VolumeUnitOfMeasure;
import com.unitconverter.flexion.dto.UnitOfMeasureConversionDto;
import com.unitconverter.flexion.dto.UnitOfMeasureConversionWriteDto;
import com.unitconverter.flexion.util.UnitConversionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import static com.unitconverter.flexion.domain.VolumeUnitOfMeasure.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnitOfMeasureConversionValidationService {

    /*
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
        if (dto.inputUom.contains(" ")) {
            dto.inputUom = dto.inputUom.replace(" ", "_");
        }
        if (dto.targetUom.contains(" ")) {
            dto.targetUom = dto.targetUom.replace(" ", "_");
        }
        var isInputUomVolume = EnumUtils.isValidEnumIgnoreCase(VolumeUnitOfMeasure.class, dto.inputUom);
        var isInputUomTemperature = EnumUtils.isValidEnumIgnoreCase(TemperatureUnitOfMeasure.class, dto.inputUom);
        var isTargetUomVolume = EnumUtils.isValidEnumIgnoreCase(VolumeUnitOfMeasure.class, dto.targetUom);
        var isTargetUomTemperature = EnumUtils.isValidEnumIgnoreCase(TemperatureUnitOfMeasure.class, dto.targetUom);

        var inputAndTargetAreVolume = isInputUomVolume && isTargetUomVolume;
        var inputAndTargetAreTemperature = isInputUomTemperature && isTargetUomTemperature;

        var conversionDto = new UnitOfMeasureConversionDto();

        // Return "invalid" if both UOMs are invalid
        if (!isInputUomVolume
                && !isTargetUomVolume
                && !isInputUomTemperature
                && !isTargetUomTemperature) {
            conversionDto.validationOutput = "invalid";
            return conversionDto;
        }

        // Returns "invalid" if one UOM is valid but the other isn't
        if (isInputUomVolume && !isTargetUomVolume) {
            conversionDto.validationOutput = "invalid";
            return conversionDto;
        } else if (!isInputUomVolume && isTargetUomVolume) {
            conversionDto.validationOutput = "invalid";
            return conversionDto;
        }

        // Returns "incorrect" if student answer can't be parsed as a number
        try {
            Double.parseDouble(dto.studentAnswer);
        } catch (NumberFormatException e) {
            log.error("Could not parse student answer to number format. Returning incorrect as answer evaluation.", e);
            conversionDto.validationOutput = "incorrect";
            return conversionDto;
        }

        if (inputAndTargetAreVolume) {
            // Calculate inputNumericalValue conversion to cubic inches
            BigDecimal numericalValueAsCubicInches = UnitConversionUtil.convertVolumeToCubicInches(dto.inputUom.toLowerCase(), dto.inputNumericalValue);

            validateVolumeConversion(numericalValueAsCubicInches, dto.targetUom.toLowerCase(), BigDecimal.valueOf(Double.parseDouble(dto.studentAnswer)), conversionDto);
        }

        // Returns "invalid" if one UOM is valid but the other isn't
        if (isInputUomTemperature && !isTargetUomTemperature) {
            conversionDto.validationOutput = "invalid";
            return conversionDto;
        } else if (!isInputUomTemperature && isTargetUomTemperature) {
            conversionDto.validationOutput = "invalid";
            return conversionDto;
        }

        if (inputAndTargetAreTemperature) {
            // Calculate inputNumericalValue conversion to base UOM
            BigDecimal numericalValueAsFahrenheit = UnitConversionUtil.convertTemperatureToFahrenheit(dto.inputUom.toLowerCase(), dto.inputNumericalValue);

            validateTemperatureConversion(numericalValueAsFahrenheit, dto.targetUom.toLowerCase(), BigDecimal.valueOf(Double.parseDouble(dto.studentAnswer)), conversionDto);
        }

        return conversionDto;
    }

    private void validateVolumeConversion(BigDecimal inputNumericalValueAsCubicInches, String targetUom, BigDecimal studentAnswer, UnitOfMeasureConversionDto conversionDto) {
        BigDecimal numericalValueAsTargetUom = UnitConversionUtil.convertVolumeFromCubicInches(targetUom, inputNumericalValueAsCubicInches);
        if (UnitConversionUtil.equalsIgnoreScale(numericalValueAsTargetUom.setScale(1, RoundingMode.CEILING), studentAnswer.setScale(1, RoundingMode.CEILING))) {
            conversionDto.validationOutput = "correct";
        } else {
            log.info("An incorrect answer has been provided for volume conversion. The correct answer is: {}", numericalValueAsTargetUom);
            conversionDto.validationOutput = "incorrect";
        }
    }

    private void validateTemperatureConversion(BigDecimal inputNumericalValueAsFahrenheit, String targetUom, BigDecimal studentAnswer, UnitOfMeasureConversionDto conversionDto) {
        BigDecimal numericalValueAsTargetUom = UnitConversionUtil.convertTemperatureFromFahrenheit(targetUom, inputNumericalValueAsFahrenheit);
        if (UnitConversionUtil.equalsIgnoreScale(numericalValueAsTargetUom.setScale(1, RoundingMode.HALF_UP), studentAnswer.setScale(1, RoundingMode.HALF_UP))) {
            conversionDto.validationOutput = "correct";
        } else {
            log.info("An incorrect answer has been provided for temperature conversion. The correct answer is: {}", numericalValueAsTargetUom);
            conversionDto.validationOutput = "incorrect";
        }
    }
}
