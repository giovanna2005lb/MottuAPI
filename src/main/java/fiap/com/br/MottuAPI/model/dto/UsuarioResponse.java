package fiap.com.br.MottuAPI.model.dto;

import fiap.com.br.MottuAPI.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private UserRole role;
}
