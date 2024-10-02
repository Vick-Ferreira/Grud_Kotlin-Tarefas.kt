# Gerenciador de Tarefas

Este projeto foi desenvolvido em **Kotlin** e faz uso do **Firebase**, incluindo o **Firebase Realtime Database** e a funcionalidade de autenticação. O objetivo principal do aplicativo é proporcionar uma experiência eficiente e amigável para o gerenciamento de tarefas.

## Funcionalidades

- Listar, editar, excluir e adicionar tarefas.
- Sistema de cadastro e login via e-mail e senha.
- Autenticação segura utilizando o Firebase Authentication.
- Acesso rápido após o primeiro login, com verificação automática de credenciais.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação utilizada para o desenvolvimento do aplicativo.
- **Firebase Authentication**: Gerenciamento de autenticação de usuários.
- **Firebase Realtime Database**: Banco de dados em tempo real para armazenamento das tarefas dos usuários.

## Como Funciona

1. **Cadastro**: O usuário se cadastra no aplicativo fornecendo um e-mail e uma senha.
2. **Login**: Após o cadastro, o usuário faz login com suas credenciais.
3. **Acesso Rápido**: Após o primeiro login, o sistema verifica automaticamente as credenciais no próximo acesso, direcionando o usuário diretamente para a tela principal.
4. **Gerenciamento de Tarefas**: Os usuários podem criar, editar, excluir e listar suas tarefas no aplicativo.

## Segurança

- A autenticação com Firebase garante que apenas usuários autenticados possam acessar suas tarefas, protegendo os dados de forma segura.
- A comunicação com o Firebase é feita de forma segura, garantindo a integridade e a privacidade das informações dos usuários.

## Requisitos

- **Kotlin 1.4 ou superior**
- **Firebase SDK**
- **Android Studio**
