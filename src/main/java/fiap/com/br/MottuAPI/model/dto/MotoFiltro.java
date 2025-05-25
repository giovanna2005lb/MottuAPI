package fiap.com.br.MottuAPI.model.dto;

import fiap.com.br.MottuAPI.model.StatusMoto;

public record MotoFiltro(
    String modelo,
    String marca,
    StatusMoto status,
    Double precoMin,
    Double precoMax
) {}
