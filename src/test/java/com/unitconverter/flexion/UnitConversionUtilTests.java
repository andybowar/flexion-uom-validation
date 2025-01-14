package com.unitconverter.flexion;

import com.unitconverter.flexion.util.UnitConversionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UnitConversionUtilTests {

	@Test
	void cupsUom_shouldConvertToCubicInches() {
        var result = UnitConversionUtil.convertVolumeToCubicInches("cups", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(14.4375), result);
	}

	@Test
	void litersUom_shouldConvertToCubicInches() {
        var result = UnitConversionUtil.convertVolumeToCubicInches("liters", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(61.023744), result);
	}

	@Test
	void tablespoonsUom_shouldConvertToCubicInches() {
        var result = UnitConversionUtil.convertVolumeToCubicInches("tablespoons", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(0.902344), result);
	}

	@Test
	void cubicInchesUom_shouldNotConvertTo() {
        var result = UnitConversionUtil.convertVolumeToCubicInches("cubic_inches", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(1), result);
	}

	@Test
	void cubicFeetUom_shouldConvertToCubicFeet() {
        var result = UnitConversionUtil.convertVolumeToCubicInches("cubic_feet", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(1728), result);
	}

	@Test
	void gallonsUom_shouldConvertToCubicInches() {
        var result = UnitConversionUtil.convertVolumeToCubicInches("gallons", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(231), result);
	}

	@Test
	void cupsUom_shouldConvertFromCubicInches() {
		var result = UnitConversionUtil.convertVolumeFromCubicInches("cups", BigDecimal.valueOf(14.4375));
		UnitConversionUtil.equalsIgnoreScale(BigDecimal.valueOf(1), result);
	}

	@Test
	void litersUom_shouldConvertFromCubicInches() {
		var result = UnitConversionUtil.convertVolumeFromCubicInches("liters", BigDecimal.valueOf(61.023744));
		UnitConversionUtil.equalsIgnoreScale(BigDecimal.valueOf(1), result);
	}

	@Test
	void tablespoonsUom_shouldConvertFromCubicInches() {
		var result = UnitConversionUtil.convertVolumeFromCubicInches("tablespoons", BigDecimal.valueOf(0.902344));
		UnitConversionUtil.equalsIgnoreScale(BigDecimal.valueOf(1), result);
	}

	@Test
	void cubicInchesUom_shouldNotConvertFrom() {
		var result = UnitConversionUtil.convertVolumeFromCubicInches("cubic_inches", BigDecimal.valueOf(1));
		assertEquals(BigDecimal.valueOf(1), result);
	}

	@Test
	void cubicFeetUom_shouldConvertFromCubicFeet() {
		var result = UnitConversionUtil.convertVolumeFromCubicInches("cubic_feet", BigDecimal.valueOf(1728));
		assertEquals(BigDecimal.valueOf(1), result);
	}

	@Test
	void gallonsUom_shouldConvertFromCubicInches() {
		var result = UnitConversionUtil.convertVolumeFromCubicInches("gallons", BigDecimal.valueOf(231));
		assertEquals(BigDecimal.valueOf(1), result);
	}

	@Test
	void kelvinUom_shouldConvertToFahrenheit() {
		var result = UnitConversionUtil.convertTemperatureToFahrenheit("kelvin", BigDecimal.valueOf(1));
		assertEquals(BigDecimal.valueOf(-457.87), result);
	}

	@Test
	void celsiusUom_shouldConvertToFahrenheit() {
		var result = UnitConversionUtil.convertTemperatureToFahrenheit("celsius", BigDecimal.valueOf(0));
		assertEquals(BigDecimal.valueOf(32.00).setScale(2, RoundingMode.HALF_DOWN), result);
	}

	@Test
	void fahrenheitUom_shouldNotConvertTo() {
		var result = UnitConversionUtil.convertTemperatureToFahrenheit("fahrenheit", BigDecimal.valueOf(1));
		assertEquals(BigDecimal.valueOf(1), result);
	}

	@Test
	void rankineUom_shouldConvertToFahrenheit() {
		var result = UnitConversionUtil.convertTemperatureToFahrenheit("rankine", BigDecimal.valueOf(1));
		assertEquals(BigDecimal.valueOf(-458.67), result);
	}

	@Test
	void kelvinUom_shouldConvertFromFahrenheit() {
		var result = UnitConversionUtil.convertTemperatureFromFahrenheit("kelvin", BigDecimal.valueOf(-457.87));
		assertEquals(BigDecimal.valueOf(1.00).setScale(2, RoundingMode.HALF_DOWN), result);
	}

	@Test
	void celsiusUom_shouldConvertFromFahrenheit() {
		var result = UnitConversionUtil.convertTemperatureFromFahrenheit("celsius", BigDecimal.valueOf(32.00));
		assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_DOWN), result);
	}

	@Test
	void fahrenheitUom_shouldNotConvertFrom() {
		var result = UnitConversionUtil.convertTemperatureFromFahrenheit("fahrenheit", BigDecimal.valueOf(1));
		assertEquals(BigDecimal.valueOf(1), result);
	}

	@Test
	void rankineUom_shouldConvertFromFahrenheit() {
		var result = UnitConversionUtil.convertTemperatureFromFahrenheit("rankine", BigDecimal.valueOf(-458.67));
		assertEquals(BigDecimal.valueOf(1.00).setScale(2, RoundingMode.HALF_DOWN), result);
	}
}
