package com.unitconverter.flexion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UnitOfMeasureConversionWriteDto {

    @NotNull
    public BigDecimal inputNumericalValue;
    @NotNull
    public String inputUom;
    @NotNull
    public String targetUom;
    @NotNull
    public String studentAnswer;
}
