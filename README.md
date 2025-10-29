# 🏥 API de Agendamento de Consultas Médicas

Este projeto é um sistema REST desenvolvido em **Spring Boot (Java 17)** para gerenciar **agendamentos de consultas médicas**, incluindo pacientes, médicos, especialidades, convênios e agendas.

---

## 📘 Sumário

- [Tecnologias](#-tecnologias)
- [Como executar o projeto](#-como-executar-o-projeto)
- [Coleção de Endpoints](#-coleção-de-endpoints)
  - [1️⃣ AgendaController](#1️⃣-agendacontroller)
  - [2️⃣ ConvenioController](#2️⃣-conveniocontroller)
  - [3️⃣ EspecialidadeController](#3️⃣-especialidadecontroller)
  - [4️⃣ MedicoController](#4️⃣-medicocontroller)
  - [5️⃣ PacienteController](#5️⃣-pacientecontroller)
- [Modelos de DTOs](#-modelos-de-dtos)
- [Swagger / OpenAPI](#-swagger--openapi)

---

## 🧠 Tecnologias

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Hibernate**
- **Jakarta Validation**
- **Lombok**
- **Swagger / Springdoc OpenAPI**
- **Banco de Dados:** H2, MySQL ou PostgreSQL

---

## ⚙️ Como executar o projeto

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/josianebegnini/josianebegnini.git
   ```

2. **Compilar e executar**
   ```bash
   mvn spring-boot:run
   ```

3. **Acessar Swagger**
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## 🌐 Coleção de Endpoints

### 1️⃣ AgendaController
**Base URL:** `/api/v1/agendas`

Gerencia os **agendamentos de consultas médicas**.

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/api/v1/agendas` | Lista todos os agendamentos |
| `GET` | `/api/v1/agendas/{id}` | Busca um agendamento por ID |
| `POST` | `/api/v1/agendas/agendar` | Cria um novo agendamento |
| `PUT` | `/api/v1/agendas/{id}/remarcar` | Remarca uma consulta existente |
| `PATCH` | `/api/v1/agendas/{id}/cancelar` | Cancela um agendamento |
| `DELETE` | `/api/v1/agendas/{id}` | Exclui permanentemente um agendamento |

#### 🧾 Exemplo de criação de agendamento
```http
POST /api/v1/agendas/agendar
Content-Type: application/json

{
  "pacienteId": 1,
  "medicoId": 3,
  "dataHora": "2025-11-10T09:30:00",
  "tipoConsulta": "REMOTO"
}
```

#### 🔁 Remarcar consulta
```
PUT /api/v1/agendas/1/remarcar?novaDataHora=2025-11-12T14:00:00&tipoConsulta=REMOTO
```

---

### 2️⃣ ConvenioController
**Base URL:** `/api/v1/convenios`

Gerencia os **convênios médicos** aceitos pela clínica.

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/api/v1/convenios` | Lista todos os convênios |
| `GET` | `/api/v1/convenios/{id}` | Busca convênio por ID |
| `POST` | `/api/v1/convenios` | Cadastra um novo convênio |
| `PUT` | `/api/v1/convenios/{id}` | Atualiza convênio existente |
| `DELETE` | `/api/v1/convenios/{id}` | Remove convênio |

#### 🧾 Exemplo de criação
```http
POST /api/v1/convenios
Content-Type: application/json

{
  "nome": "Unimed Nacional",
  "registroANS": "123456",
  "ativo": true
}
```

---

### 3️⃣ EspecialidadeController
**Base URL:** `/api/v1/especialidades`

Gerencia as **especialidades médicas** disponíveis.

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/api/v1/especialidades` | Lista todas as especialidades |
| `GET` | `/api/v1/especialidades/{id}` | Busca especialidade por ID |
| `POST` | `/api/v1/especialidades` | Cadastra uma nova especialidade |
| `PUT` | `/api/v1/especialidades/{id}` | Atualiza uma especialidade |
| `DELETE` | `/api/v1/especialidades/{id}` | Exclui uma especialidade |

#### 🧾 Exemplo
```http
POST /api/v1/especialidades
Content-Type: application/json

{
  "nome": "Cardiologia"
}
```

---

### 4️⃣ MedicoController
**Base URL:** `/api/v1/medicos`

Gerencia os **médicos cadastrados** no sistema.

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/api/v1/medicos` | Lista paginada de médicos |
| `GET` | `/api/v1/medicos/{id}` | Busca médico por ID |
| `GET` | `/api/v1/medicos/especialidade/{especialidade}` | Lista médicos por especialidade |
| `POST` | `/api/v1/medicos` | Cadastra novo médico |
| `PUT` | `/api/v1/medicos/{id}` | Atualiza médico existente |
| `DELETE` | `/api/v1/medicos/{id}` | Exclui médico |

#### 🧾 Exemplo de criação
```http
POST /api/v1/medicos
Content-Type: application/json

{
  "nome": "Dra. Ana Souza",
  "crm": "CRM12345",
  "especialidadeId": 2,
  "email": "ana.souza@hospital.com"
}
```

#### 🔍 Filtro por especialidade
```
GET /api/v1/medicos/especialidade/Cardiologia?page=0&size=4
```

---

### 5️⃣ PacienteController
**Base URL:** `/api/v1/pacientes`

Gerencia o **cadastro e manutenção de pacientes**.

| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/api/v1/pacientes` | Lista todos os pacientes |
| `GET` | `/api/v1/pacientes/{id}` | Busca paciente por ID |
| `GET` | `/api/v1/pacientes/email/{email}` | Busca paciente por e-mail |
| `GET` | `/api/v1/pacientes/search?nome={nome}` | Busca pacientes por nome parcial |
| `POST` | `/api/v1/pacientes` | Cria novo paciente |
| `PUT` | `/api/v1/pacientes/{id}` | Atualiza todos os dados do paciente |
| `PATCH` | `/api/v1/pacientes/{id}` | Atualiza parcialmente |
| `DELETE` | `/api/v1/pacientes/{id}` | Remove paciente |

#### 🧾 Exemplo de criação
```http
POST /api/v1/pacientes
Content-Type: application/json

{
  "nome": "João da Silva",
  "email": "joao.silva@gmail.com",
  "cpf": "12345678901",
  "telefone": "11999999999",
  "convenioId": 1
}
```

#### 🔍 Busca por nome
```
GET /api/v1/pacientes/search?nome=João
```

---

## 📦 Modelos de DTOs

### 🩺 AgendamentoRequestDTO
```java
{
  "pacienteId": 1,
  "medicoId": 3,
  "dataHora": "2025-11-10T09:30:00",
  "tipoConsulta": "PRIMEIRA"
}
```

### 👨‍⚕️ MedicoRequestDTO
```java
{
  "nome": "Dr. João",
  "crm": "CRM0001",
  "especialidadeId": 1,
  "email": "joao@hospital.com"
}
```

### 👩‍🦰 PacienteRequestDTO
```java
{
  "nome": "Maria Oliveira",
  "cpf": "12345678900",
  "email": "maria@gmail.com",
  "telefone": "21999999999",
  "convenioId": 2
}
```

---

## 📖 Swagger / OpenAPI

A documentação interativa da API é gerada automaticamente via **Springdoc OpenAPI**.

**URL:**
```
http://localhost:8080/swagger-ui.html
```

Lá é possível:
- Visualizar todos os endpoints
- Testar requisições diretamente pelo navegador
- Explorar os DTOs e modelos de resposta

---

## 🧑‍💻 Autor

**Josiane Begnini**  
📧 contato: [josianebegnini@gmail.com]  
🚀 Projeto acadêmico de arquitetura de software - INFNET - Arquitetura de Plataformas - Java
