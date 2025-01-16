package com.unitconverter.flexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitconverter.flexion.dto.UnitOfMeasureConversionWriteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UnitOfMeasureConversionValidationControllerIT {

    @Autowired
    protected MockMvc mockMvc;

    /**
     * Tests {@link com.unitconverter.flexion.web.UnitOfMeasureConversionValidationController#postFacilityItemInventoryDetails(UnitOfMeasureConversionWriteDto)}
     */
    @Test
    void convertFahrenheitToRankine_shouldReturnCorrect() throws Exception {

        var uomConversionWriteDto = new UnitOfMeasureConversionWriteDto();
        uomConversionWriteDto.inputNumericalValue = BigDecimal.valueOf(84.2);
        uomConversionWriteDto.inputUom = "Fahrenheit";
        uomConversionWriteDto.targetUom = "Rankine";
        uomConversionWriteDto.studentAnswer = "543.87";

        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(uomConversionWriteDto);

        mockMvc.perform(
                        post("/uom-conversion-validation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validationOutput").value("correct"));
    }

    /**
     * Tests {@link com.unitconverter.flexion.web.UnitOfMeasureConversionValidationController#postFacilityItemInventoryDetails(UnitOfMeasureConversionWriteDto)}
     */
    @Test
    void convertKelvinToFahrenheit_shouldReturnIncorrect() throws Exception {

        var uomConversionWriteDto = new UnitOfMeasureConversionWriteDto();
        uomConversionWriteDto.inputNumericalValue = BigDecimal.valueOf(317.33);
        uomConversionWriteDto.inputUom = "Kelvin";
        uomConversionWriteDto.targetUom = "Fahrenheit";
        uomConversionWriteDto.studentAnswer = "111.554";

        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(uomConversionWriteDto);

        mockMvc.perform(
                        post("/uom-conversion-validation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validationOutput").value("incorrect"));
    }

    /**
     * Tests {@link com.unitconverter.flexion.web.UnitOfMeasureConversionValidationController#postFacilityItemInventoryDetails(UnitOfMeasureConversionWriteDto)}
     */
    @Test
    void convertCupsToLiters_shouldReturnCorrect() throws Exception {

        var uomConversionWriteDto = new UnitOfMeasureConversionWriteDto();
        uomConversionWriteDto.inputNumericalValue = BigDecimal.valueOf(25.6);
        uomConversionWriteDto.inputUom = "Cups";
        uomConversionWriteDto.targetUom = "Liters";
        uomConversionWriteDto.studentAnswer = "6.1";

        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(uomConversionWriteDto);

        mockMvc.perform(
                        post("/uom-conversion-validation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validationOutput").value("correct"));
    }

    /**
     * Tests {@link com.unitconverter.flexion.web.UnitOfMeasureConversionValidationController#postFacilityItemInventoryDetails(UnitOfMeasureConversionWriteDto)}
     */
    @Test
    void convertGallonsToKelvin_shouldReturnInvalid() throws Exception {

        var uomConversionWriteDto = new UnitOfMeasureConversionWriteDto();
        uomConversionWriteDto.inputNumericalValue = BigDecimal.valueOf(73.12);
        uomConversionWriteDto.inputUom = "Gallons";
        uomConversionWriteDto.targetUom = "Kelvin";
        uomConversionWriteDto.studentAnswer = "19.4";

        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(uomConversionWriteDto);

        mockMvc.perform(
                        post("/uom-conversion-validation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validationOutput").value("invalid"));
    }

    /**
     * Tests {@link com.unitconverter.flexion.web.UnitOfMeasureConversionValidationController#postFacilityItemInventoryDetails(UnitOfMeasureConversionWriteDto)}
     */
    @Test
    void convertFahrenheitToRankine_shouldReturnIncorrect_whenStudentResponseIsNotANumber() throws Exception {

        var uomConversionWriteDto = new UnitOfMeasureConversionWriteDto();
        uomConversionWriteDto.inputNumericalValue = BigDecimal.valueOf(6.5);
        uomConversionWriteDto.inputUom = "Fahrenheit";
        uomConversionWriteDto.targetUom = "Rankine";
        uomConversionWriteDto.studentAnswer = "dog";

        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(uomConversionWriteDto);

        mockMvc.perform(
                        post("/uom-conversion-validation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validationOutput").value("incorrect"));
    }

    /**
     * Tests {@link com.unitconverter.flexion.web.UnitOfMeasureConversionValidationController#postFacilityItemInventoryDetails(UnitOfMeasureConversionWriteDto)}
     */
    @Test
    void convertUnits_shouldReturnInvalid_whenUomInvalid() throws Exception {

        var uomConversionWriteDto = new UnitOfMeasureConversionWriteDto();
        uomConversionWriteDto.inputNumericalValue = BigDecimal.valueOf(136.1);
        uomConversionWriteDto.inputUom = "dog";
        uomConversionWriteDto.targetUom = "Celsius";
        uomConversionWriteDto.studentAnswer = "45.32";

        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(uomConversionWriteDto);

        mockMvc.perform(
                        post("/uom-conversion-validation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.validationOutput").value("invalid"));
    }
}
