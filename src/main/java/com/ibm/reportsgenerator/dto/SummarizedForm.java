package com.ibm.reportsgenerator.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ibm.reportsgenerator.utils.Rest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummarizedForm {

    private String name;
    private String email;
    private BigDecimal totalCredit;
    private BigDecimal totalDebit;

    @JsonSerialize(using = Rest.BigDecimalSerializer.class)
    private BigDecimal netAmount;

    @Override
    public String toString(){
        return Rest.toJsonString(this);
    }
}
