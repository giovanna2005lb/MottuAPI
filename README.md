# MottuAPI
📌 Nota: Projeto desenvolvido para fins acadêmicos na disciplina de Java Advanced
API REST para gerenciamento de aluguel de motos, com autenticação baseada em JWT, controle de acesso por perfil (cliente e mecânico), cache e paginação.

## Integrantes
RM555679 - Lavinia Soo Hyun Park

RM556242 - Giovanna Laturague Bueno

## Perfis de Acesso

- `CLIENTE`: pode alugar, reagendar e cancelar aluguéis, consegue visualizar apenas motos que estejam no estado de DISPONÍVEL.
- `MECANICO`: pode registrar novas motos, assim como edita-las e apaga-las.

## Endpoints

### 🔐 Contas
- `POST /users`: Cria um usuário.
```json
{
  "nome": "Mecânico Teste",
  "email": "mecanico@email.com",
  "senha": "123456",
  "role": "MECANICO"
}
```
- `POST /login`: Autentica o usuário e retorna o token JWT.
```json
{
  "email": "mecanico@email.com",
  "password": "123456"
}
```

### 🛵 Motos
- `POST /motos`: Registra uma moto.
- **Permissão:** `MECANICO`
```json
{
  "modelo": "CG 160 Start",
  "marca": "Honda",
  "status": "DISPONIVEL",
  "problema": null,
  "ano": 2023,
  "precoPorDia": 89.90
}
```
- Mecanicos também conseguem deletar ou editar uma moto.
- Mecanicos conseguem ver todas as motos registradas, independente de seu estado.

### 📅 Aluguéis (`/alugueis`)

#### Alugar Moto

- `POST /alugueis`
- **Permissão:** `CLIENTE`
- **Body:**
```json
{
  "motoId": 1,
  "dataInicio": "2025-05-30",
  "dataFim": "2025-06-05"
}
```
- Clientes podem reagendar (Editar) ou cancelar (apagar) um aluguel.
- Quando o cliente aluga uma moto, ela muda para o estado de "ALUGADA"

## ▶️ Como Executar
```bash
./mvnw spring-boot:run
```
## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- Oracle Database
- Cache com Spring (`@Cacheable`, `@CacheEvict`)
- Validações com Bean Validation (Jakarta)
- Maven
