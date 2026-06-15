package model.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import model.notificacao.NotificacaoMensagem;
import model.notificacao.NotificacaoRepositorio;
import model.projetos.ProjetoPesquisa;
import model.relatorio.Relatorio;

public class Professor extends Usuario {

    private static final ArrayList<Professor> professores = new ArrayList<>();
    private String area;

    public Professor() {
        super();
    }

    public Professor(String nome, String area, String login, String password) {
        super(nome, login, password);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void criarProjeto(String nome, Professor orientador, String area, String descricao ,LocalDate datainicio) {
        // ao criar um projeto, instancia-se um ProjetoPesquisa e adiciona ao array de projetos
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido!");
        }

        if (orientador == null) {
            throw new IllegalArgumentException("Nome inválido!");
        }

        if (area == null || area.isBlank()) {
            throw new IllegalArgumentException("Área do projeto inválida!");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição do projeto inválida!");
        }

        if (datainicio == null) {
            throw new IllegalArgumentException("Data de início inválida!");
        }

        ProjetoPesquisa projeto = new ProjetoPesquisa(nome, orientador, area, descricao, datainicio);
        ProjetoPesquisa.getProjetos().add(projeto);
    } 

    public ProjetoPesquisa editarProjeto(String nomeProjeto, String novoNome, Professor novoOrientador, String novaArea, String novaDescricao, LocalDate novaDataInicio) {
        // busca o projeto pelo nome e atualiza os campos fornecidos
        if (nomeProjeto == null || nomeProjeto.isBlank()) {
            throw new IllegalArgumentException("Nome do projeto inválido!");
        }
        
        //variavel auxiliar para armazenar o projeto encontrado
        ProjetoPesquisa projetoEncontrado = null;
            for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                if (nomeProjeto.equals(projeto.getNome())) {
                    projetoEncontrado = projeto;
                    break;
                }
            }

            if (projetoEncontrado == null) {
                throw new IllegalArgumentException("Projeto não encontrado: " + nomeProjeto);
            }

            if (novoNome != null && !novoNome.isBlank()) {
                projetoEncontrado.setNome(novoNome);
            }

            if (novoOrientador != null) {
                projetoEncontrado.setOrientador(novoOrientador);
            }

            if (novaArea != null && !novaArea.isBlank()) {
                projetoEncontrado.setArea(novaArea);
            }

            if (novaDescricao != null && !novaDescricao.isBlank()) {
                projetoEncontrado.setDescricao(novaDescricao);
            }
            
            if (novaDataInicio != null) {
                projetoEncontrado.setDataInicio(novaDataInicio);
            }
        return projetoEncontrado; 
    } 

    public void acompanharProgressos(String nomeProjeto) {
        // busca o projeto pelo nome e exibe o progresso dos participantes
        if (nomeProjeto == null || nomeProjeto.isBlank()) {
            throw new IllegalArgumentException("Nome do projeto inválido!");
        }

        ProjetoPesquisa projetoEncontrado = null;
        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
            if (nomeProjeto.equals(projeto.getNome())) {
                projetoEncontrado = projeto;
                break;
            }
        }

        if (projetoEncontrado == null) {
            throw new IllegalArgumentException("Projeto não encontrado: " + nomeProjeto);
        }

        System.out.println("Acompanhando progresso do projeto: " + projetoEncontrado.getNome());
        System.out.println("Orientador: " + projetoEncontrado.getOrientador().getNome());
        System.out.println("Área: " + projetoEncontrado.getArea());
        System.out.println("Data de início: " + projetoEncontrado.getDataInicio());
        projetoEncontrado.exibirParticipantes();
    } 

    public void validarRelatorios(String nomeProjeto) {
        if (nomeProjeto == null || nomeProjeto.isBlank()) {
            throw new IllegalArgumentException("Nome do projeto inválido!");
        }

        //variavel auxiliar para buscar o projeto no array de projetos
        ProjetoPesquisa projetoEncontrado = null;
        for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
            if (nomeProjeto.equals(projeto.getNome())) {
                projetoEncontrado = projeto;
                break;
            }
        }

        if (projetoEncontrado == null) {
            throw new IllegalArgumentException("Projeto não encontrado!");
        }

        //adiciona o relatorio ao array de relatorios do projeto
        ArrayList<Relatorio> relatoriosProjeto = projetoEncontrado.getRelatorios();

        if (relatoriosProjeto.isEmpty()) {
            System.out.println("Nenhum relatório foi enviado para o projeto!");
            return;
        }

        System.out.println("Validando relatórios do projeto: " + nomeProjeto);
        for (Relatorio relatorio : relatoriosProjeto) {
            boolean valido = relatorio.isValido();// verifica se o conteudo é nulo
            System.out.println("Autor: " + relatorio.getAutor().getNome());
            System.out.println("Conteúdo: " + (relatorio.getConteudo() == null ? "Nulo" : relatorio.getConteudo()));
            System.out.println("Status: " + (valido ? "Válido" : "Inválido"));
            if (!valido) {
                System.out.println("Relatório sem conteúdo válido!");
            }
        }
    }
    
    public void criarNotificacao(String titulo, String conteudo) {
        NotificacaoMensagem notificacao = new NotificacaoMensagem(titulo, conteudo, this.nome);
        NotificacaoRepositorio.adicionar(notificacao);
    }

    @Override 
    public boolean cadastro(String nome, String area, String login, String password) throws IllegalArgumentException {
        /*
        Cadastrando-se no sistema... o usuário irá criar seu login e senha 
        mostrar os sysout's no main
        criar a variavel auxiliar 'loginSucesso' para controle do while
        */ 

        if (nome == null || nome.isBlank() || nome.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("Nome inválido!");
        }

        if (area == null || area.isBlank() || !area.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("Area inválido!");
        }

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login inválido!");
        }

        if (password == null || password.length() < 6 || password.length() > 30) {
            throw new IllegalArgumentException("Senha deve ter entre 6 e 30 caracteres!");
        }

        for (Professor prof : professores) {
            if (login.equals(prof.getLogin())) {
                throw new IllegalArgumentException("Login já cadastrado!");
            }
        }
        
        Professor professor = new Professor(nome, area, login, password);
        professores.add(professor);
        return true;
    }

    @Override
    public boolean login(String login, String password) {
        //Verifica se o login e senha são iguais aos fornecidos no cadastro 
        if (login == null || password == null) {
            return false;
        }

        for (Professor prof : professores) {
            if (login.equals(prof.getLogin()) && password.equals(prof.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
