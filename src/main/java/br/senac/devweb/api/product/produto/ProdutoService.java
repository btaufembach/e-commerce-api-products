package br.senac.devweb.api.product.produto;


import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;


    public ProdutoModel salvar(ProdutoRepresentation.CreateOrUpdate createOrUpdate, Categoria categoria) {

        ProdutoModel produtoModel = ProdutoModel.builder()
                .nome(createOrUpdate.getNome())
                .descricao(createOrUpdate.getDescricao())
                .complemento(Strings.isEmpty(createOrUpdate.getComplemento()) ? "" : createOrUpdate.getComplemento())
                .fabricante(createOrUpdate.getFabricante())
                .fornecedor(Strings.isEmpty(createOrUpdate.getFornecedor()) ? "" : createOrUpdate.getFornecedor())
                .qtde(createOrUpdate.getQtde())
                .valor(createOrUpdate.getValor())
                .unidadeMedida(createOrUpdate.getUnidadeMedida())
                .categoria(categoria)
                .status(ProdutoModel.Status.ATIVO)
                .build();

        return this.produtoRepository.save(produtoModel);
    }

    public ProdutoModel atualizar(Long id, ProdutoRepresentation.CreateOrUpdate createOrUpdate, Categoria categoria) {

        ProdutoModel produtoModelAntigo = this.buscarUm(id);

        ProdutoModel produtoModelAtualizado = produtoModelAntigo.toBuilder()
                .nome(createOrUpdate.getNome())
                .descricao(createOrUpdate.getDescricao())
                .complemento(Strings.isEmpty(createOrUpdate.getComplemento()) ? "" : createOrUpdate.getComplemento())
                .fabricante(createOrUpdate.getFabricante())
                .fornecedor(Strings.isEmpty(createOrUpdate.getFornecedor()) ? "" : createOrUpdate.getFornecedor())
                .qtde(createOrUpdate.getQtde())
                .valor(createOrUpdate.getValor())
                .unidadeMedida(createOrUpdate.getUnidadeMedida())
                .categoria(categoria)
                .status(ProdutoModel.Status.ATIVO)
                .build();

        return this.produtoRepository.save(produtoModelAtualizado);

    }

    public List<ProdutoModel> buscarTodos(Predicate filter){
        return this.produtoRepository.findAll(filter);
    }

    public ProdutoModel buscarUm(Long id) {
        BooleanExpression filter = QProdutoModel.produtoModel.id.eq(id)
                .and(QProdutoModel.produtoModel.status.eq(ProdutoModel.Status.ATIVO));
        return this.produtoRepository.findOne(filter).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    public void deletar(Long id) {
        ProdutoModel produtoModel = this.buscarUm(id);
        produtoModel.setStatus(ProdutoModel.Status.INATIVO);
        this.produtoRepository.save(produtoModel);
    }


}
