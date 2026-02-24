package com.luizalebs.comunicacao_api.infraestructure.entities;

import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "COMUNICACAO") // Define que o nome desta tabela lá no MySQL será COMUNICACAO
public class ComunicacaoEntity implements Serializable {

    @Id // Marca o ID como a "Chave Mestra" (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco de dados gera o número sozinho (1, 2, 3...)
    private Long id;

    @Column(name = "HORA_ENVIO", nullable = false) // Coluna da hora; o "nullable = false" garante que nunca fique vazia
    private Date dataHoraEnvio;

    @Column(name = "NOME_DESTINATARIO", nullable = false)
    private String nomeDestinatario;

    @Column(name = "EMAIL_DESTINATARIO", nullable = false, unique = true) // 'unique = true' não deixa cadastrar dois e-mails iguais
    private String emailDestinatario;

    @Column(name = "TELEFONE_DESTINATARIO")
    private String telefoneDestinatario;

    @Column(name = "MENSAGEM", nullable = false)
    private String mensagem;

    @Column(name = "MODO_ENVIO")
    @Enumerated(EnumType.STRING) // Salva o nome do Enum (ex: 'EMAIL', 'SMS') como texto no banco
    private ModoEnvioEnum modoDeEnvio;

    @Column(name = "STATUS_ENVIO")
    @Enumerated(EnumType.STRING) // Indica se o envio está PENDENTE, CONCLUÍDO, etc.
    private StatusEnvioEnum statusEnvio;

    // Resumindo: Essas são as especificações de cada "gaveta" (coluna) da nossa tabela no banco.
}