package com.unitconverter.flexion.web;

import com.unitconverter.flexion.dto.UnitOfMeasureConversionWriteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UnitOfMeasureConversionValidationController {

    @Operation(description = "Adds an inventory detail")
    @PostMapping("/uom-conversion-validation")
    public String postFacilityItemInventoryDetails(
            @Valid @RequestBody UnitOfMeasureConversionWriteDto dto) {

        return "";
    }
}
