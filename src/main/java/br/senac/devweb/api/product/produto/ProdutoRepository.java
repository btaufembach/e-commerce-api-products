package br.senac.devweb.api.product.produto;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoRepository extends CrudRepository<ProdutoModel, Long>,
        QuerydslPredicateExecutor<ProdutoModel> {

    List<ProdutoModel> findAll(Predicate filter);
}
