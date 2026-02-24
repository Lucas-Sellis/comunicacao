package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // ESSA CLASSE É UM CONTROLLER, OU SEJA, RECEBE AS REQUISIÇÕES HTTP
@RequestMapping("/comunicacao") // CAMINHO BASE DA API
@Tag(name = "Comunicacao", description = "Agendamento de Comunicações")
public class ComunicacaoController {

    // INJEÇÃO DE DEPENDÊNCIA DO SERVICE
    // O CONTROLLER NÃO TEM REGRA DE NEGÓCIO, ELE SÓ CHAMA O SERVICE
    private final ComunicacaoService service;
    // CONSTRUTOR PARA O SPRING INJETAR O SERVICE
    public ComunicacaoController(ComunicacaoService service) {
        this.service = service;
    }


    @PostMapping("/agendar")

    @Operation(summary = "Realizar agendamento ", description = "Registra uma nova solicitação de comunicação no sistema para envio futuro")
    @ApiResponse(responseCode = "201", description = "Agendamento criado e salvo com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na requisição ex: formato de e-mail inválido ou campos nulos")
    @ApiResponse(responseCode = "500", description = "Erro interno ao processar o agendamento no servidor")

    public ResponseEntity<ComunicacaoOutDTO> agendar(@RequestBody ComunicacaoInDTO dto)  {
        // RECEBE OS DADOS NO BODY DA REQUISIÇÃO (JSON)
        // MANDA PARA O SERVICE PROCESSAR
        // RETORNA STATUS 200 OK COM O RESULTADO
        return ResponseEntity.ok(service.agendarComunicacao(dto));
    }



    @GetMapping()
    @Operation(summary = "Consultar comunicação", description = "Busca os detalhes e o status atual de um agendamento através do e-mail do destinatário.")
    @ApiResponse(responseCode = "200", description = "Dados da comunicação recuperados com sucesso")
    @ApiResponse(responseCode = "404", description = "Comunicação não encontrada: o e-mail informado não existe no banco")
    @ApiResponse(responseCode = "500", description = "Erro interno: falha ao processar a busca no banco de dados")

    public ResponseEntity<ComunicacaoOutDTO> buscarStatus(@RequestParam String emailDestinatario) {
        // RECEBE O EMAIL COMO PARÂMETRO NA URL
        // CHAMA O SERVICE PARA BUSCAR NO BANCO
        // RETORNA 200 OK COM O STATUS DA COMUNICAÇÃO
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }



    @PatchMapping("/cancelar")
    @Operation(summary = "Interromper agendamento", description = "Altera o status de uma comunicação específica para CANCELADO, interrompendo o fluxo de envio.")
    @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso no sistema")
    @ApiResponse(responseCode = "404", description = "Não foi possível cancelar: o registro solicitado não foi encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno: falha técnica ao tentar atualizar o status")


    public ResponseEntity<ComunicacaoOutDTO> cancelarStatus(@RequestParam String emailDestinatario) {
        // RECEBE O EMAIL COMO PARÂMETRO
        // PEDE PARA O SERVICE ALTERAR O STATUS PARA CANCELADO
        // RETORNA 200 OK COM O RESULTADO ATUALIZADO
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }
}
