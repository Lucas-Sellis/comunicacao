#  Comunicação API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

Esta é uma API robusta de agendamento de comunicações desenvolvida com **Spring Boot**. 
O sistema permite gerenciar o ciclo de vida de mensagens (E-mail, SMS, WhatsApp e Push), controlando seus status de envio.

## 🏗️ Arquitetura e Padrões
O projeto foi desenhado focando em segurança e boas práticas:
- **DTO Pattern**: Utilizamos `InDTO` para recebimento de dados e `OutDTO` para resposta, garantindo que a **Entity** (nosso modelo de banco de dados) nunca seja exposta diretamente.
- **Enums**: Padronização rigorosa dos modos de envio e status da tarefa.
- **Service Layer**: Toda a regra de negócio e conversões (Mapper) estão centralizadas na camada de serviço.

## 🚦 Status e Modos de Envio

| Status | Descrição |
| :--- | :--- |
| `PENDENTE` | Aguardando processamento |
| `ENVIADO` | Comunicação disparada com sucesso |
| `CANCELADO` | Agendamento interrompido pelo usuário |

**Canais Disponíveis:** `EMAIL`, `SMS`, `PUSH`, `WHATSAPP`.

## 🛠️ Tecnologias
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Lombok** (para código limpo)

## 📡 Endpoints (Documentação da API)

### 1. Agendar Comunicação
Cria um novo agendamento no banco de dados.
- **Método:** `POST`
- **Caminho:** `/comunicacao/agendar`
- **Corpo (JSON):**
```json
{
  "nomeDestinatario": "Nome do Cliente",
  "emailDestinatario": "teste@email.com",
  "telefoneDestinatario": "11999999999",
  "mensagem": "Mensagem de teste",
  "modoDeEnvio": "EMAIL",
  "dataHoraEnvio": "2026-02-25 10:00:00"
}