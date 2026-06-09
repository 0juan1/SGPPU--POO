package model.usuarios;

import java.util.ArrayList;

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

    public void desativarUsuario() {} //implementar, setar o status para desativado

    public void removerProjeto() {} //implementar, usar como referencia o nome do projeto para remover do array

    public void consultarRelatorio() {} //implementar

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