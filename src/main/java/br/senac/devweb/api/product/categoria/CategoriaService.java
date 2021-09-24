package br.senac.devweb.api.product.categoria;

import br.senac.devweb.api.product.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CategoriaService {



    private CategoriaRepository categoriaRepository;

    public Categoria salvar(CategoriaRepresentation.CreateOrUpdateCategoria createOrUpdateCategoria) {

        return this.categoriaRepository.save(Categoria.builder()
                .descricao(createOrUpdateCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());

    }

    public Categoria update(Long id, CategoriaRepresentation.CreateOrUpdateCategoria createOrUpdateCategoria) {
        Categoria categoria = this.getCategoria(id);
        categoria.setDescricao(createOrUpdateCategoria.getDescricao());
        return this.categoriaRepository.save(categoria);
    }

    public List<Categoria> getAllCaregoria(Predicate filter) {
        return this.categoriaRepository.findAll(filter);
    }

    public void deleteCategoria(Long id) {
        Categoria categoria = this.getCategoria(id);
        categoria.setStatus(Categoria.Status.INATIVO);
        this.categoriaRepository.save(categoria);
    }


    /***
     * busca uma categoria ativa ou retorna um notfoundS
     * @param id
     * @return
     */
    public Categoria getCategoria(Long id) {
        BooleanExpression filter =
                QCategoria.categoria.id.eq(id).and(QCategoria.categoria.status.eq(Categoria.Status.ATIVO));

        return this.categoriaRepository.findOne(filter)
                .orElseThrow(()-> new NotFoundException("Categoria não encontrada."));
    }


}
