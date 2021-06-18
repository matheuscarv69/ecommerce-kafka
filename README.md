# 🔥 Projeto de Ecommerce ☕

Este projeto faz parte do curso de Introdução ao Kafka: Produtores, Consumidores e Streams da Alura.

Ele foi desenvolvido seguindo o conteúdo apresentado no curso com algumas modificações pessoais.

[![GitHub stars](https://img.shields.io/github/stars/matheuscarv69/orange-talents-05-template-proposta?color=orange)](https://github.com/matheuscarv69/orange-talents-05-template-proposta/stargazers)

## 🛠 Pré-Requisitos

- [Java JDK 11+](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html)
- [Kafka](https://kafka.apache.org/downloads)
- [Docker-opcional](https://www.docker.com/products/docker-desktop)
- [Docker-compose-opcional](https://docs.docker.com/compose/install/)

### 🆘 Material de apoio 
Dependendo do S.O que você esteja utilizando, a instalação do Kafka tende a ser bem trabalhosa,
então o link abaixo pode te ajudar a instalá-lo no windows. 

(Tem um **docker-compose** no projeto podes aproveita-lo para evitar dor de cabeça 😏)

- [Tutorial de instalação do kafka no Windows 10](https://ichi.pro/pt/como-instalar-o-apache-kafka-no-windows-facilmente-53170105811632)

### 🐳 Docker

Este projeto conta com um **docker-compose** para subir os containers do Kafka e o do Zookeeper.

Os requisitos necessários são ter instalados:

- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker-compose](https://docs.docker.com/compose/install/)

## 🎲 Executando o docker-compose
Com esse repositório já clonado em sua máquina e com todos os pré-requisitos atendidos.

1. Você deve ir até a raiz do projeto onde o arquivo **docker-compose.yml** está. 
2. Deve abrir um terminal na raiz do projeto.
3. Agora certifique-se que o seu Docker já está em execução.
4. Execute o seguinte comando no terminal:

```bash
docker-compose up -d
```
## 🦸‍♂️ Alguns cheatsheats do Kafka

### ❗ Acessando a pasta bin do Kafka dentro do container
Entre no console do container do Kafka via terminal ou use o Docker Desktop, então navegue até o diretório: **usr/bin**

```bash
cd usr/bin
```

### ✅ Lista todos os grupos e os descreve 
⚠ Obs: Funciona em algumas versões do kafka, não consegui descobrir a última versão compatível
```bash
./kafka-consumer-groups --all-groups --bootstrap-server kafka:29092 --describe
```
### ✅ Descreve o grupo informado
```bash
./kafka-consumer-groups --bootstrap-server kafka:29092 --group EmailService --describe
```
### ✅ Observa o Tópico informado
```bash
./kafka-console-consumer --bootstrap-server kafka:29092 --topic ECOMMERCE_NEW_ORDER
```
### ✅ Altera a quantidade de Partições do Tópico informado 
```bash
./kafka-console-consumer --bootstrap-server kafka:29092 --topic ECOMMERCE_NEW_ORDER
```

## 🚀 Tecnologias 👩‍🚀

As seguintes tecnologias foram utilizadas no desenvolvimento do projeto:

- JDK 11
- Kafka 2.13-2.8.0
- Docker
- Docker-compose

## 👨🏻‍💻 Autor

<br>
<a href="https://github.com/matheuscarv69">
 <img style="border-radius: 35%;" src="https://avatars1.githubusercontent.com/u/55814214?s=460&u=ffb1e928527a55f53df6e0d323c2fd7ba92fe0c3&v=4" width="100px;" alt=""/>
 <br />
 <sub><b>Matheus Carvalho</b></sub></a> <a href="https://github.com/matheuscarv69" title="Matheus Carvalho">🚀</a>

Feito por Matheus Carvalho, entre em contato!✌🏻
 <p align="left">
    <a href="mailto:matheus9126@gmail.com" alt="Gmail" target="_blank">
      <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white&link=mailto:matheus9126@gmail.com"/></a>
    <a href="https://www.linkedin.com/in/matheus-carvalho69/" alt="Linkedin" target="_blank">
        <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white&link=https://www.linkedin.com/in/matheus-carvalho69/"/></a>  
    <a href="https://www.instagram.com/_mmcarvalho/" alt="Instagram" target="_blank">
      <img src="https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white&link=https://www.instagram.com/_mmcarvalho/"/></a>  
  </p>