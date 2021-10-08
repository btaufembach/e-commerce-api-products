package br.senac.devweb.api.product.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class Paginacao {

    private Integer tamanhopagina;
    private Integer paginaSelecionada;
    private boolean proximaPagina;
    private List<?> conteudo;

}
