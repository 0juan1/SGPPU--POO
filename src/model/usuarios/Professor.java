package model.usuarios;

import java.util.ArrayList;

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

    public void criarProjeto() {} //implementar, quando um projeto for criado, um ProjetoPesquisa é instanciado e adicionado ao array de projetos

    public void editarProjeto() {} //implementar

    public void acompanharProgressos() {} //implementar

    public void validarRelatorios() {} //implementar
    
    /*
    Cadastrando-se no sistema... o usuário irá criar seu login e senha 
        mostrar os sysout's no main
        criar a variavel auxiliar 'loginSucesso' para controle do while
    */ 
    @Override 
    public boolean cadastro(String nome, String area, String login, String password) throws IllegalArgumentException {
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
