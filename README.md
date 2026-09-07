# Booking Service

Serviço de agendamentos do projeto Saloon Platform.

## Visão Geral

O Booking Service é responsável pelo gerenciamento de agendamentos de serviços nos salões, incluindo criação, cancelamento, confirmação e comunicação assíncrona via RabbitMQ.

## Porta

**5005**

## Funcionalidades

- Criação de agendamentos
- Cancelamento de agendamentos
- Confirmação de agendamentos
- Verificação de disponibilidade
- Notificações assíncronas via RabbitMQ
- Integração com Payment Service

## Endpoints

| Método | Caminho | Descrição |
|--------|---------|-----------|
| POST | `/api/bookings` | Cria novo agendamento |
| GET | `/api/bookings/{id}` | Busca agendamento por ID |
| PUT | `/api/bookings/{id}` | Atualiza agendamento |
| DELETE | `/api/bookings/{id}` | Cancela agendamento |
| GET | `/api/bookings/user/{userId}` | Lista agendamentos do usuário |
| GET | `/api/bookings/saloon/{saloonId}` | Lista agendamentos do salão |

## Tecnologias

- Spring Boot 4.1.0
- Spring Data JPA
- Spring AMQP (RabbitMQ)
- Flyway (migrações)
- MySQL
- OpenFeign
- Eureka Client
- Lombok
- Java 21

## Banco de Dados

- **Nome:** bookingdb
- **Porta:** 3302

## Como Rodar

```bash
mvn clean package
java -jar target/booking-service-0.0.1-SNAPSHOT.jar
```

## Comunicação Assíncrona

O serviço utiliza RabbitMQ para:
- Notificar usuários sobre confirmação/cancelamento
- Enviar dados para Payment Service
- Notificar salões sobre novos agendamentos

## Integrações

- **User Service:** Validação de usuários
- **Saloon Service:** Validação de salões
- **Service Offering:** Verificação de serviços
- **Payment Service:** Processamento de pagamentos
- **Notifications:** Envio de notificações
