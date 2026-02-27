package com.luizalebs.comunicacao_api.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
// aqui estamos ativando o Mockito para usar @Mock e @InjectMocks
class ComunicacaoControllerTest {

    @InjectMocks
    // aqui estamos criando a controller real que queremos testar
    // mas o service dentro dela será falso (mockado)
    private ComunicacaoController controller;

    @Mock
    // aqui estamos criando um service falso
    // ele não executa regra de negócio real, eu controlo o que ele retorna
    private ComunicacaoService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        // aqui eu estou configurando o MockMvc
        // isso cria um ambiente fake do Spring MVC
        // permitindo simular requisições HTTP sem subir o servidor
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        // aqui eu crio o ObjectMapper
        // ele serve para transformar objeto Java em JSON
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveAgendarComSucesso() throws Exception {

        // ========================
        // ARRANGE (preparar cenário)
        // ========================

        // aqui eu crio o DTO que será enviado na requisição
        ComunicacaoInDTO inDTO = new ComunicacaoInDTO();
        inDTO.setEmailDestinatario("teste@email.com");

        // aqui eu crio o DTO que o service deve retornar
        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();
        outDTO.setEmailDestinatario("teste@email.com");

        // quando a controller chamar o service,
        // eu estou dizendo que ele deve retornar esse outDTO
        when(service.agendarComunicacao(any(ComunicacaoInDTO.class)))
                .thenReturn(outDTO);

        // ========================
        // ACT + ASSERT
        // ========================

        // aqui eu estou simulando uma requisição HTTP POST
        // enviando um JSON para o endpoint /comunicacao/agendar
        mockMvc.perform(
                        post("/comunicacao/agendar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inDTO))
                )
                // aqui eu verifico se o retorno da requisição foi 200 OK
                .andExpect(status().isOk());

        // aqui eu verifico se o service foi chamado exatamente uma vez
        // isso garante que a controller realmente delegou para o service
        verify(service, times(1))
                .agendarComunicacao(any(ComunicacaoInDTO.class));

        // aqui eu garanto que nenhum outro método do service foi chamado
        verifyNoMoreInteractions(service);
    }



    @Test
    void deveBuscarStatusComunicacao() throws Exception {

        String email = "teste@email.com";

        ComunicacaoOutDTO dto = new ComunicacaoOutDTO();
        dto.setEmailDestinatario(email);

        // quando o service for chamado, ele retorna o dto
        when(service.buscarStatusComunicacao(email)).thenReturn(dto);

        mockMvc.perform(get("/comunicacao")
                        .param("emailDestinatario", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).buscarStatusComunicacao(email);
    }
    @Test
    void deveCancelarComunicacao() throws Exception {

        String email = "teste@email.com";

        ComunicacaoOutDTO dto = new ComunicacaoOutDTO();
        dto.setEmailDestinatario(email);

        when(service.alterarStatusComunicacao(email)).thenReturn(dto);

        mockMvc.perform(patch("/comunicacao/cancelar")
                        .param("emailDestinatario", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).alterarStatusComunicacao(email);
    }
}