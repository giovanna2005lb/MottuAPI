package fiap.com.br.MottuAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fiap.com.br.MottuAPI.model.Moto;
import fiap.com.br.MottuAPI.model.StatusMoto;

public interface MotoRepository extends JpaRepository<Moto, Long>, JpaSpecificationExecutor<Moto> {
    List<Moto> findByStatus(StatusMoto status);

    List<Moto> findByMarcaAndStatus(String marca, StatusMoto status);

    List<Moto> findByMecanicoResponsavelId(Long mecanicoId);
}
