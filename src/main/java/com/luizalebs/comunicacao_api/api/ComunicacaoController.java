package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @PostMapping("/agendar")    // ESSE ENDPOINT RECEBE UM POST PARA AGENDAR UMA COMUNICAÇÃO

    @Operation(summary = "Agendar Comunicações ", description = "Agendar uma comunicação")
    @ApiResponse(responseCode = "200", description = "Comunicação salva com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados do pedido inválidos")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")

    public ResponseEntity<ComunicacaoOutDTO> agendar(@RequestBody ComunicacaoInDTO dto)  {
        // RECEBE OS DADOS NO BODY DA REQUISIÇÃO (JSON)
        // MANDA PARA O SERVICE PROCESSAR
        // RETORNA STATUS 200 OK COM O RESULTADO
        return ResponseEntity.ok(service.agendarComunicacao(dto));
    }

    @GetMapping()

    @Operation(summary = "Buscar Status de  Comunicações ", description = "Buscar Status uma comunicação")
    @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados do pedido inválidos")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    // ESSE ENDPOINT É UM GET PARA CONSULTAR O STATUS
    public ResponseEntity<ComunicacaoOutDTO> buscarStatus(@RequestParam String emailDestinatario) {
        // RECEBE O EMAIL COMO PARÂMETRO NA URL
        // CHAMA O SERVICE PARA BUSCAR NO BANCO
        // RETORNA 200 OK COM O STATUS DA COMUNICAÇÃO
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }

    @PatchMapping("/cancelar")
    @Operation(summary = "Cancelar Status de  Comunicações ", description = "Cancelar Status uma comunicação")
    @ApiResponse(responseCode = "200", description = "Status cancelado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados do pedido inválidos")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    // ESSE ENDPOINT USA PATCH PARA ALTERAR O STATUS
    // NO CASO, ELE CANCELA A COMUNICAÇÃO
    public ResponseEntity<ComunicacaoOutDTO> cancelarStatus(@RequestParam String emailDestinatario) {
        // RECEBE O EMAIL COMO PARÂMETRO
        // PEDE PARA O SERVICE ALTERAR O STATUS PARA CANCELADO
        // RETORNA 200 OK COM O RESULTADO ATUALIZADO
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }
}