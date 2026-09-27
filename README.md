# Sistema de Estacionamento de Carros — Projeto Final (Tópicos em Tecnologia)

Projeto Java Web feito com **Maven + Spring Boot + Spring Data JPA + Thymeleaf**, atendendo aos
requisitos do enunciado "Projeto Final – Tema Livre":

1. Banco **MySQL** com 2 tabelas em relacionamento **1:m** (`Cliente` 1 → N `Veiculo`), no mesmo
   padrão do exemplo filmeNet (Parte 6).
2. Frameworks: **Maven, Spring, Spring Data JPA**.
3. **CRUD completo** para as duas tabelas (Cliente e Veículo).
4. **1 Derived Query** implementada: `VeiculoRepository.findByCorIgnoreCase(String cor)`,
   usada na tela "Buscar Veículos por Cor".

## Modelo

- **Cliente** (`idCliente`, `nome`, `cpf`, `telefone`) — dono do(s) veículo(s).
- **Veiculo** (`id`, `placa`, `modelo`, `cor`, `idCliente` FK) — cada veículo pertence a um cliente.

## Como abrir no IntelliJ IDEA

1. Descompacte o projeto.
2. `File > Open...` e selecione a pasta `estacionamento` (a que contém o `pom.xml`).
3. Deixe o IntelliJ importar como projeto **Maven** (ele baixa as dependências automaticamente —
   é necessário estar com internet na primeira importação).
4. Confirme que o **JDK 21** (ou superior) está configurado no projeto.

## Configurar o banco MySQL

1. Tenha um MySQL rodando localmente (ex: XAMPP, MySQL Server, Docker etc.).
2. Copie `src/main/resources/application-local.properties.example` para
   `src/main/resources/application-local.properties` e ajuste a conexão local:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/estacionamento?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   ```
   O arquivo `application-local.properties` é ignorado pelo Git. O modelo `.example`
   contém somente valores ilustrativos e deve ser versionado, sem senhas reais.
   O perfil `local` é o padrão, portanto o botão Run do IntelliJ continua funcionando.
   Para outro ambiente, selecione um perfil com `SPRING_PROFILES_ACTIVE` e forneça
   `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`
   por variáveis de ambiente. O Spring Boot não carrega arquivos `.env` automaticamente.
3. Não é preciso criar o banco manualmente: `createDatabaseIfNotExist=true` cria o schema
   `estacionamento` automaticamente, e `spring.jpa.hibernate.ddl-auto=update` cria/atualiza as
   tabelas sozinho ao iniciar a aplicação.

## Rodando

- Execute a classe `EstacionamentoApplication` (botão ▶ no IntelliJ), ou pelo terminal:
  ```
  ./mvnw spring-boot:run
  ```
- Acesse: `http://localhost:8080/menus/principal`

## Telas disponíveis

| Ação                          | URL                          |
|-------------------------------|-------------------------------|
| Menu principal                | `/menus/principal`            |
| Cadastrar / listar clientes   | `/clientes/formulario` `/clientes/listagem` |
| Cadastrar / listar veículos   | `/veiculos/formulario` `/veiculos/listagem` |
| Buscar veículos por cor (Derived Query) | `/veiculos/buscar` |

## Roteiro sugerido para a apresentação

1. Mostrar as entidades `Cliente` e `Veiculo` e o `@OneToMany`/`@ManyToOne` (relacionamento 1:m).
2. Mostrar o `pom.xml` com Maven, Spring Boot, Spring Data JPA.
3. Cadastrar um cliente → cadastrar um veículo vinculado a ele → editar → excluir (CRUD completo).
4. Abrir `/veiculos/buscar`, digitar uma cor e mostrar o método `findByCorIgnoreCase` no
   `VeiculoRepository.java` (Derived Query).

## Observação

A exclusão de um `Cliente` que possua veículos é bloqueada pelo banco. A aplicação
retorna à listagem com uma mensagem amigável: exclua os veículos vinculados ou transfira-os
para outro proprietário antes de excluir o cliente.
