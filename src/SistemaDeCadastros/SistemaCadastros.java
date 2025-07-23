package SistemaDeCadastros;

import java.io.*;
import java.nio.file.Files;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SistemaCadastros {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        telaInicial(input);
    }

    public static void telaInicial(Scanner input) {
        while (true) {
            System.out.println("1 - Iniciar o sistema para cadastro de PETS");
            System.out.println("2 - Iniciar o sistema para alterar formulário");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int iniciar = input.nextInt();
                input.nextLine();

                switch (iniciar) {
                    case 1:
                        exibirMenu(input);
                        break;
                    case 2:
                        formularioMenu(input);
                        break;
                    case 3:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida! Digite um número entre 1 e 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                input.nextLine();
            }
        }
    }

    public static void exibirMenu(Scanner input) {
        while (true) {
            System.out.println("===== MENU INICIAL =====");
            System.out.println("1 - Cadastrar um novo pet");
            System.out.println("2 - Alterar os dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
            System.out.println("5 - Listar pets por algum critério (idade, nome, raça)");
            System.out.println("6 - Voltar para o menu inicial");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int formulario = input.nextInt();
                input.nextLine();

                switch (formulario) {
                    case 1:
                        cadastrarPet(input);
                        System.out.println();
                        break;
                    case 2:
                        alterarPetCadastrado(input);
                        System.out.println();
                        break;
                    case 3:
                        deletarPet(input);
                        System.out.println();
                        break;
                    case 4:
                        listarTodosPet();
                        System.out.println();
                        break;
                    case 5:
                        listarCriterioPet(input);
                        System.out.println();
                        break;
                    case 6:
                        return;
                    case 7:
                        System.out.println("Saindo...");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida! Digite um número entre 1 e 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                input.nextLine();
            }
        }
    }

    public static void cadastrarPet(Scanner input) {
        File file = new File("formulario.txt");

        List<String> todasPerguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                todasPerguntas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo 'formulario.txt': " + e.getMessage());
            return;
        }

        String nome = Pet.NAO_INFORMADO;
        Pet.TipoPet tipo = null;
        Pet.SexoPet sexo = null;
        String numero = Pet.NAO_INFORMADO;
        String rua = "";
        String cidade = "";
        int idade = Pet.NAO_INFORMADO_IDADE;
        int peso = Pet.NAO_INFORMADO_PESO;
        String raca = Pet.NAO_INFORMADO;

        List<String> perguntasExtras = new ArrayList<>();
        List<String> respostasExtras = new ArrayList<>();

        int perguntaNumero = 1;

        for (String pergunta : todasPerguntas) {
            System.out.println(pergunta);

            switch (perguntaNumero) {
                case 1:
                    while (true) {
                        String resposta1 = input.nextLine().trim();

                        if (resposta1.isEmpty()) {
                            nome = Pet.NAO_INFORMADO;
                            break;
                        } else if (resposta1.split(" ").length < 2 || !resposta1.matches("[A-Za-zÀ-ÿ ]+")) {
                            System.out.println("Nome inválido! Deve inserir nome e sobrenome com letras apenas.");
                            System.out.print("Por favor, Digite novamente: ");
                        } else {
                            nome = resposta1;
                            break;
                        }
                    }
                    break;

                case 2:
                    while (true) {
                        String resposta2 = input.nextLine().trim();

                        try {
                            tipo = Pet.TipoPet.valueOf(resposta2.toUpperCase());
                            break;
                        } catch (Exception e) {
                            System.out.println("Tipo inválido! Use CACHORRO ou GATO.");
                            System.out.print("Por favor, Digite novamente: ");
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        String resposta3 = input.nextLine().trim();

                        if (resposta3.equalsIgnoreCase("M")) {
                            sexo = Pet.SexoPet.MACHO;
                            break;
                        } else if (resposta3.equalsIgnoreCase("F")) {
                            sexo = Pet.SexoPet.FEMEA;
                            break;
                        } else {
                            System.out.println("Sexo inválido! Use M ou F.");
                            System.out.print("Por favor, Digite novamente: ");
                        }
                    }
                    break;

                case 4:
                    while (true) {
                        System.out.print("Número da casa: ");
                        numero = input.nextLine().trim();

                        if (numero.isEmpty()) {
                            numero = Pet.NAO_INFORMADO;
                            break;
                        } else if (!numero.matches("\\d+")) {
                            System.out.println("Número inválido! Tente novamente.");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.print("Rua: ");
                        rua = input.nextLine().trim();

                        if (rua.isEmpty()) {
                            break;
                        } else if (!rua.matches("[A-Za-zÀ-ÿ0-9 ]*")) {
                            System.out.println("Rua inválida! Use apenas letras e números.");
                            System.out.print("Por favor, Digite novamente: ");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.print("Cidade: ");
                        cidade = input.nextLine().trim();

                        if (cidade.isEmpty()) {
                            break;
                        } else if (!cidade.matches("[A-Za-zÀ-ÿ ]*")) {
                            System.out.println("Cidade inválida! Use apenas letras.");
                        } else {
                            break;
                        }
                    }
                    break;

                case 5:
                    while (true) {
                        String resposta5 = input.nextLine().trim();

                        if (resposta5.isEmpty()) {
                            idade = Pet.NAO_INFORMADO_IDADE;
                            break;
                        } else {
                            try {
                                double idadeDouble = Double.parseDouble(resposta5.replace(",", "."));
                                if (idadeDouble > 20) {
                                    System.out.println("Idade não pode ser maior que 20 anos.");
                                    System.out.print("Por favor, Digite novamente: ");
                                } else if (idadeDouble < 1) {
                                    idade = 0;
                                    break;
                                } else {
                                    idade = (int) Math.round(idadeDouble);
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Idade inválida! Digite um número válido.");
                                System.out.print("Por favor, digite novamente: ");
                            }
                        }
                    }
                    break;

                case 6:
                    while (true) {
                        String resposta6 = input.nextLine().trim();

                        if (resposta6.isEmpty()) {
                            peso = Pet.NAO_INFORMADO_PESO;
                            break;
                        } else {
                            try {
                                double pesoDouble = Double.parseDouble(resposta6.replace(",", "."));
                                if (pesoDouble < 0.5 || pesoDouble > 60) {
                                    System.out.println("Peso fora do intervalo permitido (0.5kg a 60kg).");
                                    System.out.print("Por favor, digite novamente: ");
                                } else {
                                    peso = (int) Math.round(pesoDouble);
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Peso inválido! Digite um número válido.");
                                System.out.print("Por favor, digite novamente: ");
                            }
                        }
                    }
                    break;

                case 7:
                    while (true) {
                        String resposta7 = input.nextLine().trim();

                        if (resposta7.isEmpty()) {
                            raca = Pet.NAO_INFORMADO;
                            break;
                        } else if (!resposta7.matches("[A-Za-zÀ-ÿ ]+")) {
                            System.out.println("Raça inválida! Digite apenas letras.");
                            System.out.print("Por favor, digite novamente: ");
                        } else {
                            raca = resposta7;
                            break;
                        }
                    }
                    break;

                default:
                    String resposta = input.nextLine().trim();
                    if (resposta.isEmpty()) {
                        resposta = "NÃO INFORMADO";
                    }
                    perguntasExtras.add(pergunta);
                    respostasExtras.add(resposta);
                    break;
            }

            perguntaNumero++;
        }

        Endereco endereco = new Endereco(rua, numero, cidade);
        Pet novoPet = new Pet(nome, tipo, sexo, endereco, idade, peso, raca);

        System.out.println("-------------------------");
        System.out.println("Pet cadastrado com sucesso!");
        System.out.println("-------------------------");

        salvarPet(novoPet, perguntasExtras, respostasExtras);
    }

    public static void salvarPet(Pet pet, List<String> perguntasExtras, List<String> respostasExtras) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String dateNow = now.format(formatter);

        String nomeArquivo = dateNow + "-" + pet.getNome().toUpperCase().replace(" ", "") + ".TXT";

        File file = new File("petsCadastrados", nomeArquivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("1 - " + pet.getNome());
            bw.newLine();
            bw.write("2 - " + pet.getTipo().toString());
            bw.newLine();
            bw.write("3 - " + pet.getSexo().toString());
            bw.newLine();
            bw.write("4 - " + pet.getEndereco().toString());
            bw.newLine();
            bw.write("5 - " + pet.getIdade() + " anos");
            bw.newLine();
            bw.write("6 - " + pet.getPeso() + "kg");
            bw.newLine();
            bw.write("7 - " + pet.getRaca());
            bw.newLine();

            for (int i = 0; i < perguntasExtras.size(); i++) {
                int numPergunta = 8 + i;
                String perguntaSemNumero = perguntasExtras.get(i).replaceFirst("^\\d+\\s*-\\s*", "");
                bw.write(numPergunta + " - " + "[EXTRA - " + perguntaSemNumero + "]" + " - " + respostasExtras.get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarTodosPet() {
        File pasta = new File("petsCadastrados");
        File[] arquivos = pasta.listFiles();
        int contador = 1;

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String nome = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String tipo = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String sexo = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String endereco = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String idade = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String peso = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String raca = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");

                    System.out.println(contador + ". " + nome + " - " + tipo + " - " + sexo + " - " +
                            endereco + " - " + idade + " - " + peso + " - " + raca);
                    contador++;
                } catch (IOException e) {
                    System.err.println("Erro ao ler arquivo: " + arquivo.getName());
                }
            }
        } else {
            System.out.println("Nenhum pet encontrado.");
        }

        if (contador == 1) {
            System.out.println("Nenhum pet cadastrado.");
        }
    }

    public static String normalizer(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return texto.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }

    public static List<File> listarCriterioPet(Scanner input) {
        String buscaTipo;
        String tipoNormalizer;

        while (true) {
            System.out.print("Digite o tipo do pet (Gato ou Cachorro): ");
            buscaTipo = input.nextLine();
            tipoNormalizer = normalizer(buscaTipo);

            if (tipoNormalizer.equals("gato") || tipoNormalizer.equals("cachorro")) {
                break;
            } else {
                System.out.println("Tipo inválido. Digite apenas Gato ou Cachorro.");
            }
        }

        System.out.println("Escolha o(s) critério(s) de busca:");
        System.out.println("1 - Nome ou sobrenome");
        System.out.println("2 - Sexo");
        System.out.println("3 - Idade");
        System.out.println("4 - Peso");
        System.out.println("5 - Raça");
        System.out.println("6 - Endereço");
        System.out.println("7 - Nome + Idade");
        System.out.println("8 - Idade + Peso");

        int opcoes = -1;
        while (opcoes < 1 || opcoes > 8) {
            try {
                System.out.print("Opção (1 a 8): ");
                opcoes = Integer.parseInt(input.nextLine());
                if (opcoes < 1 || opcoes > 8) {
                    System.out.println("Número inválido. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }

        String buscaNome = "", buscaSexo = "", buscaIdade = "", buscaPeso = "", buscaRaca = "", buscaEndereco = "";

        switch (opcoes) {
            case 1:
                System.out.print("Digite o nome ou sobrenome: ");
                buscaNome = input.nextLine();
                break;
            case 2:
                System.out.print("Digite o sexo: ");
                buscaSexo = input.nextLine();
                break;
            case 3:
                System.out.print("Digite a idade: ");
                buscaIdade = input.nextLine();
                break;
            case 4:
                System.out.print("Digite o peso: ");
                buscaPeso = input.nextLine();
                break;
            case 5:
                System.out.print("Digite a raça: ");
                buscaRaca = input.nextLine();
                break;
            case 6:
                System.out.print("Digite o endereço: ");
                buscaEndereco = input.nextLine();
                break;
            case 7:
                System.out.print("Digite o nome ou sobrenome: ");
                buscaNome = input.nextLine();
                System.out.print("Digite a idade: ");
                buscaIdade = input.nextLine();
                break;
            case 8:
                System.out.print("Digite a idade: ");
                buscaIdade = input.nextLine();
                System.out.print("Digite o peso: ");
                buscaPeso = input.nextLine();
                break;
        }

        File pasta = new File("petsCadastrados");
        File[] files = pasta.listFiles();
        int contador = 1;
        List<File> arquivosFiltrados = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String nome = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String tipo = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String sexo = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String endereco = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String idade = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String peso = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");
                    String raca = br.readLine().replaceFirst("^\\d+\\s*-\\s*", "");

                    boolean matches = normalizer(tipo).contains(tipoNormalizer);

                    if (opcoes == 1 || opcoes == 7) {
                        matches = matches && normalizer(nome).contains(normalizer(buscaNome));
                    }
                    if (opcoes == 2) {
                        matches = matches && normalizer(sexo).contains(normalizer(buscaSexo));
                    }
                    if (opcoes == 3 || opcoes == 7 || opcoes == 8) {
                        matches = matches && normalizer(idade).contains(normalizer(buscaIdade));
                    }
                    if (opcoes == 4 || opcoes == 8) {
                        matches = matches && normalizer(peso).contains(normalizer(buscaPeso));
                    }
                    if (opcoes == 5) {
                        matches = matches && normalizer(raca).contains(normalizer(buscaRaca));
                    }
                    if (opcoes == 6) {
                        matches = matches && normalizer(endereco).contains(normalizer(buscaEndereco));
                    }

                    if (matches) {
                        String nomeFormatado = aplicarNegrito(nome, buscaNome);
                        String sexoFormatado = aplicarNegrito(sexo, buscaSexo);
                        String idadeFormatado = aplicarNegrito(idade, buscaIdade);
                        String pesoFormatado = aplicarNegrito(peso, buscaPeso);
                        String racaFormatado = aplicarNegrito(raca, buscaRaca);
                        String enderecoFormatado = aplicarNegrito(endereco, buscaEndereco);

                        System.out.println(contador + ". " + nomeFormatado + " - " + tipo + " - " +
                                sexoFormatado + " - " + enderecoFormatado + " - " +
                                idadeFormatado + " - " + pesoFormatado + " - " + racaFormatado);

                        arquivosFiltrados.add(file);
                        contador++;
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo: " + file.getName());
                }
            }
        } else {
            System.out.println("Nenhum pet encontrado.");
        }

        if (contador == 0) {
            System.out.println("Nenhum pet encontrado com os critérios especificados.");
        }

        return arquivosFiltrados;
    }

    private static String aplicarNegrito(String texto, String termoBusca) {
        if (termoBusca == null || termoBusca.isEmpty()) {
            return texto;
        }
        return texto.replaceAll("(?i)(" + Pattern.quote(termoBusca) + ")", "\033[1m$1\033[0m");
    }

    public static void alterarPetCadastrado(Scanner input) {
        List<File> arquivosFiltrados = listarCriterioPet(input);

        if (arquivosFiltrados.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
            return;
        }

        System.out.print("Digite o número do pet que deseja alterar: ");
        int opcao;

        while (true) {
            try {
                opcao = Integer.parseInt(input.nextLine());
                if (opcao >= 1 && opcao <= arquivosFiltrados.size()) {
                    break;
                } else {
                    System.out.print("Número inválido. Digite novamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }

        File petSelecionado = arquivosFiltrados.get(opcao - 1);

        List<String> dados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(petSelecionado))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                dados.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo do pet.");
            return;
        }

        String nome = dados.get(0).substring(4);
        String tipo = dados.get(1).substring(4);
        String sexo = dados.get(2).substring(4);
        String endereco = dados.get(3).substring(4);
        String idade = dados.get(4).substring(4).replace(" anos", "");
        String peso = dados.get(5).substring(4).replace("kg", "");
        String raca = dados.get(6).substring(4);

        System.out.println("Alterando dados do pet: " + nome);
        System.out.println("Tipo: " + tipo + " (não pode ser alterado)");
        System.out.println("Sexo: " + sexo + " (não pode ser alterado)");

        System.out.print("Novo nome [" + nome + "]: ");
        String novoNome = input.nextLine().trim();
        if (novoNome.isBlank()) novoNome = nome;

        System.out.print("Novo endereço [" + endereco + "]: ");
        String novoEndereco = input.nextLine().trim();
        if (novoEndereco.isBlank()) novoEndereco = endereco;

        System.out.print("Nova idade [" + idade + "]: ");
        String novaIdade = input.nextLine().trim();
        if (novaIdade.isBlank()) novaIdade = idade;

        System.out.print("Novo peso [" + peso + "]: ");
        String novoPeso = input.nextLine().trim();
        if (novoPeso.isBlank()) novoPeso = peso;

        System.out.print("Nova raça [" + raca + "]: ");
        String novaRaca = input.nextLine().trim();
        if (novaRaca.isBlank()) novaRaca = raca;

        boolean houveAlteracao =
            !novoNome.equals(nome) ||
            !novoEndereco.equals(endereco) ||
            !novaIdade.equals(idade) ||
            !novoPeso.equals(peso) ||
            !novaRaca.equals(raca);

        if (houveAlteracao) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(petSelecionado))) {
                bw.write("1 - " + novoNome);
                bw.newLine();
                bw.write("2 - " + tipo);
                bw.newLine();
                bw.write("3 - " + sexo);
                bw.newLine();
                bw.write("4 - " + novoEndereco);
                bw.newLine();
                bw.write("5 - " + novaIdade + " anos");
                bw.newLine();
                bw.write("6 - " + novoPeso + "kg");
                bw.newLine();
                bw.write("7 - " + novaRaca);
                bw.newLine();

                for (int i = 7; i < dados.size(); i++) {
                    bw.write(dados.get(i));
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Erro ao salvar os dados do pet.");
                return;
            }

            String nomeOriginalFormatado = nome.toUpperCase().replace(" ", "");
            String novoNomeFormatado = novoNome.toUpperCase().replace(" ", "");

            if (!novoNomeFormatado.equals(nomeOriginalFormatado)) {
                String nomeAntigoArquivo = petSelecionado.getName();
                int indiceHifen = nomeAntigoArquivo.indexOf('-');
                if (indiceHifen != -1) {
                    String prefixoData = nomeAntigoArquivo.substring(0, indiceHifen + 1);
                    String novoNomeArquivo = prefixoData + novoNomeFormatado + ".TXT";
                    File novoArquivo = new File(petSelecionado.getParent(), novoNomeArquivo);

                    if (novoArquivo.exists()) {
                        System.out.println("Já existe um pet com esse nome e data. O nome do arquivo não foi alterado.");
                    } else {
                        boolean renomeado = petSelecionado.renameTo(novoArquivo);
                        if (renomeado) {
                            System.out.println("Arquivo renomeado para: " + novoNomeArquivo);
                        } else {
                            System.out.println("Erro ao renomear o arquivo.");
                        }
                    }
                } else {
                    System.out.println("Formato de nome de arquivo inválido. Renomeação ignorada.");
                }
            }

            System.out.println("Pet alterado com sucesso!");
        } else {
            System.out.println("Nenhuma alteração feita. O arquivo não foi modificado.");
        }
    }

    public static void deletarPet(Scanner input) {
        List<File> resultadosBusca = listarCriterioPet(input);

        if (resultadosBusca.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados.");
            return;
        }

        System.out.print("Digite o número do pet que deseja deletar: ");
        int opcao;

        while (true) {
            String entrada = input.nextLine();
            try {
                opcao = Integer.parseInt(entrada);
                if (opcao >= 1 && opcao <= resultadosBusca.size()) {
                    break;
                } else {
                    System.out.print("Número inválido. Digite novamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }

        File petSelecionado = resultadosBusca.get(opcao - 1);

        System.out.println("Tem certeza que deseja deletar este pet?");
        System.out.print("Digite Sim para confirmar e Não para cancelar: ");
        String confirmacao = input.nextLine().trim();

        if (confirmacao.equalsIgnoreCase("SIM")) {
            if (petSelecionado.delete()) {
                System.out.println("Pet deletado com sucesso!");
            } else {
                System.out.println("Erro ao deletar o pet.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public static void formularioMenu(Scanner input) {
        while (true) {
            System.out.println("===== MENU DO FORMULÁRIO =====");
            System.out.println("1 - Criar nova pergunta");
            System.out.println("2 - Alterar pergunta existente");
            System.out.println("3 - Excluir pergunta existente");
            System.out.println("4 - Voltar para o menu inicial");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                    criarNovaPergunta(input);
                    break;
                case 2:
                    alterarPerguntaExtra(input);
                    break;
                case 3:
                    excluirPerguntaExtra(input);
                    break;
                case 4:
                    return;
                case 5:
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public static void criarNovaPergunta(Scanner input) {
        File file = new File("formulario.txt");

        try {
            List<String> perguntas = Files.readAllLines(file.toPath());

            int ultimaOrdem = 0;
            for (String linha : perguntas) {
                if (linha.contains(" - ")) {
                    String[] partes = linha.split(" - ", 2);
                    try {
                        int numero = Integer.parseInt(partes[0].trim());
                        if (numero > ultimaOrdem) {
                            ultimaOrdem = numero;
                        }
                    } catch (NumberFormatException e) {
                        throw new IOException("Linha no formato inválido na numeração: " + linha);
                    }
                }
            }

            System.out.print("Digite a nova pergunta: ");
            String novaPergunta = input.nextLine().trim();

            if (novaPergunta.isBlank()) {
                System.out.println("Pergunta não pode ser vazia.");
                return;
            }

            perguntas.add((ultimaOrdem + 1) + " - " + novaPergunta);
            Files.write(file.toPath(), perguntas);

            System.out.println("Pergunta adicionada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler ou escrever o arquivo.");
        }
    }

    public static void alterarPerguntaExtra(Scanner input) {
        File file = new File("formulario.txt");

        try {
            List<String> perguntas = Files.readAllLines(file.toPath());

            if (perguntas.size() <= 7) {
                System.out.println("Não existem perguntas extras para alterar.");
                return;
            }

            for (int i = 7; i < perguntas.size(); i++) {
                System.out.println(perguntas.get(i));
            }

            System.out.print("Digite o número da pergunta que deseja alterar: ");
            int numero = Integer.parseInt(input.nextLine());

            if (numero <= 7 || perguntas.stream().noneMatch(p -> p.startsWith(numero + " -"))) {
                System.out.println("Você só pode alterar perguntas extras (acima de 7 e que existam).");
                return;
            }

            System.out.print("Digite a nova pergunta: ");
            String novaPergunta = input.nextLine().trim();

            perguntas.set(numero - 1, numero + " - " + novaPergunta);
            Files.write(file.toPath(), perguntas);

            System.out.println("Pergunta alterada com sucesso!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao alterar pergunta.");
        }
    }

    public static void excluirPerguntaExtra(Scanner input) {
        File file = new File("formulario.txt");

        try {
            List<String> perguntas = Files.readAllLines(file.toPath());

            if (perguntas.size() <= 7) {
                System.out.println("Não existem perguntas extras para excluir.");
                return;
            }

            for (int i = 7; i < perguntas.size(); i++) {
                System.out.println(perguntas.get(i));
            }

            System.out.print("Digite o número da pergunta que deseja excluir: ");
            int numero = Integer.parseInt(input.nextLine());

            if (numero <= 7 || perguntas.stream().noneMatch(p -> p.startsWith(numero + " -"))) {
                System.out.println("Você só pode excluir perguntas extras (acima de 7 e que existam).");
                return;
            }

            System.out.print("Tem certeza que deseja excluir esta pergunta? (SIM/NÃO): ");
            String confirmacao = input.nextLine().trim().toUpperCase();

            if (!confirmacao.equals("SIM")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            int indiceRemover = -1;
            for (int i = 0; i < perguntas.size(); i++) {
                if (perguntas.get(i).startsWith(numero + " -")) {
                    indiceRemover = i;
                    break;
                }
            }

            if (indiceRemover == -1) {
                System.out.println("Pergunta não encontrada.");
                return;
            }

            perguntas.remove(indiceRemover);

            for (int i = indiceRemover; i < perguntas.size(); i++) {
                String[] partes = perguntas.get(i).split(" - ", 2);
                if (partes.length == 2) {
                    int novoNumero = numero + (i - indiceRemover);
                    perguntas.set(i, novoNumero + " - " + partes[1]);
                }
            }

            Files.write(file.toPath(), perguntas);

            System.out.println("Pergunta excluída com sucesso!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao excluir pergunta.");
        }
    }
}




