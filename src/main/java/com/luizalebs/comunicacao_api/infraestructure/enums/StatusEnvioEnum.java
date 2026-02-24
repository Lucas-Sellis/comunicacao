package com.luizalebs.comunicacao_api.infraestructure.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnvioEnum {

    // Os Enums são as opções fixas que não mudam.
    // É como o cardápio: o cliente só pode escolher o que está escrito aqui.

    PENDENTE,   // O pedido entrou na cozinha, mas ainda não foi feito.
    ENVIADO,    // O prato saiu para a entrega.
    CANCELADO;  // O pedido foi interrompido e não será entregue.
}