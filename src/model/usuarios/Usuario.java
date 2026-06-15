package model.usuarios;

import java.util.ArrayList;
import model.notificacao.NotificacaoRepositorio;
import model.notificacao.Notificacoes;

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

    public void exibirNotificacoesGlobais() {
        ArrayList<Notificacoes> notificacoes = NotificacaoRepositorio.getNotificacoes();
        if (notificacoes.isEmpty()) {
            System.out.println("Sem notificações!");
            return;
        }

        System.out.println("Notificações gerais:");
        for (Notificacoes notificacao : notificacoes) {
            notificacao.exibirNotificacao();
        }
    }

    public static ArrayList<Notificacoes> getNotificacoesGlobais() {
        return NotificacaoRepositorio.getNotificacoes();
    }

    public abstract boolean cadastro(String nome, String curso, String login, String password) throws IllegalArgumentException;
    public abstract boolean login(String login, String password) throws IllegalAccessException;

    public void logout() {
        System.out.println("Saindo!...");
    }

}