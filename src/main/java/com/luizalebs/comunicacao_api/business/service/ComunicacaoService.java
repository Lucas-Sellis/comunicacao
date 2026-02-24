package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service // ESSA CLASSE É UM SERVICE, OU SEJA, AQUI FICA A REGRA DE NEGÓCIO
public class ComunicacaoService {

    // AQUI É INJEÇÃO DE DEPENDÊNCIA.
    // ESTAMOS RECEBENDO O REPOSITORY PARA ACESSAR O BANCO
    private final ComunicacaoRepository repository;

    // INJETAMOS A DEPENDÊNCIA DO MAPPER (gerado automaticamente pelo MapStruct)
    // Ele serve para transformar DTO em Entity e vice-versa sem precisar de código manual
    private final ComunicacaoMapper mapper;

    // CONSTRUTOR QUE RECEBE AS DEPENDÊNCIAS.
    // O SPRING INJETA AUTOMATICAMENTE POR CAUSA DO @Service
    public ComunicacaoService(ComunicacaoRepository repository, ComunicacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ESSE MÉTODO AGENDA UMA COMUNICAÇÃO
    // RECEBE UM DTO DE ENTRADA (InDTO)
    public ComunicacaoOutDTO agendarComunicacao(ComunicacaoInDTO dto) {

        // SE O DTO FOR NULO, NÃO FAZ SENTIDO CONTINUAR
        if (Objects.isNull(dto)) {
            throw new RuntimeException("DTO não pode ser nulo");
        }

        // AQUI ELE DEFINE AUTOMATICAMENTE O STATUS COMO PENDENTE
        dto.setStatusEnvio(StatusEnvioEnum.PENDENTE);

        // TRANSFORMA O DTO EM ENTITY PARA PODER SALVAR NO BANCO
        ComunicacaoEntity entity = mapper.paraEntity(dto);

        // SALVA A ENTITY NO BANCO
        repository.save(entity);

        // DEPOIS DE SALVAR, CONVERTE A ENTITY DE VOLTA PARA DTO DE SAÍDA
        ComunicacaoOutDTO outDTO = mapper.paraDTO(entity);

        // RETORNA O DTO PARA O CONTROLLER
        return outDTO;
    }

    // ESSE MÉTODO BUSCA O STATUS DE UMA COMUNICAÇÃO PELO EMAIL
    public ComunicacaoOutDTO buscarStatusComunicacao(String emailDestinatario) {

        // BUSCA NO BANCO PELO EMAIL DO DESTINATÁRIO
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);

        // SE NÃO ENCONTRAR, JOGA EXCEÇÃO
        if (Objects.isNull(entity)) {
            throw new RuntimeException("Comunicação não encontrada");
        }

        // CONVERTE A ENTITY PARA DTO E RETORNA
        return mapper.paraDTO(entity);
    }

    // ESSE MÉTODO ALTERA O STATUS DA COMUNICAÇÃO PARA CANCELADO
    public ComunicacaoOutDTO alterarStatusComunicacao(String emailDestinatario) {

        // BUSCA A COMUNICAÇÃO NO BANCO
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);

        // SE NÃO ENCONTRAR, JOGA EXCEÇÃO
        if (Objects.isNull(entity)) {
            throw new RuntimeException("Comunicação não encontrada");
        }

        // ALTERA O STATUS PARA CANCELADO
        entity.setStatusEnvio(StatusEnvioEnum.CANCELADO);

        // SALVA NOVAMENTE NO BANCO COM O STATUS ATUALIZADO
        repository.save(entity);

        // CONVERTE PARA DTO E RETORNA
        return mapper.paraDTO(entity);
    }
}
git add .
git commit -m "feat: adiciona cancelamento de comunicação com MapStruct"
