package com.unitconverter.flexion.web;

import com.unitconverter.flexion.dto.UnitOfMeasureConversionDto;
import com.unitconverter.flexion.dto.UnitOfMeasureConversionWriteDto;
import com.unitconverter.flexion.service.UnitOfMeasureConversionValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UnitOfMeasureConversionValidationController {

    private final UnitOfMeasureConversionValidationService unitOfMeasureConversionValidationService;

    @Operation(description = "Returns 'correct', 'incorrect' or 'invalid' for the given UOM conversion")
    @PostMapping("/uom-conversion-validation")
    public UnitOfMeasureConversionDto postFacilityItemInventoryDetails(
            @Valid @RequestBody UnitOfMeasureConversionWriteDto dto) {

        return unitOfMeasureConversionValidationService.uomConversionValidation(dto);
    }
}
