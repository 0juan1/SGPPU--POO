package model.usuarios;
import java.util.ArrayList;

public class Aluno extends Usuario {

    private final ArrayList<Aluno> alunos = new ArrayList<>();
    private final String curso;

    public Aluno(String nome, String curso, String login, String password) {
        super(nome, login, password);
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }
    
    public void solicitarInscricaoProjeto() {} //implementar

    public void cancelarInscricao() {} //implementar

    public void meusProjetos() {} //implementar

    public void notificacoes() {} //implementar
       
    @Override /* Cadastrando-se no sistema, o usuário irá criar seu login e senha */ 
    public void cadastro(String nome, String curso, String login, String password) { // throws IllegalArgumentsException

        for (Aluno alun : alunos) {
            if (nome.isEmpty() || nome.matches("[a-zA-Z]+")) {
                // throw new IllegalArgumentsException("Digite um nome válido! Apenas letras são permitidas!");
                return;
            } 
            if (curso.isEmpty() || curso.matches("[a-zA-Z]+")) {
                // throw new IllegalArgumentsException("Digite um nome válido! Apenas letras são permitidas!");
                return;
            }
            if (login.equals(alun.getLogin())) {
                // throw new IllegalArgumentsException("O login acima já existe!");
                return;
            }
            if (password.length() < 6 || password.length() > 30) {
                // throw new IllegalArgumentsException("A senha deve ter entre 6 e 30 caracteres!");
                return;
            }
        }
        Aluno aluno = new Aluno(nome, curso, login, password);
        alunos.add(aluno);
        System.out.println("Cadastro realizado com sucesso!");
    
    }

    @Override
    public void login(String login, String password) {

        for (Aluno alun : alunos) {
            if (login.equals(alun.getLogin()) || password.equals(alun.getPassword())) {
                System.out.println("Login realizado com sucesso!");
                return;
            }
            else {
                System.out.println("Login ou senha invalido!");
                return;
            }
        }
        System.out.println("Bem vindo!");

    }

    @Override
    public void logout() {
        
    }

} 