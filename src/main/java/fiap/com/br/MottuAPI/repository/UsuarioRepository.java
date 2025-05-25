package fiap.com.br.MottuAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.MottuAPI.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
