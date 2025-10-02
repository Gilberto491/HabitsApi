# Habits APP

## 📌 Sobre o Projeto
O **Habits API** é um projeto desenvolvido em **Java + Spring Boot**, que tem como objetivo gerenciar hábitos, categorias e catálogos de forma organizada e extensível.  
A ideia central é fornecer uma API backend que permita criar, listar e organizar hábitos do usuário, seguindo boas práticas de design e arquitetura de software.

Este projeto também está sendo usado como laboratório de aprendizado para **Clean/Hexagonal Architecture**, **DDD (Domain-Driven Design)** e boas práticas de **Spring Data JPA**, **REST API** e **tratamento de exceções padronizado**.

---

## 🏛️ Arquitetura Utilizada

O projeto segue princípios de **Clean Architecture** e **DDD**, separando bem cada camada:

```
com.app.habits
├─ application → Casos de uso, DTOs e Mappers
│ ├─ dto
│ ├─ mapper
│ └─ usecase
│
├─ domain → Regras de negócio e abstrações
│ ├─ exception → Exceções de domínio
│ ├─ model → Entidades e objetos de domínio
│ └─ port → Interfaces (ports) para comunicação com outras camadas
│
├─ infrastructure → Implementações técnicas (adapters)
│ ├─ config → Configurações gerais (MessageSource, CorrelationIdFilter, etc.)
│ ├─ persistence → Acesso a dados
│ │ ├─ adapter → Implementações de repositórios
│ │ ├─ entity → Entidades JPA
│ │ └─ spring → Integrações Spring Data
│ └─ rest → Controllers (API REST) e handlers
│ └─ error → Padronização de erros e ResponseDTOs
│
└─ HabitsApplication → Classe principal do Spring Boot
```

### 🔹 Camada **Application**
- Contém os **casos de uso** (`usecase`) que orquestram a lógica da aplicação.  
- **DTOs** para transporte de dados entre camadas.  
- **Mappers** para conversão entre entidades, DTOs e modelos de domínio.

### 🔹 Camada **Domain**
- Representa o **coração do sistema**, independente de frameworks.  
- Contém os **modelos de domínio**, **ports** (interfaces) e **exceções de negócio**.  
- Não conhece nada de infraestrutura, apenas regras e contratos.

### 🔹 Camada **Infrastructure**
- Adapta o domínio para o “mundo real” (banco de dados, REST, configs).  
- **Persistence**: entidades JPA e adapters que implementam os ports.  
- **Rest**: exposição via API HTTP (controllers + exception handler padronizado).  
- **Error Handling**: responses de erro consistentes com suporte a **i18n** (mensagens em `messages.properties`).  
- **Config**: beans globais (MessageProvider, CorrelationIdFilter etc.).

---

## ⚙️ Tecnologias e Dependências
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Hibernate**
- **Lombok**
- **Flyway** (migrações de banco)
- **i18n** com `messages.properties`
- **Padronização de erros com @RestControllerAdvice**

---

## 📂 Estrutura de Erros
Todas as exceções da aplicação seguem um padrão de resposta em JSON:

```
{
  "timestamp": "2025-10-02T15:27:10.123-03:00",
  "path": "/api/v1/catalog/categories",
  "traceId": "9a6e3e9f-6d72-4b9c-9d26-4d8c2dbf1a1c",
  "code": "VALIDATION_ERROR",
  "message": "Falha de validação nos dados enviados.",
  "fieldErrors": [
    { "field": "name", "message": "não pode ser vazio", "rejectedValue": "" }
  ]
}
```

---

## 📖 Próximos Passos

Implementar autenticação/autorização.

Criar testes automatizados para os casos de uso.

Expor documentação da API via Swagger/OpenAPI.

Criar front-end para consumir a API.

---

## 📜 Licença

Este projeto está com No License definido.
Isso significa que o código não pode ser copiado, modificado ou redistribuído sem autorização prévia.
É público apenas para visualização e estudo.
