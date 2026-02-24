# Jogadores Por Times API

API REST completa para gerenciamento de jogadores de futebol organizados por times.

## 📋 Visão Geral

Sistema Spring Boot para gerenciar times de futebol e seus jogadores, com cálculo automático de folha salarial e relacionamentos ManyToOne/OneToMany.

- **Java:** 21
- **Spring Boot:** 3.5.4
- **Banco de Dados:** PostgreSQL (produção), H2 (testes)
- **Build:** Maven
- **Testes:** 32 testes (100% passando)

---

## 🚀 Quick Start

### Pré-requisitos

```bash
Java 21
Maven 3.8+
PostgreSQL 12+
```

### Build e Execução

```bash
./mvnw clean install
./mvnw spring-boot:run
```

API disponível em: `http://localhost:8080`

### Rodar Testes

```bash
./mvnw test
# Resultado esperado: Tests run: 32, Failures: 0, Errors: 0, Skipped: 0
```

---

## � Monitoramento e Documentação

### Health Check (Actuator) 🏥

Monitora a saúde da aplicação e fornece informações sobre o sistema em execução.

- **Health Status:** `http://localhost:8080/actuator/health`
  - Verifica estado da aplicação (UP/DOWN)
  - Informações de banco de dados
  - Status de componentes críticos
- **Informações da App:** `http://localhost:8080/actuator/info`
  - Versão da aplicação
  - Metadados do projeto

---

## �📚 Endpoints Principais

### Jogadores

- `GET /jogador` - Listar todos
- `GET /jogador/{id}` - Buscar por ID
- `POST /jogador` - Criar novo
- `PUT /jogador/{id}/atualizar` - Atualizar
- `DELETE /jogador/{id}` - Deletar

### Times

- `GET /times` - Listar todos
- `GET /times/{id}` - Buscar por ID
- `POST /times` - Criar novo
- `PUT /times/{id}` - Atualizar
- `DELETE /times/{id}` - Deletar

---

## 🧪 Testes

**32 testes implementados com 100% de aprovação:**

- 4 Testes unitários de services com Mockito
- 6 Testes de integração com @DataJpaTest e H2
- 16 Testes de controllers com @WebMvcTest
- 6 Testes adicionais

---

## 📄 Documentação

- [ANALISE_COMPLETA.md](./ANALISE_COMPLETA.md) - Análise detalhada do projeto
- [RELATORIO_FINAL.md](./RELATORIO_FINAL.md) - Relatório completo das mudanças

---

## ✨ Melhorias Implementadas

✅ Exception handling global com @ControllerAdvice  
✅ Logging completo em operações críticas  
✅ Validação rigorosa de dados (DTOs)  
✅ Testes robustos (unitários, integração, API)  
✅ Tratamento de erros padronizado  
✅ Documentação completa  
✅ **Health Check (Actuator)** para monitoramento da aplicação

---

## 🛠️ Stack Técnico

- **Framework:** Spring Boot 3.5.4
- **ORM:** JPA/Hibernate
- **Banco:** PostgreSQL + H2 (testes)
- **Build:** Maven
- **Testing:** JUnit 5, Mockito, Spring Test
- **Logging:** SLF4J + Logback
- **Validação:** Jakarta Validation
- **Serialização:** Jackson
- **Monitoramento:** Spring Boot Actuator

---

**Projeto criado do zero com arquitetura em camadas, testes robustos e documentação completa.**
