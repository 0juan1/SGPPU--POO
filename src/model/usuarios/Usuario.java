package model.usuarios;

public abstract class Usuario {

    protected String nome;
    protected String login;
    protected String password;  

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

    public abstract void cadastro(String nome, String curso, String login, String password);
    public abstract void login(String login, String password);

    public void logout() {
        System.out.println("Saindo!...");
    }

}