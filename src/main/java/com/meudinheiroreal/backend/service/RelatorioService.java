package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.dto.response.ResumoFinanceiroDTO;
import com.meudinheiroreal.backend.model.Lancamento;
import com.meudinheiroreal.backend.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final LancamentoRepository lancamentoRepository;

    // Alterado para receber LocalDate (bater com o Controller)
    public ResumoFinanceiroDTO gerarResumo(LocalDate inicio, LocalDate fim, Long idUsuario) {

        if (inicio == null) {
            inicio = YearMonth.now().atDay(1);
        }
        if (fim == null) {
            fim = YearMonth.now().atEndOfMonth();
        }

        LocalDateTime dataInicio = inicio.atStartOfDay();
        LocalDateTime dataFim = fim.atTime(LocalTime.MAX);

        List<Lancamento> lancamentos = lancamentoRepository.findByDataLancamentoBetweenAndUsuarioIdUsuario(dataInicio, dataFim, idUsuario);

        // Processa os totais gerais
        BigDecimal totalGanhos = calcularTotalPorTipo(lancamentos, "RECEITA");
        BigDecimal totalGastos = calcularTotalPorTipo(lancamentos, "DESPESA");
        BigDecimal saldoTotal = totalGanhos.subtract(totalGastos);

        // Agrupa os valores
        Map<String, BigDecimal> ganhosPorCategoria = agruparPorCategoria(lancamentos, "RECEITA");
        Map<String, BigDecimal> gastosPorCategoria = agruparPorCategoria(lancamentos, "DESPESA");

        return ResumoFinanceiroDTO.builder()
                .saldoTotal(saldoTotal)
                .totalGanhos(totalGanhos)
                .totalGastos(totalGastos)
                .gastosPorCategoria(gastosPorCategoria)
                .ganhosPorCategoria(ganhosPorCategoria)
                .build();
    }

    private BigDecimal calcularTotalPorTipo(List<Lancamento> lancamentos, String tipo) {
        return lancamentos.stream()
                .filter(l -> l.getTipo().name().equalsIgnoreCase(tipo))
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, BigDecimal> agruparPorCategoria(List<Lancamento> lancamentos, String tipo) {
        return lancamentos.stream()
                .filter(l -> l.getTipo().name().equalsIgnoreCase(tipo))
                .collect(Collectors.groupingBy(
                        l -> l.getCategoria().getNome(),
                        Collectors.reducing(BigDecimal.ZERO, Lancamento::getValor, BigDecimal::add)
                ));
    }
}