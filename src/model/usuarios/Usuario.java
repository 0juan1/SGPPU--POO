package model.usuarios;

public abstract class Usuario {

    protected String nome;
    protected String login;
    protected String password;  

    public Usuario() { 

    }

    public Usuario(String nome, String login, String password) {
        this.nome = nome;
        this.login = login;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public abstract boolean cadastro(String nome, String curso, String login, String password) throws IllegalArgumentException;
    public abstract boolean login(String login, String password) throws IllegalAccessException;

    public void logout() {
        System.out.println("Saindo!...");
    }

}