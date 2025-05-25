package fiap.com.br.MottuAPI.specification;

import org.springframework.data.jpa.domain.Specification;
import fiap.com.br.MottuAPI.model.Moto;
import fiap.com.br.MottuAPI.model.StatusMoto;

public class MotoSpecification {

    public static Specification<Moto> comModelo(String modelo) {
        return (root, query, cb) -> modelo == null ? null :
                cb.like(cb.lower(root.get("modelo")), "%" + modelo.toLowerCase() + "%");
    }

    public static Specification<Moto> comMarca(String marca) {
        return (root, query, cb) -> marca == null ? null :
                cb.like(cb.lower(root.get("marca")), "%" + marca.toLowerCase() + "%");
    }

    public static Specification<Moto> comStatus(StatusMoto status) {
        return (root, query, cb) -> status == null ? null :
                cb.equal(root.get("status"), status);
    }

    public static Specification<Moto> comPrecoMin(Double precoMin) {
        return (root, query, cb) -> precoMin == null ? null :
                cb.greaterThanOrEqualTo(root.get("precoPorDia"), precoMin);
    }

    public static Specification<Moto> comPrecoMax(Double precoMax) {
        return (root, query, cb) -> precoMax == null ? null :
                cb.lessThanOrEqualTo(root.get("precoPorDia"), precoMax);
    }
}

