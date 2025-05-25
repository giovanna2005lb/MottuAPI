package fiap.com.br.MottuAPI.model.dto;

import fiap.com.br.MottuAPI.model.StatusMoto;

public record MotoResponse(
    Long id,
    String modelo,
    String marca,
    StatusMoto status,
    String problema,
    Integer ano,
    Double precoPorDia,
    Long mecanicoId,
    String mecanicoNome
) {}