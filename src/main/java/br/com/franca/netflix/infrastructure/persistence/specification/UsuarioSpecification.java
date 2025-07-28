package br.com.franca.netflix.infrastructure.persistence.specification;

import br.com.franca.netflix.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    public static Specification<UsuarioEntity> nomeContem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<UsuarioEntity> statusIgual(Boolean ativo) {
        return (root, query, cb) -> {
            if (ativo == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("ativo"), ativo);
        };
    }
}