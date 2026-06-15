package model.notificacao;

public class NotificacaoMensagem implements Notificacoes {

    private final String titulo;
    private final String conteudo;
    private final String autor;

    public NotificacaoMensagem(String titulo, String conteudo, String autor) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido!");
        }
        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("Conteúdo inválido!");
        }
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public void exibirNotificacao() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Mensagem: " + conteudo);
    }
}
