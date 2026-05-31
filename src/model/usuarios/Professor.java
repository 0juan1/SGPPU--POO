package model.usuarios;

import java.util.ArrayList;

public class Professor extends Usuario {

    private final ArrayList<Professor> professores = new ArrayList<>();
    private final String area;

    public Professor(String nome, String area, String login, String password) {
        super(nome, login, password);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void criarProjeto() {} //implementar

    public void editarProjeto() {} //implementar

    public void acompanharProgressos() {} //implementar

    public void validarRelatorios() {} //implementar
    
    @Override
    public void cadastro(String nome, String curso, String login, String password) {
        
        for (Professor prof : professores) {
            if (nome.isEmpty() || nome.matches("[a-zA-Z]+")) {
                // throw new IllegalArgumentsException("Digite um nome válido! Apenas letras são permitidas!");
                return;
            } 
            if (area.isEmpty() || area.matches("[a-zA-Z]+")) {
                // throw new IllegalArgumentsException("Digite um nome válido! Apenas letras são permitidas!");
                return;
            }
            if (login.equals(prof.getLogin())) {
                // throw new IllegalArgumentsException("O login acima já existe!");
                return;
            }
            if (password.length() < 6 || password.length() > 30) {
                // throw new IllegalArgumentsException("A senha deve ter entre 6 e 30 caracteres!");
                return;
            }
        }
        Professor professor = new Professor(nome, area, login, password);
        professores.add(professor);
        System.out.println("Cadastro realizado com sucesso!");

    }

    @Override
    public void login(String login, String password) { // lançar as exceçoes
        
        for (Professor prof : professores) {
            if (login.equals(prof.getLogin()) || password.equals(prof.getPassword())) {
                System.out.println("Login realizado com sucesso!");
                return;
            }
            else {
                System.out.println("Login ou senha inválido!");
                return;
            }
        }
        System.out.println("Bem vindo!");

    }

    @Override
    public void logout() {
        System.out.println("Saindo!...");
    }

}
