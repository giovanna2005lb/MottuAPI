package fiap.com.br.MottuAPI.model.dto;

import fiap.com.br.MottuAPI.model.StatusMoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotoRequest(
    @NotBlank String modelo,
    @NotBlank String marca,
    @NotNull StatusMoto status,
    String problema,
    @NotNull Integer ano,
    @NotNull Double precoPorDia
) {}
