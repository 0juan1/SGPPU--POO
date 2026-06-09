package model.usuarios;
import java.util.ArrayList;

public class Aluno extends Usuario {

    private static final ArrayList<Aluno> alunos = new ArrayList<>(); //verificar a logica desse array e o array das classes Professor e Coordenador
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


    public void solicitarParticipacao() {} //implementar, o coordenador deve acessar as solicitações

    public void desistir() {} //implementar, chamar cancelarPariticipacao

    public void meusProjetos() {} //implementar, 

    public void gerarRelatorio() {} //implementar, gravar em um txt

    
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