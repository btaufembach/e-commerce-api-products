package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ProdutoRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdate {

        @NotNull(message = "O campo não pode ser nulo")
        @Size(min = 1, max = 30, message = "O campo deve conter entre 1 e 30 caracteres")
        private String nome;

        @NotNull(message = "O campo descrição não pode ser nulo")
        @Size(min = 1, max = 30, message = "O campo descrição deve conter entre 1 e 255 caracteres")
        private String descricao;

        private String complemento;

        @NotNull(message = "O campo valor não pode ser nulo")
        private Double valor;

        @NotNull(message = "O campo unidade de medida não pode ser nulo")
        private ProdutoModel.UnidadeMedida unidadeMedida;

        @NotNull(message = "O campo quantidade não pode ser nulo")
        private Double qtde;

        @NotNull(message = "O campo fabricante não pode ser nulo")
        @Size(min = 1, max = 255, message = "O campo fabricante deve conter entre 1 e 255 caracteres")
        private String fabricante;

        private String fornecedor;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detalhes {

        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private ProdutoModel.UnidadeMedida unidadeMedida;
        private Double qtde;
        private String fabricante;
        private String fornecedor;
        private CategoriaRepresentation.Detail categoria;

        public static Detalhes from (ProdutoModel produtoModel) {

            return Detalhes.builder()
                    .id(produtoModel.getId())
                    .nome(produtoModel.getNome())
                    .descricao(produtoModel.getDescricao())
                    .complemento(produtoModel.getComplemento())
                    .valor(produtoModel.getValor())
                    .unidadeMedida(produtoModel.getUnidadeMedida())
                    .qtde(produtoModel.getQtde())
                    .fabricante(produtoModel.getFabricante())
                    .fornecedor(produtoModel.getFornecedor())
                    .categoria(CategoriaRepresentation.Detail.from(produtoModel.getCategoria()))
                    .build();
        }
    }
}
