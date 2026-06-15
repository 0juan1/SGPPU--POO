package model.usuarios;

import exceptions.LimiteDeVagasException;
import java.util.ArrayList;
import model.projetos.ProjetoPesquisa;

public class Aluno extends Usuario {

    private static final ArrayList<Aluno> alunos = new ArrayList<>();
    private String curso;
    private AlunoStatus status;

    public Aluno() {
        super();
    }

    public Aluno(String nome, String curso, String login, String password) {
        super(nome, login, password);
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }
    
    public AlunoStatus getStatus() {
        return status;
    }

    public void setStatus(AlunoStatus status) {
        this.status = status;
    }

    
    public enum AlunoStatus {
        ATIVO,
        DESATIVADO,
        INSCRITO,
        EM_ANALISE,
        PARTICIPACAO_CONCLUIDA,
        PARTICIPACAO_CANCELADA
    }
    
    // o coordenador poderá acessar as solicitações
    public boolean solicitarParticipacao(ProjetoPesquisa projeto) throws LimiteDeVagasException{
        if (projeto == null) throw new IllegalArgumentException("Projeto inválido!"); 
        if (status == AlunoStatus.INSCRITO || status == AlunoStatus.EM_ANALISE) return false;
        
        if (projeto.possuiVagas()) {
            return projeto.adicionarSolicitacao(this);
        }
        return false;
    } 
    
    public boolean desistir(ProjetoPesquisa projeto) {
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto inválido!");
        }

        // remove o aluno do arry de participantes
        boolean removido = projeto.removerParticipante(this.getNome());
        if (removido) {
            this.setStatus(AlunoStatus.PARTICIPACAO_CANCELADA);
            //atualiza o status do aluno
        }
        return removido;
    }

    // mostra os projetos em que o aluno está inscrito
    public void meusProjetos() {
        ArrayList<ProjetoPesquisa> inscricoes = new ArrayList<>();
            for (ProjetoPesquisa projeto : ProjetoPesquisa.getProjetos()) {
                if (projeto.contemParticipante(this)) {
                    inscricoes.add(projeto);
                }
            }

        if (inscricoes.isEmpty()) {
            System.out.println("O aluno  " + this.getNome() + "Você não está inscrito em nenhum projeto!");
            return;
        }

        // imprimindo os projetos que estiver inscrito
        System.out.println("Minhas inscrições:");
            for (ProjetoPesquisa p : inscricoes) {
                System.out.println(" " + p.getNome() + " (Orientador: " + p.getOrientador().getNome() + ")");
            }
    } 
    
    /*
    Cadastrando-se no sistema... o usuário irá criar seu login e senha 
        mostrar os sysout's no main
        criar a variavel auxiliar 'loginSucesso' para controle do while
    */ 
    @Override 
    public boolean cadastro(String nome, String curso, String login, String password) throws IllegalArgumentException {
        if (nome == null || nome.isBlank() || nome.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("Nome inválido!");
        }

        if (curso == null || curso.isBlank() || !curso.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("Curso inválido!");
        }

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login inválido!");
        }

        if (password == null || password.length() < 6 || password.length() > 30) {
            throw new IllegalArgumentException("Senha deve ter entre 6 e 30 caracteres!");
        }

        for (Aluno alun : alunos) {
            if (login.equals(alun.getLogin())) {
                throw new IllegalArgumentException("Login já cadastrado!");
            }
        }
        
        Aluno aluno = new Aluno(nome, curso, login, password);
        alunos.add(aluno);
        return true;
    }

    @Override
    public boolean login(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        
        for (Aluno alun : alunos) {
            if (login.equals(alun.getLogin()) && password.equals(alun.getPassword())) {
                return true;
            }
        }
        return false;
    }

} 