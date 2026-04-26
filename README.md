# Infinity Air Rewards

A DimDim lançou o iPhone Infinity Air e criou uma campanha promocional para o mês de maio de 2026. Clientes que comprarem o aparelho nesse período recebem o AirDock, uma base de carregamento sem fio exclusiva.

Este projeto implementa uma API REST em Java Spring Boot conectada a um banco Oracle XE, ambos executando em containers Docker e conectados por uma rede Docker.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Oracle XE
- Docker
- Maven

## Estrutura da solução

A solução possui dois containers:

- `app_rm564995`: aplicação Java Spring Boot
- `oracle_rm564995`: banco de dados Oracle XE

Os containers se comunicam pela rede Docker:

- `dimdim_air_rewards`

O banco utiliza o volume nomeado:

- `dimdim_oradata`

## Rotas da API

| Método | Rota | Descrição |
|---|---|---|
| GET | `/beneficios` | Lista todos os benefícios |
| GET | `/beneficios/{id}` | Busca um benefício por ID |
| POST | `/beneficios` | Cria um benefício |
| PUT | `/beneficios/{id}` | Atualiza um benefício |
| DELETE | `/beneficios/{id}` | Remove um benefício |

## Como executar com Docker

### 1. Criar a rede Docker

```bash
docker network create dimdim_air_rewards

```

### 2. Criar o volume nomeado

```bash
docker volume create dimdim_oradata
```

### 3. Subir o container Oracle

```bash
docker run -d --name oracle_rm564995 --network dimdim_air_rewards -p 1521:1521 -e ORACLE_PASSWORD=Dimdim123 -e APP_USER=dimdim -e APP_USER_PASSWORD=Dimdim123 -v dimdim_oradata:/opt/oracle/oradata gvenzl/oracle-xe:21-slim
```

### 4. Criar a imagem da aplicação Java

```bash
docker build -t infinity-air-rewards-rm564995 .
```

### 5. Subir o container da aplicação Java

```bash
docker run -d --name app_rm564995 --network dimdim_air_rewards -p 8080:8080 -e "DB_URL=jdbc:oracle:thin:@oracle_rm564995:1521/XEPDB1" -e "DB_USER=dimdim" -e "DB_PASSWORD=Dimdim123" infinity-air-rewards-rm564995
```

### 6. Verificar os containers em execução

```bash
docker ps
```

Devem aparecer os containers:

```text
app_rm564995
oracle_rm564995
```

### 7. Acessar a API

A API estará disponível em:

```text
http://127.0.0.1:8080/beneficios
```

Em alguns ambientes Windows, o acesso por `localhost` pode tentar usar IPv6. Caso ocorra erro de conexão, utilize `127.0.0.1`.

## Exemplos de teste da API

### Criar um benefício

```bash
curl -X POST http://127.0.0.1:8080/beneficios -H "Content-Type: application/json" -d "{\"nomeCliente\":\"Steves Jobs\",\"emailCliente\":\"steves@dimdim.com\",\"produtoComprado\":\"iPhone Infinity Air\",\"dataCompra\":\"2026-05-10\",\"statusBeneficio\":\"APROVADO\"}"
```

### Listar benefícios

```bash
curl http://127.0.0.1:8080/beneficios
```

### Atualizar um benefício

Troque o ID conforme o retorno do POST.

```bash
curl -X PUT http://127.0.0.1:8080/beneficios/1 -H "Content-Type: application/json" -d "{\"nomeCliente\":\"Steves Jobs\",\"emailCliente\":\"steves@dimdim.com\",\"produtoComprado\":\"iPhone Infinity Air\",\"dataCompra\":\"2026-05-10\",\"statusBeneficio\":\"ENVIADO\"}"
```

### Deletar um benefício

Troque o ID conforme o registro criado.

```bash
curl -X DELETE http://127.0.0.1:8080/beneficios/1
```

## Consultar dados no Oracle

Entrar no Oracle pelo container:

```bash
docker exec -it oracle_rm564995 sqlplus dimdim/Dimdim123@XEPDB1
```

Formatar a saída no SQLPlus:

```sql
SET LINESIZE 200
COLUMN NOME_CLIENTE FORMAT A20
COLUMN STATUS_BENEFICIO FORMAT A20
```

Consultar a tabela:

```sql
SELECT ID, NOME_CLIENTE, STATUS_BENEFICIO
FROM BENEFICIOS_AIRDOCK;
```

## Comandos para evidências

Os comandos abaixo devem ser executados para gerar evidências da execução dos containers, imagens, volumes e redes:

```bash
docker ps
docker image ls
docker volume ls
docker network ls
```

## Observação sobre o projeto

O container `oracle_rm564995` executa o banco Oracle XE com volume nomeado para persistência dos dados.

O container `app_rm564995` executa a aplicação Java Spring Boot e se conecta ao banco Oracle pela rede Docker `dimdim_air_rewards`.

A aplicação implementa um CRUD completo para os benefícios da campanha Infinity Air Rewards.