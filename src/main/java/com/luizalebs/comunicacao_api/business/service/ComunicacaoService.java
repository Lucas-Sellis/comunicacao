package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicacaoConverter;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service // ESSA CLASSE É UM SERVICE, OU SEJA, AQUI FICA A REGRA DE NEGÓCIO
public class ComunicacaoService {

    // AQUI É INJEÇÃO DE DEPENDÊNCIA.
    // ESTAMOS RECEBENDO O REPOSITORY PARA ACESSAR O BANCO
    private final ComunicacaoRepository repository;

    // AQUI TAMBÉM É INJEÇÃO DE DEPENDÊNCIA.
    // O CONVERTER SERVE PARA TRANSFORMAR DTO EM ENTITY E VICE-VERSA
    private final ComunicacaoConverter converter;

    // CONSTRUTOR QUE RECEBE AS DEPENDÊNCIAS.
    // O SPRING INJETA AUTOMATICAMENTE POR CAUSA DO @Service
    public ComunicacaoService(ComunicacaoRepository repository, ComunicacaoConverter converter) {
        this.repository = repository;
        this.converter = converter;
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
        ComunicacaoEntity entity = converter.paraEntity(dto);

        // SALVA A ENTITY NO BANCO
        repository.save(entity);

        // DEPOIS DE SALVAR, CONVERTE A ENTITY DE VOLTA PARA DTO DE SAÍDA
        ComunicacaoOutDTO outDTO = converter.paraDTO(entity);

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
        return converter.paraDTO(entity);
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
        return converter.paraDTO(entity);
    }
}