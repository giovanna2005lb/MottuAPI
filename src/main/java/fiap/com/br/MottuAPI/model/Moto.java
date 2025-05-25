package fiap.com.br.MottuAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "motos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String modelo;

    @NotBlank
    private String marca;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusMoto status;

    private String problema;
    
    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Usuario mecanicoResponsavel;

    @NotNull
    private Integer ano;

    @NotNull
    private Double precoPorDia;
}
