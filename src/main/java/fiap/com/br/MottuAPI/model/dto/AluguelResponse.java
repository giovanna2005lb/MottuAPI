package fiap.com.br.MottuAPI.model.dto;

import java.time.LocalDate;

public record AluguelResponse(
    Long id,
    String emailCliente,
    Long motoId,
    String modeloMoto,
    String marcaMoto,
    LocalDate dataInicio,
    LocalDate dataFim
) {}