package com.luizalebs.comunicacao_api.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ComunicacaoInDTO implements Serializable {

    //  DTO IN: O que o cliente manda (O Pedido). é o pedido na mão do garçom: ele traz só o necessário para a cozinha (Service)
    // trabalhar, sem expor o estoque (Entity).

    // Define que a data que recebemos no JSON deve seguir o padrão "Ano-Mês-Dia Hora:Minuto:Segundo"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHoraEnvio;

    // Atributos privados garantem que ninguém mude os dados "na força bruta" (Encapsulamento).
    private String nomeDestinatario;
    private String emailDestinatario;
    private String telefoneDestinatario;
    private String mensagem;
    private ModoEnvioEnum modoDeEnvio;

    @JsonIgnore // Ignora o status porque o cliente não pode escolher se o pedido já nasce "Concluído".
    private StatusEnvioEnum statusEnvio;

}