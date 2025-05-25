package fiap.com.br.MottuAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "alugueis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "moto_id")
    private Moto moto;

    private LocalDate dataInicio;

    private LocalDate dataFim;
}
