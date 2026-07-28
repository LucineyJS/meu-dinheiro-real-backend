package com.meudinheiroreal.backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumoFinanceiroDTO {

    private BigDecimal saldoTotal;
    private BigDecimal totalGanhos;
    private BigDecimal totalGastos;
    private Map<String, BigDecimal> gastosPorCategoria;
    private Map<String, BigDecimal> ganhosPorCategoria;
}