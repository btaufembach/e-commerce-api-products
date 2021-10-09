package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import br.senac.devweb.api.product.categoria.CategoriaService;
import br.senac.devweb.api.product.util.Paginacao;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final ProdutoRepository produtoRepository;

    @PostMapping("/")
    public ResponseEntity<ProdutoRepresentation.Detalhes> cadastrarProduto(
            @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate){

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation.Detalhes
                        .from(this.produtoService.salvar(createOrUpdate,categoria)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> getAllProduto(
            @QuerydslPredicate(root = ProdutoModel.class) Predicate filtroProduto,
            @RequestParam(name = "paginaSelecionada", required = false, defaultValue = "0") Integer paginaSelecionada,
            @RequestParam(name = "tamanhoPagina", required = false, defaultValue = "2") Integer tamanhoPagina) {



        BooleanExpression filter = Objects.isNull(filtroProduto) ?
                QProdutoModel.produtoModel.status.eq(ProdutoModel.Status.ATIVO) :
                QProdutoModel.produtoModel.status.eq(ProdutoModel.Status.ATIVO).and(filtroProduto);

        Pageable paginaDesejada = PageRequest.of(paginaSelecionada, tamanhoPagina);
        Page<ProdutoModel> listaProduto = this.produtoRepository.findAll(filter, paginaDesejada);

        Paginacao paginacao = Paginacao.builder()
                .tamanhopagina(tamanhoPagina)
                .paginaSelecionada(paginaSelecionada)
                .proximaPagina(listaProduto.hasNext())
                .conteudo(ProdutoRepresentation.Lista.from(listaProduto.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> getOneProduto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProdutoRepresentation.Detalhes.from(this.produtoService.buscarUm(id)));
    }

    @PutMapping("/id")
    public ResponseEntity<ProdutoRepresentation.Detalhes> atualizaProduto(@PathVariable("id") Long id,
                                                                          @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProdutoRepresentation.Detalhes.from(this.produtoService.atualizar(id, createOrUpdate, categoria)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduto(@PathVariable("id") Long id) {
        this.produtoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
