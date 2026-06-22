import exceptions.LimiteDeVagasException;
import java.util.ArrayList;
import java.util.Scanner;
import model.projetos.ProjetoPesquisa;
import model.relatorio.Relatorio;
import model.usuarios.Aluno;
import model.usuarios.Coordenador;
import model.usuarios.Professor;
import model.usuarios.Usuario;
import model.usuarios.UsuarioRepositorio;
import utilidades.LimparTela;

public class App {
    
    private static final Scanner scan = new Scanner(System.in);
    private static Usuario user;
    
    public static void main(String[] args) throws IllegalAccessException, LimiteDeVagasException {

        boolean executando = true;
        while (executando) {
            LimparTela.Limpar();

            menuInicio();
            String opcao = scan.nextLine().trim();
            switch (opcao) {
                case "1" -> cadastrarUsuario();

                case "2" -> {
                    loginUsuario();
                    switch (user) {
                        case Aluno alunoLogado -> menuAluno(alunoLogado);
                        case Professor professorLogado -> menuProfessor(professorLogado);
                        case Coordenador coordenadorLogado -> menuCoordenador(coordenadorLogado);
                        default -> {}
                    }
                }

                case "3" -> {
                    fecharPrograma();
                    executando = false;
                }

                default -> {
                    System.out.println("Opção inválida!");
                    scan.nextLine();
                }
            }
        }
    }

    public static void menuInicio() {
        System.out.println("====== SGPPU - SISTEMA DE GERENCIAMENTO DE PROJETOS DE PESQUISA UNIVERSITARIOS ======");
        System.out.println("1 - CADASTRAR-SE");
        System.out.println("2 - LOGIN");
        System.out.println("3 - SAIR");
    }

