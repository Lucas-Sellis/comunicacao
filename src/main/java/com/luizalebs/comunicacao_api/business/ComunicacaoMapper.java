package com.luizalebs.comunicacao_api.business;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComunicacaoMapper {

// bom fizemos a implementação do mapper para fazer as conversoes automaticas

    ComunicacaoEntity paraEntity(ComunicacaoInDTO dto); // recebe dto e transforma em entity

    ComunicacaoOutDTO paraDTO(ComunicacaoEntity entity); // recebe entity e transforma em dto
}

