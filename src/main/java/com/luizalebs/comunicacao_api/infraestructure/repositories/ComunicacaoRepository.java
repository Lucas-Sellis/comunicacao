package com.luizalebs.comunicacao_api.infraestructure.repositories;

import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.springframework.data.repository.CrudRepository;

public interface ComunicacaoRepository extends CrudRepository<ComunicacaoEntity, Long> {

    // Usei o CrudRepository porque ele já entrega o "kit básico" pronto:
    // Já vem com as funções de Salvar, Deletar, Buscar e Alterar sem eu precisar escrever SQL na mão.

    // Essa função busca uma comunicação no banco usando o e-mail do destinatário.
    // O Spring entende o nome do método e monta a busca sozinho para mim.
    ComunicacaoEntity findByEmailDestinatario(String emailDestinatario);
}