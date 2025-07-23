<img width="1000" height="435" alt="Capturar" src="https://github.com/user-attachments/assets/8749dbf8-65e6-496e-9cf2-75e9a9258107" />


# Pet Registration System

Este é um sistema de linha de comando (CLI) desenvolvido em Java para gerenciar o cadastro de pets para adoção. Ele simula uma aplicação de adoção de pets, permitindo que o usuário, dono do abrigo de animais registre, consulte, edite e remova informações sobre os animais.

## 📦 Funcionalidades

A aplicação é dividida em **passos de desenvolvimento**, com funcionalidades robustas:

### ✅ Funcionalidades principais:

1. **Leitura de perguntas via arquivo**  
    - As perguntas essenciais são carregadas de um arquivo `formulario.txt` — sem uso de `System.out.println()` fixo no código.

2. **Menu interativo com validações**  
    - Menu exibido no terminal com opções numéricas e validações de entrada.

3. **Cadastro de pets com validações**  
   Cadastro feito com base nas perguntas do arquivo, incluindo:
    - Validação de nome, peso, idade e raça.
    - Endereço detalhado.
    - Campos obrigatórios e preenchimento automático de "NÃO INFORMADO".

4. **Armazenamento em arquivos `.txt`**
    - Cada pet é salvo como arquivo único com timestamp e nome do pet.

5. **Busca por múltiplos critérios**
    - Busque pets por nome, idade, peso, sexo, endereço, etc.
    - Busca case-insensitive e sem acentos.
    - Combinação de até dois critérios.
    - Realce dos termos pesquisados em **negrito**.

6. **Alteração de dados**
    - Permite modificar qualquer informação do pet (exceto tipo e sexo) após busca.

7. **Exclusão de pets**
    - Remoção com confirmação após busca detalhada.

8. **Menu extra - Edição de perguntas**
    - Criar novas perguntas dinâmicas.
    - Alterar ou remover perguntas adicionais.
    - Reorganização automática do arquivo `formulario.txt`.
