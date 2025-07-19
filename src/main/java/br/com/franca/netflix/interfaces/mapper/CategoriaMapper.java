package br.com.franca.netflix.interfaces.mapper;

import br.com.franca.netflix.domain.model.Categoria;
import br.com.franca.netflix.infrastructure.persistence.entity.CategoriaEntity;
import br.com.franca.netflix.interfaces.dto.CategoriaResponseDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaMapper {

    public Categoria toDomain(CategoriaEntity categoriaEntity){
        return Categoria.builder()
                .id(categoriaEntity.getId())
                .nome(categoriaEntity.getNome())
                .build();
    }

    public CategoriaEntity toEntity(Categoria categoria){
        return CategoriaEntity.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }

    public CategoriaResponseDTO toCategoriaResponse(Categoria categoria) {
        CategoriaResponseDTO categoriaResponseDTO = new CategoriaResponseDTO();
        categoriaResponseDTO.setId(categoria.getId());
        categoriaResponseDTO.setNome(categoria.getNome());
        return categoriaResponseDTO;
    }

    public List<CategoriaResponseDTO> toCategoriaResponseList(List<Categoria> categoriaList) {
        return categoriaList.stream()
                .map(this::toCategoriaResponse)
                .collect(Collectors.toList());
    }
}