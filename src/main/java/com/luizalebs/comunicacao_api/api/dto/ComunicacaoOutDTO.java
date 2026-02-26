package com.luizalebs.comunicacao_api.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ComunicacaoOutDTO implements Serializable {

    // O DTO OUT é o retorno do garçom: aqui o prato já está pronto e sendo entregue ao cliente.

    // Devolvemos a data formatada para o cliente saber exatamente quando o agendamento foi registrado.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHoraEnvio;

    private String nomeDestinatario;
    private String emailDestinatario;
    private String telefoneDestinatario;
    private String mensagem;
    private ModoEnvioEnum modoDeEnvio;

    // Diferente do "In", aqui o Status aparece!
    // O cliente recebe a confirmação se o pedido está 'AGENDADO', 'ENVIADO' ou 'CANCELADO'.
    private StatusEnvioEnum statusEnvio;

}