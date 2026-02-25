package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.BadRequestException;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service // ESSA CLASSE É UM SERVICE, OU SEJA, AQUI FICA A REGRA DE NEGÓCIO
public class ComunicacaoService {

    // AQUI É INJEÇÃO DE DEPENDÊNCIA.
    private final ComunicacaoRepository repository;

    // INJETAMOS A DEPENDÊNCIA DO MAPPER (gerado automaticamente pelo MapStruct)
    private final ComunicacaoMapper mapper;

    // CONSTRUTOR QUE RECEBE AS DEPENDÊNCIAS.
    public ComunicacaoService(ComunicacaoRepository repository, ComunicacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ESSE MÉTODO AGENDA UMA COMUNICAÇÃO
    public ComunicacaoOutDTO agendarComunicacao(ComunicacaoInDTO dto) {

        try {
            // SE O DTO FOR NULO, NÃO FAZ SENTIDO CONTINUAR
            if (Objects.isNull(dto)) {
                throw new BadRequestException("DTO não pode ser nulo, requisição inválida");
            }

            // DEFINE AUTOMATICAMENTE O STATUS COMO PENDENTE
            dto.setStatusEnvio(StatusEnvioEnum.PENDENTE);

            // TRANSFORMA O DTO EM ENTITY PARA PODER SALVAR NO BANCO
            ComunicacaoEntity entity = mapper.paraEntity(dto);

            // SALVA A ENTITY NO BANCO E GUARDA O RETORNO (boa prática)
            ComunicacaoEntity entitySalva = repository.save(entity);

            // CONVERTE A ENTITY SALVA DE VOLTA PARA DTO DE SAÍDA
            return mapper.paraDTO(entitySalva);
        } catch (BadRequestException e) {
            throw new BadRequestException("Erro ao agendar a comunicação " +e.getCause());
        }
    }

    // ESSE MÉTODO BUSCA O STATUS DE UMA COMUNICAÇÃO PELO EMAIL
    public ComunicacaoOutDTO buscarStatusComunicacao(String emailDestinatario) {

        try {
            ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + emailDestinatario));

            // SE NÃO ENCONTRAR, LANÇA EXCEÇÃO
            if (Objects.isNull(entity)) {
                throw new ResourceNotFoundException("Comunicação não encontrada");
            }

            // RETORNA O DTO CONVERTIDO
            return mapper.paraDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao buscar o Status da Comunicação " +e.getCause());
        }
    }

    // ESSE MÉTODO ALTERA O STATUS DA COMUNICAÇÃO PARA CANCELADO
    public ComunicacaoOutDTO alterarStatusComunicacao(String emailDestinatario) {

        try {
            ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + emailDestinatario));

            // SE NÃO ENCONTRAR, LANÇA EXCEÇÃO
            if (Objects.isNull(entity)) {
                throw new ResourceNotFoundException("Comunicação não encontrada");
            }

            // ALTERA O STATUS PARA CANCELADO
            entity.setStatusEnvio(StatusEnvioEnum.CANCELADO);

            // SALVA A ALTERAÇÃO NO BANCO
            ComunicacaoEntity entityAtualizada = repository.save(entity);

            // RETORNA O DTO ATUALIZADO
            return mapper.paraDTO(entityAtualizada);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da comunicação " +e.getCause());
        }
    }
}