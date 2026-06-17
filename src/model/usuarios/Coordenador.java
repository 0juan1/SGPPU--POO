package model.usuarios;

import java.util.ArrayList;
import model.notificacao.NotificacaoMensagem;
import model.notificacao.NotificacaoRepositorio;
import model.projetos.ProjetoPesquisa;
import model.relatorio.Relatorio;

public class Coordenador extends Usuario {

    private static final ArrayList<Coordenador> coordenadores = new ArrayList<>();
    private String area;

    public Coordenador() {
        super();
    }

    public Coordenador(String nome, String area, String login, String password) {
        super(nome, login, password);
        this.area = area;
    }

    public String getArea() {
        return area;
    }
    
    public void ativarUsuario(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno inválido!");            
        }
        aluno.setStatus(Aluno.AlunoStatus.ATIVO);
    }

    public void desativarUsuario(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno inválido!");
        }
        aluno.setStatus(Aluno.AlunoStatus.DESATIVADO);
    }

    public boolean excluirProjeto(ProjetoPesquisa projeto) {
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto inválido!");
        }

        // busca o projeto na lista global
        ProjetoPesquisa projetoEncontrado = null;
        for (ProjetoPesquisa p : ProjetoPesquisa.getProjetos()) {
            if (p == projeto || (p.getNome() != null && p.getNome().equals(projeto.getNome()))) {
                projetoEncontrado = p;
                break;
            }
        }

        if (projetoEncontrado == null) {
            return false;
        }

        // remove e atualiza status dos participantes
        for (Aluno participante : projetoEncontrado.getParticipantes()) {
            if (participante != null) {
                participante.setStatus(Aluno.AlunoStatus.PARTICIPACAO_CANCELADA);
            }
        }
        // exclue todos os participantes do projeto
        projetoEncontrado.getParticipantes().clear();

        // remove e atualiza status dos solicitantes
        for (Aluno solicitante : projetoEncontrado.getSolicitantes()) {
            if (solicitante != null) {
                solicitante.setStatus(Aluno.AlunoStatus.ATIVO);
            }
        }
        // exclue todas as solicitações do projeto que será excluído
        projetoEncontrado.getSolicitantes().clear();

        // remove o projeto do arraylist de projetos
        ProjetoPesquisa.getProjetos().remove(projetoEncontrado);

        // Cria notificação com informações do projeto excluído
        String titulo = "Projeto Excluído";
        String conteudo = "O projeto '" + projetoEncontrado.getNome() + "' foi excluído pelo coordenador.\n" +
                        "Área: " + projetoEncontrado.getArea() + "\n" +
                        "Orientador: " + projetoEncontrado.getOrientador().getNome() + "\n" +
                        "Participantes removidos: " + projetoEncontrado.getParticipantes().size();
        criarNotificacao(titulo, conteudo);
        
        return true;
    }

    public void consultarRelatorio() {
        // a variavél 'projetos' aqui, esta referenciando o array statico de projetos em ProjetoPesquisa
        ArrayList<ProjetoPesquisa> projetos = ProjetoPesquisa.getProjetos();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado!");
            return;
        }

        System.out.println("Relatórios por projeto:");
        for (ProjetoPesquisa projeto : projetos) {
            System.out.println("Projeto: " + projeto.getNome());
            ArrayList<Relatorio> relatorios = projeto.getRelatorios();

            if (relatorios.isEmpty()) {
                System.out.println("Nenhum relatório enviado!");
                continue;
            }
            
            for (Relatorio relatorio : relatorios) {
                System.out.println("Autor: " + relatorio.getAutor().getNome());
                System.out.println("Conteúdo: " + relatorio.getConteudo());
                System.out.println("Validade: " + (relatorio.isValido() ? "Válido" : "Inválido"));
            }
        }
    }

    public void exibirEstatisticas() {
        int totalProjetos = ProjetoPesquisa.getProjetos().size();
        int totalParticipantes = ProjetoPesquisa.getProjetos().stream().mapToInt(projeto -> projeto.getParticipantes().size()).sum();
        int totalProfessores = 0;
        int totalCoordenadores = 0;

        for (Usuario usuario : UsuarioRepositorio.getUsuarios()) {
            if (usuario instanceof Professor) {
                totalProfessores++;
            } else if (usuario instanceof Coordenador) {
                totalCoordenadores++;
            }
        }

        System.out.println("=== ESTATÍSTICAS GERAIS DO SISTEMA ===");
        System.out.println("Total de projetos: " + totalProjetos);
        System.out.println("Total de participantes: " + totalParticipantes);
        System.out.println("Total de professores: " + totalProfessores);
        System.out.println("Total de coordenadores: " + totalCoordenadores);
    }
    
    public void criarNotificacao(String titulo, String conteudo) {
        NotificacaoMensagem notificacao = new NotificacaoMensagem(titulo, conteudo, this.nome);
        NotificacaoRepositorio.adicionar(notificacao);
    }
    
    /*
    Cadastrando-se no sistema... o usuário irá criar seu login e senha 
        mostrar os sysout's no main
        criar a variavel auxiliar 'loginSucesso' para controle do while
    */ 
    @Override 
    public boolean cadastro(String nome, String area, String login, String password) throws IllegalArgumentException {
        if (nome == null || nome.isBlank() || !nome.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("Nome inválido!");
        }

        if (area == null || area.isBlank() || !area.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("area inválido!");
        }

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login inválido!");
        }

        if (password == null || password.length() < 6 || password.length() > 30) {
            throw new IllegalArgumentException("Senha deve ter entre 6 e 30 caracteres!");
        }

        for (Coordenador coord : coordenadores) {
            if (login.equals(coord.getLogin())) {
                throw new IllegalArgumentException("Login já cadastrado!");
            }
        }
        
        Coordenador coordenador = new Coordenador(nome, area, login, password);
        coordenadores.add(coordenador);
        UsuarioRepositorio.adicionarUsuario(coordenador);
        return true;
    }

    @Override
    public boolean login(String login, String password) {
        if (login == null || password == null) {
            return false;
        }

        for (Coordenador coord : coordenadores) {
            if (login.equals(coord.getLogin()) && password.equals(coord.getPassword())) {
                return true;
            }
        }
        return false;
    }


}