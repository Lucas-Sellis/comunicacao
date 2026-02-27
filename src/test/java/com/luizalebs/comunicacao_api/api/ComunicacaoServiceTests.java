package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// aqui estamos ativando o Mockito para poder usar @Mock e @InjectMocks
class ComunicacaoServiceTest {

    @InjectMocks
    // aqui estamos criando o service real que queremos testar
    // porém as dependências dele (repository e mapper) serão falsas (mockadas)
    private ComunicacaoService service;

    @Mock
    // aqui estamos criando um repository falso
    // ele não acessa banco de verdade, eu controlo o que ele retorna
    private ComunicacaoRepository repository;

    @Mock
    // aqui estamos criando um mapper falso
    // ele não converte de verdade, eu digo o que ele deve retornar
    private ComunicacaoMapper mapper;

    private ComunicacaoInDTO inDTO;
    private ComunicacaoEntity entity;

    @BeforeEach
    void setup() {

        // aqui eu estou criando o objeto que simula o que chega na requisição
        inDTO = new ComunicacaoInDTO();
        inDTO.setEmailDestinatario("teste@email.com");

        // aqui eu estou criando a entity que representa o objeto convertido
        // que seria salvo no banco
        entity = new ComunicacaoEntity();
        entity.setEmailDestinatario("teste@email.com");
    }

    @Test
    void deveAgendarComSucesso() {

        // ========================
        // ARRANGE (preparar cenário)
        // ========================

        // aqui eu crio o DTO de saída que o método deve retornar no final
        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();
        outDTO.setEmailDestinatario("teste@email.com");

        // quando o service chamar o mapper para converter o DTO de entrada em entity,
        // eu estou dizendo que ele deve retornar essa entity que eu criei
        when(mapper.paraEntity(inDTO))
                .thenReturn(entity);

        // quando o service tentar salvar a entity,
        // eu estou simulando que o banco salvou e retornou essa mesma entity
        when(repository.save(entity))
                .thenReturn(entity);

        // quando o service converter a entity salva para DTO de saída,
        // eu estou dizendo qual DTO deve ser retornado
        when(mapper.paraDTO(entity))
                .thenReturn(outDTO);

        // ========================
        // ACT (executar método)
        // ========================

        // aqui eu estou chamando o método real do service
        // passando o inDTO como entrada
        ComunicacaoOutDTO resultado = service.agendarComunicacao(inDTO);

        // ========================
        // ASSERT (verificar resultado)
        // ========================

        // aqui eu verifico se o retorno não é nulo
        assertNotNull(resultado);

        // aqui eu verifico se o email retornado é o mesmo que eu defini
        assertEquals("teste@email.com", resultado.getEmailDestinatario());

        // aqui eu verifico se o repository foi chamado exatamente uma vez
        // isso garante que o service realmente tentou salvar
        verify(repository, times(1)).save(entity);
    }
}