    private static boolean alunoTemProjetos(Aluno aluno) {
        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
            if (projeto.contemParticipante(aluno)) {
                return true;
            }
        }
        return false;
    }

    public static void cadastrarUsuario() {
        boolean running = true;
        while (running) {
            LimparTela.Limpar();
            System.out.println("====== FACA SEU CADASTRO NO SISTEMA! ======");
            System.out.println("1 - PARA COORDENADOR");
            System.out.println("2 - PARA PROFESSOR");
            System.out.println("3 - PARA ALUNO");

            String opcao = scan.nextLine().trim();
            switch (opcao) {
                case "1" -> {
                    Coordenador coordenador = new Coordenador();
                    boolean cadastroSucesso = false;

                    while (!cadastroSucesso) {
                        System.out.print("DIGITE SEU NOME: ");
                        String nome = scan.nextLine().trim();

                        System.out.print("DIGITE SUA ÁREA: ");
                        String area = scan.nextLine().trim();

                        System.out.print("CRIE SEU LOGIN: ");
                        String login = scan.nextLine().trim();

                        System.out.print("CRIE SUA SENHA: ");
                        String password = scan.nextLine().trim();

                        try {
                            cadastroSucesso = coordenador.cadastro(nome, area, login, password);
                            System.out.println("Cadastro realizado com sucesso!");
                        }
                        catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    running = false;
                }

                case "2" -> {

                    Professor professor = new Professor();
                    boolean cadastroSucesso = false;

                    while (!cadastroSucesso) {
                        System.out.print("DIGITE SEU NOME: ");
                        String nome = scan.nextLine().trim();

                        System.out.print("DIGITE SUA ÁREA: ");
                        String area = scan.nextLine().trim();

                        System.out.print("CRIE SEU LOGIN: ");
                        String login = scan.nextLine().trim();

                        System.out.print("CRIE SUA SENHA: ");
                        String password = scan.nextLine().trim();

                        try {
                            cadastroSucesso = professor.cadastro(nome, area, login, password);
                            System.out.println("Cadastro realizado com sucesso!");
                        }
                        catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    running = false;
                }

                case "3" -> {
                    Aluno aluno = new Aluno();
                    boolean cadastroSucesso = false;

                    while (!cadastroSucesso) {
                        System.out.print("DIGITE SEU NOME: ");
                        String nome = scan.nextLine().trim();

                        System.out.print("DIGITE SEU CURSO: ");
                        String curso = scan.nextLine().trim();

                        System.out.print("CRIE SEU LOGIN: ");
                        String login = scan.nextLine().trim();

                        System.out.print("CRIE SUA SENHA: ");
                        String password = scan.nextLine().trim();

                        try {
                            cadastroSucesso = aluno.cadastro(nome, curso, login, password);
                            System.out.println("Cadastro realizado com sucesso!");
                        }
                        catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    running = false;
                }
                
                default -> System.out.println("Opção inválida!");
            }
        }

        System.out.println("\nPressione ENTER para voltar ao menu principal...");
        scan.nextLine();
    }

    public static void loginUsuario() throws IllegalAccessException {
        boolean loginSucesso = false;
        while (!loginSucesso) {
            LimparTela.Limpar();
            System.out.println("====== FAÇA SEU LOGIN NO SISTEMA! ======");
            System.out.print("SEU LOGIN: ");
            String login = scan.nextLine().trim();
            System.out.print("SENHA: ");
            String password = scan.nextLine().trim();

            Usuario encontrado = null;
            for (Usuario u : UsuarioRepositorio.getUsuarios()) {
                if (u == null) {
                    continue;
                }

                try {
                    if (u.login(login, password)) {
                        encontrado = u;
                        break;
                    }
                } 
                catch (IllegalAccessException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }

            if (encontrado != null) {
                user = encontrado;
                loginSucesso = true;
                System.out.println("\nLogin realizado com sucesso!");
            }
            else {
                System.out.println("\nLogin ou senha inválidos.");
                loginSucesso = false;
            }
        }
    }

    public static void menuAluno(Aluno alunoUser) {
        boolean running = true;
        while (running) { 
            LimparTela.Limpar();
            System.out.println("====== MENU ALUNO ======");
            System.out.println("BEM VINDO!");
            System.out.println("================================");
            System.out.println("1 - CONSULTAR PROJETOS"); 
            System.out.println("2 - SOLICITAR PARTICIPACAO"); 
            System.out.println("3 - CANCELAR INSCRICAO"); 
            System.out.println("4 - MEUS PROJETOS");
            System.out.println("5 - CRIAR RELATORIO");
            System.out.println("6 - NOTIFICACOES"); 
            System.out.println("7 - SAIR");
            
            String opcao = scan.nextLine().trim();
            switch (opcao) {
                case "1" -> { // chamar o exibirProjetos() de ProjetoPesquisa
                    System.out.println("\n====== PROJETOS DISPONÍVEIS ======");
                    ProjetoPesquisa.exibirProjetos();
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                } 
                    
                case "2" -> { // chamar o Aluno.solicitarParticipacao()
                    System.out.println("\n====== SOLICITAR PARTICIPACAO ======");
                    if (ProjetoPesquisa.getProjetos().isEmpty()) {
                        System.out.println("Nenhum projeto cadastrado!");
                        System.out.println("\nPressione ENTER para continuar...");
                        scan.nextLine();
                        break;
                    }
                    ProjetoPesquisa.exibirProjetos();
                    System.out.print("Digite o NOME do projeto: ");
                    String nomeProjeto = scan.nextLine().trim();
                    
                    // Buscar o projeto pelo nome
                    ProjetoPesquisa projetoSelecionado = null;
                        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                            if (projeto.getNome().equalsIgnoreCase(nomeProjeto)) {
                                projetoSelecionado = projeto;
                                break;
                            }
                        }
                    
                    if (projetoSelecionado != null) {
                        try {
                            alunoUser.solicitarParticipacao(projetoSelecionado);
                            System.out.println("Solicitação enviada com sucesso!");
                        } 
                        catch (LimiteDeVagasException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Projeto não encontrado!");
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }

                case "3" -> { // chamar o Aluno.desistir()
                    System.out.println("\n====== CANCELAR INSCRICAO ======");
                    if (!alunoTemProjetos(alunoUser)) {
                        System.out.println("O aluno " + alunoUser.getNome() + " não está inscrito em nenhum projeto!");
                        System.out.println("\nPressione ENTER para continuar...");
                        scan.nextLine();
                        break;
                    }
                    alunoUser.meusProjetos();
                    System.out.print("Digite o NOME do projeto: ");
                    String nomeProjeto = scan.nextLine().trim();
                    
                    // Buscar o projeto pelo nome
                    ProjetoPesquisa projetoSelecionado = null;
                        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                            if (projeto.getNome().equalsIgnoreCase(nomeProjeto)) {
                                projetoSelecionado = projeto;
                                break;
                            }
                        }
                    
                    if (projetoSelecionado != null) {
                        if (alunoUser.desistir(projetoSelecionado)) {
                            System.out.println("Inscrição cancelada com sucesso!");
                        } else {
                            System.out.println("Erro ao cancelar inscrição!");
                        }
                    } else {
                        System.out.println("Projeto não encontrado!");
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }

                case "4" -> {
                    System.out.println("\n====== MEUS PROJETOS ======");
                    alunoUser.meusProjetos();
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                } 
                
                case "5" -> {
                    System.out.println("\n====== CRIAR RELATORIO ======");
                    if (!alunoTemProjetos(alunoUser)) {
                        System.out.println("O aluno " + alunoUser.getNome() + " não está inscrito em nenhum projeto!");
                        System.out.println("\nPressione ENTER para continuar...");
                        scan.nextLine();
                        break;
                    }
                    alunoUser.meusProjetos();
                    System.out.print("Digite o NOME do projeto: ");
                    String nomeProjeto = scan.nextLine().trim();
                    
                    // Buscar o projeto pelo nome
                    ProjetoPesquisa projetoSelecionado = null;
                        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                            if (projeto.getNome().equalsIgnoreCase(nomeProjeto)) {
                                projetoSelecionado = projeto;
                                break;
                            }
                        }

                    if (projetoSelecionado != null) {
                        System.out.print("Digite o conteúdo do relatório: ");
                        String conteudo = scan.nextLine().trim();

                        try {
                            Relatorio.criar(alunoUser, projetoSelecionado, conteudo);
                            System.out.println("Relatório criado com sucesso!");
                        } 
                        catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Projeto não encontrado!");
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                } 
                
                case "6" -> {
                    System.out.println("\n====== NOTIFICACOES ======");
                    alunoUser.exibirNotificacoesGlobais();
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                } 
                
                case "7" -> {
                    alunoUser.logout();
                    running = false;
                    System.out.println("Voltando ao menu principal...");
                } 
                
                default -> {
                    System.out.println("Opcao invalida! Tente novamente.");
                    scan.nextLine();
                }
            }
        }
    }

    public static void menuProfessor(Professor professorUser) throws LimiteDeVagasException {
        boolean running = true;
        while (running) { 
            LimparTela.Limpar();
            System.out.println("====== MENU PROFESSOR ======");
            System.out.println("Bem-vindo!");
            System.out.println("1 - CRIAR NOVO PROJETO");
            System.out.println("2 - EDITAR PROJETO");
            System.out.println("3 - ADICIONAR PARTICIPANTES");
            System.out.println("4 - ACOMPANHAR PROGRESSO");
            System.out.println("5 - VALIDAR RELATORIO");
            System.out.println("6 - ENVIAR NOTIFICACAO");
            System.out.println("7 - SAIR");

            String opcao = scan.nextLine().trim();
            switch (opcao) {
                case "1" -> { 
                    System.out.println("\n====== CRIAR NOVO PROJETO ======");

                    System.out.print("Nome do projeto: ");
                    String nome = scan.nextLine().trim();
                    
                    System.out.print("Área do projeto: ");
                    String area = scan.nextLine().trim();
                    
                    System.out.print("Descrição do projeto: ");
                    String descricao = scan.nextLine().trim();
                    
                    System.out.print("Data de início (YYYY-MM-DD): ");
                    String dataInicio = scan.nextLine().trim();
                    
                    try {
                        java.time.LocalDate data = java.time.LocalDate.parse(dataInicio);
                        professorUser.criarProjeto(nome, professorUser, area, descricao, data);
                        System.out.println("Projeto criado com sucesso!");
                    } 
                    catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Erro: Formato de data inválido!");
                    } 
                    catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "2" -> { 
                    System.out.println("\n====== EDITAR PROJETO ======");
                    if (ProjetoPesquisa.getProjetos().isEmpty()) {
                        System.out.println("Nenhum projeto cadastrado!");
                        System.out.println("\nPressione ENTER para continuar...");
                        scan.nextLine();
                        break;
                    }
                    ProjetoPesquisa.exibirProjetos();

                    System.out.print("Nome do projeto a editar: ");
                    String nomeProjeto = scan.nextLine().trim();
                    
                    System.out.print("Novo nome (ou deixe vazio para manter): ");
                    String novoNome = scan.nextLine().trim();
                    
                    System.out.print("Nova área (ou deixe vazio para manter): ");
                    String novaArea = scan.nextLine().trim();
                    
                    System.out.print("Nova descrição (ou deixe vazio para manter): ");
                    String novaDescricao = scan.nextLine().trim();
                    
                    System.out.print("Nova data de início (YYYY-MM-DD) (ou deixe vazio para manter): ");
                    String novaData = scan.nextLine().trim();
                    
                    try {
                        java.time.LocalDate data = novaData.isBlank() ? null : java.time.LocalDate.parse(novaData);
                        professorUser.editarProjeto(nomeProjeto, 
                            novoNome.isBlank() ? null : novoNome,
                            null,
                            novaArea.isBlank() ? null : novaArea,
                            novaDescricao.isBlank() ? null : novaDescricao,
                            data);
                        System.out.println("Projeto editado com sucesso!");
                    } 
                    catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Erro: Formato de data inválido!");
                    } 
                    catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "3" -> { 
                    System.out.println("\n====== ADICIONAR PARTICIPANTES ======");
                    if (ProjetoPesquisa.getProjetos().isEmpty()) {
                        System.out.println("Nenhum projeto cadastrado!");
                        System.out.println("\nPressione ENTER para continuar...");
                        scan.nextLine();
                        break;
                    }
                    ProjetoPesquisa.exibirProjetos();
                    System.out.print("Nome do projeto: ");
                    String nomeProjeto = scan.nextLine().trim();
                    
                    ProjetoPesquisa projetoSelecionado = null;
                        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                            if (projeto.getNome().equalsIgnoreCase(nomeProjeto)) {
                                projetoSelecionado = projeto;
                                break;
                            }
                        }
                    
                    if (projetoSelecionado != null) {
                        System.out.println("Solicitações de participação pendentes:");
                        ArrayList<Aluno> solicitantes = projetoSelecionado.getSolicitantes();
                        
                        if (solicitantes.isEmpty()) {
                            System.out.println("Nenhuma solicitação pendente!");
                        } else {
                            for (int i = 0; i < solicitantes.size(); i++) {
                                System.out.println((i + 1) + " - " + solicitantes.get(i).getNome());
                            }
                            System.out.print("Digite o número do aluno para adicionar (ou 0 para cancelar): ");
                            try {
                                int escolha = Integer.parseInt(scan.nextLine().trim());
                                if (escolha > 0 && escolha <= solicitantes.size()) {
                                    Aluno alunoSelecionado = solicitantes.get(escolha - 1); // começa do indice 0 por isso (escolha -1) 
                                    projetoSelecionado.adicionarParticipante(alunoSelecionado);
                                    System.out.println("Aluno adicionado com sucesso!");
                                } else if (escolha != 0) {
                                    System.out.println("Opção inválida!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida!");
                            }
                        }
                    } else {
                        System.out.println("Projeto não encontrado!");
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "4" -> { 
                    System.out.println("\n====== ACOMPANHAR PROGRESSO ======");
                    if (ProjetoPesquisa.getProjetos().isEmpty()) {
                        System.out.println("Nenhum projeto cadastrado!");
                    } else {
                        professorUser.acompanharProgresso();
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "5" -> { 
                    System.out.println("\n====== VALIDAR RELATORIO ======");
                    if (ProjetoPesquisa.getProjetos().isEmpty()) {
                        System.out.println("Nenhum projeto cadastrado!");
                        System.out.println("\nPressione ENTER para continuar...");
                        scan.nextLine();
                        break;
                    }
                    ProjetoPesquisa.exibirProjetos();
                    System.out.print("Nome do projeto: ");
                    String nomeProjeto = scan.nextLine().trim();
                    
                    try {
                        professorUser.validarRelatorios(nomeProjeto);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "6" -> { 
                    System.out.println("\n====== ENVIAR NOTIFICACAO ======");
                    System.out.print("Título da notificação: ");
                    String titulo = scan.nextLine().trim();
                    
                    System.out.print("Conteúdo da notificação: ");
                    String conteudo = scan.nextLine().trim();
                    
                    try {
                        professorUser.criarNotificacao(titulo, conteudo);
                        System.out.println("Notificação enviada com sucesso!");
                    } 
                    catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "7" -> { 
                    professorUser.logout();
                    running = false;
                    System.out.println("Voltando ao menu principal...");
                }
                
                default -> {
                    System.out.println("Opcao invalida! Tente novamente.");
                    scan.nextLine();
                }
            }
        }
    }

    public static void menuCoordenador(Coordenador coordenadorUser) {
        boolean running = true;
        while (running) {
            LimparTela.Limpar();
            System.out.println("====== MENU COORDENADOR ======");
            System.out.println("Bem-vindo!");
            System.out.println("1 - GERENCIAR PROJETOS");
            System.out.println("2 - GERENCIAR USUARIOS");
            System.out.println("3 - CONSULTAR RELATORIOS");
            System.out.println("4 - ESTATISTICAS GERAIS");
            System.out.println("5 - CRIAR NOTIFICACAO");
            System.out.println("6 - SAIR"); 

            String opcao = scan.nextLine().trim();
            switch (opcao) {
                case "1" -> {
                    boolean isRunning = true;
                    while (isRunning) { 
                        System.out.println("====== GERENCIAR PROJETOS ======");
                        System.out.println("1 - CONSULTAR PROJETOS");
                        System.out.println("2 - EXCLUIR PROJETO");
                        System.out.println("3 - VOLTAR");
                        
                        String op = scan.nextLine().trim();
                        switch (op) {
                            case "1" -> { // mostrar todos os projetos e seus atributos
                                System.out.println("\n====== TODOS OS PROJETOS ======");
                                ProjetoPesquisa.exibirProjetos();
                                
                                System.out.println("\nPressione ENTER para continuar...");
                                scan.nextLine();
                            }
                            
                            case "2" -> { // chamar o Coordenador.excluirProjeto()
                                System.out.println("\n====== EXCLUIR PROJETO ======");
                                if (ProjetoPesquisa.getProjetos().isEmpty()) {
                                    System.out.println("Nenhum projeto cadastrado!");
                                    System.out.println("\nPressione ENTER para continuar...");
                                    scan.nextLine();
                                    break;
                                }
                                ProjetoPesquisa.exibirProjetos();
                                System.out.print("Digite o NOME do projeto para excluir: ");
                                String nomeProjeto = scan.nextLine().trim();
                                
                                // Buscar o projeto pelo nome
                                ProjetoPesquisa projetoSelecionado = null;
                                for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                                    if (projeto.getNome().equalsIgnoreCase(nomeProjeto)) {
                                        projetoSelecionado = projeto;
                                        break;
                                    }
                                }
                                
                                if (projetoSelecionado != null) {
                                    if (coordenadorUser.excluirProjeto(projetoSelecionado)) {
                                        System.out.println("Projeto excluído com sucesso!");
                                    } else {
                                        System.out.println("Erro ao excluir projeto!");
                                    }
                                } else {
                                    System.out.println("Projeto não encontrado!");
                                }
                                System.out.println("\nPressione ENTER para continuar...");
                                scan.nextLine();
                            }
                            
                            case "3" -> {
                                isRunning = false;
                                System.out.println("Voltando ao menu principal...");
                            }
                            
                            default -> {
                                System.out.println("Opcao invalida! Tente novamente.");
                                scan.nextLine();    
                            }
                        }
                    }
                }
                
                case "2" -> {
                    boolean isRunning = true;
                    while (isRunning) { 
                        System.out.println("====== GERENCIAR USUARIOS ======");
                        System.out.println("1 - ATIVAR USUARIO");
                        System.out.println("2 - DESATIVAR USUARIO");
                        System.out.println("3 - CONSULTAR TODAS AS SOLICITACOES");
                        System.out.println("4 - VOLTAR");
                        
                        String op = scan.nextLine().trim();
                        switch (op) {
                            case "1" -> { // chamar o Coordenador.ativarUsuario()
                                System.out.println("\n====== ATIVAR USUARIO ======");
                                System.out.println("\n");

                                if (!exibirAlunosOrdenadosPorStatus()) {
                                    System.out.println("\nPressione ENTER para continuar...");
                                    scan.nextLine();
                                    break;
                                }

                                System.out.print("Digite o nome do aluno para ativar: ");
                                String nome = scan.nextLine().trim();
                                
                                // Buscar o aluno pelo nome
                                Aluno alunoSelecionado = null;
                                for (Usuario u : UsuarioRepositorio.getUsuarios()) {
                                    if (u instanceof Aluno aluno && aluno.getNome().equalsIgnoreCase(nome)) {
                                        alunoSelecionado = aluno;
                                        break;
                                    }
                                }
                                
                                if (alunoSelecionado != null) {
                                    try {
                                        coordenadorUser.ativarUsuario(alunoSelecionado);
                                        alunoSelecionado.setStatus(Aluno.AlunoStatus.ATIVO);
                                        System.out.println("Aluno " + alunoSelecionado.getNome() + " ativado com sucesso!");
                                    } 
                                    catch (IllegalArgumentException e) {
                                        System.out.println("Erro: " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Aluno não encontrado!");
                                }
                                System.out.println("\nPressione ENTER para continuar...");
                                scan.nextLine();
                            }
                            
                            case "2" -> { // chamar o Coordenador.desativarUsuario()
                                System.out.println("\n====== DESATIVAR USUARIO ======");
                                System.out.println("\n");

                                if (!exibirAlunosOrdenadosPorStatus()) {
                                    System.out.println("\nPressione ENTER para continuar...");
                                    scan.nextLine();
                                    break;
                                }

                                System.out.print("Digite o nome do aluno a desativar: ");
                                String nome = scan.nextLine().trim();
                                
                                // Buscar o aluno pelo nome
                                Aluno alunoSelecionado = null;
                                for (Usuario u : UsuarioRepositorio.getUsuarios()) {
                                    if (u instanceof Aluno aluno && aluno.getNome().equalsIgnoreCase(nome)) {
                                        alunoSelecionado = aluno;
                                        break;
                                    }
                                }
                                
                                if (alunoSelecionado != null) {
                                    try {
                                        coordenadorUser.desativarUsuario(alunoSelecionado);
                                        alunoSelecionado.setStatus(Aluno.AlunoStatus.DESATIVADO);
                                        System.out.println("Aluno " + alunoSelecionado.getNome() + " desativado com sucesso!");
                                    } 
                                    catch (IllegalArgumentException e) {
                                        System.out.println("Erro: " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Aluno não encontrado!");
                                }
                                System.out.println("\nPressione ENTER para continuar...");
                                scan.nextLine();
                            }
                            
                            case "3" -> { // percorrer o array de projetos e para cada projeto chamar ProjetoPesquisa.exibirSolicitantes()
                                System.out.println("\n====== TODAS AS SOLICITACOES ======");
                                ArrayList<ProjetoPesquisa> projetos = ProjetoPesquisa.getProjetos();
                                
                                if (projetos.isEmpty()) {
                                    System.out.println("Nenhum projeto cadastrado!");
                                } else {
                                    for (ProjetoPesquisa projeto : projetos) {
                                        projeto.exibirSolicitantes();
                                        System.out.println("----------------------------------------");
                                    }
                                }
                                System.out.println("\nPressione ENTER para continuar...");
                                scan.nextLine();
                            } 
                            
                            case "4" -> {
                                isRunning = false;
                                System.out.println("Voltando ao menu principal...");
                            } 
                            
                            default -> {
                                System.out.println("Opcao invalida! Tente novamente.");
                                scan.nextLine();
                            }
                        }
                    }
                }
                
                case "3" -> { 
                    System.out.println("\n====== CONSULTAR RELATORIOS ======");
                    coordenadorUser.consultarRelatorio();
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "4" -> {
                    System.out.println("\n====== ESTATISTICAS GERAIS ======");
                    coordenadorUser.exibirEstatisticas();
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }

                case "5" -> { 
                    System.out.println("\n====== CRIAR NOTIFICACAO ======");
                    System.out.print("Título da notificação: ");
                    String titulo = scan.nextLine().trim();
                    
                    System.out.print("Conteúdo da notificação: ");
                    String conteudo = scan.nextLine().trim();
                    
                    try {
                        coordenadorUser.criarNotificacao(titulo, conteudo);
                        System.out.println("Notificação criada com sucesso!");
                    } 
                    catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scan.nextLine();
                }
                
                case "6" -> { 
                    coordenadorUser.logout();
                    running = false;
                    System.out.println("Voltando ao menu principal...");
                }
                
                default -> {
                    System.out.println("Opção inválida! Tente novamente.");
                    scan.nextLine();
                }
            }
        }
    }

    public static boolean exibirAlunosOrdenadosPorStatus() {
        ArrayList<Aluno> alunos = new ArrayList<>();
        for (Usuario u : UsuarioRepositorio.getUsuarios()) {
            if (u instanceof Aluno aluno) {
                alunos.add(aluno);
            }
        }

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado!");
            return false;
        }

        alunos.sort((a, b) -> {
            int prioridadeA = a.getStatus() == Aluno.AlunoStatus.DESATIVADO ? 0 : 1;
            int prioridadeB = b.getStatus() == Aluno.AlunoStatus.DESATIVADO ? 0 : 1;
            
                if (prioridadeA != prioridadeB) {
                    return Integer.compare(prioridadeA, prioridadeB);
                }
            String nomeA = a.getNome() == null ? "" : a.getNome();
            String nomeB = b.getNome() == null ? "" : b.getNome();
            return nomeA.compareToIgnoreCase(nomeB);
        });

        System.out.println("ALUNOS (DESATIVADOS PRIMEIRO):");
        for (Aluno aluno : alunos) {
            System.out.println(aluno.getNome() + " - " + aluno.getStatus());
        }
        return true;
    }

    public static void fecharPrograma() {
        System.out.println("ENCERRANDO!");
        System.out.println("...");
    }
    
}
