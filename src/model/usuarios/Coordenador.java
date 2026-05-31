package model.usuarios;

import java.util.ArrayList;

public class Coordenador extends Usuario {

    private final ArrayList<Coordenador> coordenadores = new ArrayList<>();
    private final String area;

    public Coordenador(String nome, String area, String login, String password) {
        super(nome, login, password);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void ativarUsuario() {} //implementar

    public void desativarUsuario() {} //implementar

    public void removerUsuario() {} //implementar

    public void consultarRelatorio() {} //implementar

    @Override
    public void cadastro(String nome, String curso, String login, String password) {
        
        for (Coordenador coord : coordenadores) {
            if (nome.isEmpty() || nome.matches("[a-zA-Z]+")) {
                // throw new IllegalArgumentsException("Digite um nome válido! Apenas letras são permitidas!");
                return;
            } 
            if (curso.isEmpty() || curso.matches("[a-zA-Z]+")) {
                // throw new IllegalArgumentsException("Digite um nome válido! Apenas letras são permitidas!");
                return;
            }
            if (login.equals(coord.getLogin())) {
                // throw new IllegalArgumentsException("O login acima já existe!");
                return;
            }
            if (password.length() < 6 || password.length() > 30) {
                // throw new IllegalArgumentsException("A senha deve ter entre 6 e 30 caracteres!");
                return;
            }
        }
        Coordenador coordenador = new Coordenador(nome, curso, login, password);
        coordenadores.add(coordenador);
        System.out.println("Cadastro realizado com sucesso!");

    }

    @Override
    public void login(String login, String password) {

        for (Coordenador coord : coordenadores) {
            if (login.equals(coord.getLogin()) || password.equals(coord.getPassword())) {
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
        
    }

}