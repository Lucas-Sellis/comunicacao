#  Comunicação API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)



API de agendamento de comunicações desenvolvida com Spring Boot.

O sistema permite gerenciar o ciclo de vida de mensagens (E-mail, SMS, WhatsApp e Push), controlando seus status de envio de forma estruturada e segura.

🏗️ Arquitetura e Boas Práticas

O projeto foi estruturado seguindo padrões amplamente utilizados no mercado:

DTO Pattern
Utilização de ComunicacaoInDTO para entrada de dados e ComunicacaoOutDTO para resposta, evitando a exposição direta da Entity.

Service Layer
Toda a regra de negócio está centralizada na camada de serviço, mantendo o Controller limpo e organizado.

Mapper
Conversão entre DTO e Entity isolada da lógica principal.

Enums
Padronização dos modos de envio e status da comunicação.

OpenAPI / Swagger
Documentação interativa disponível em:

http://localhost:8080/swagger-ui.html

Docker
Ambiente totalmente containerizado para facilitar execução e deploy.

🐳 Como Rodar com Docker

A aplicação está completamente dockerizada, incluindo banco de dados e API.

1️⃣ Gerar o .jar
./mvnw clean package -DskipTests
2️⃣ Subir a aplicação e o banco
docker-compose up --build

A API ficará disponível em:

http://localhost:8080

🚦 Status e Modos de Envio
📌 Status da Comunicação
Status	Descrição
PENDENTE	Aguardando processamento
ENVIADO	Comunicação enviada com sucesso
CANCELADO	Agendamento interrompido pelo usuário
📌 Canais Disponíveis

EMAIL, SMS, PUSH, WHATSAPP

🛠️ Tecnologias Utilizadas

Java 17

Spring Boot 3

Spring Data JPA

MySQL

Lombok

Docker

Swagger / OpenAPI

📡 Endpoints
1️⃣ Agendar Comunicação

Cria um novo agendamento no sistema.

Método: POST

URL: /comunicacao/agendar

Corpo da Requisição (JSON)
{
"nomeDestinatario": "Nome do Cliente",
"emailDestinatario": "teste@email.com",
"telefoneDestinatario": "11999999999",
"mensagem": "Mensagem de teste",
"modoDeEnvio": "EMAIL",
"dataHoraEnvio": "2026-02-25T10:00:00"
}