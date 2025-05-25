package fiap.com.br.MottuAPI.model.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record AluguelRequest(
    @NotNull Long motoId,
    @NotNull LocalDate dataInicio,
    LocalDate dataFim
) {}
