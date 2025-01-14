package com.unitconverter.flexion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public class UnitOfMeasureConversionWriteDto {

    public BigDecimal inputNumericalValue;
    public String inputUom;
    public String targetUom;
    public BigDecimal studentAnswer;
}
