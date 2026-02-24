package com.luizalebs.comunicacao_api.infraestructure.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModoEnvioEnum {

    // Aqui definimos os canais de entrega oficiais.
    // São as únicas formas que o nosso sistema aceita para enviar a mensagem.

    EMAIL,
    SMS,
    PUSH,
    WHATSAPP;
}