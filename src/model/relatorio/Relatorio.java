package model.relatorio;

import model.projetos.ProjetoPesquisa;
import model.usuarios.Aluno;

public class Relatorio {
    private Aluno autor;
    private ProjetoPesquisa projeto;
    private String conteudo;

    /* Construtor privado e método statico criar() para evitar usar o this no construtor
    para que a referencia nao vaze antes da construcao terminar */

    private Relatorio(Aluno autor, ProjetoPesquisa projeto, String conteudo) {
        if (autor == null) {
            throw new IllegalArgumentException("Autor inválido!");
        }
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto inválido!");
        }
        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("Conteúdo do relatório inválido!");
        }

        this.autor = autor;
        this.projeto = projeto;
        this.conteudo = conteudo;
    }

    // Será chamado pelo aluno
    public static Relatorio criar(Aluno autor, ProjetoPesquisa projeto, String conteudo) {
        Relatorio relatorio = new Relatorio(autor, projeto, conteudo);
        projeto.adicionarRelatorio(relatorio);
        return relatorio;
    }

    public Aluno getAutor() {
        return autor;
    }

    public ProjetoPesquisa getProjeto() {
        return projeto;
    }

    public String getConteudo() {
        return conteudo;
    }

    public boolean isValido() {
        return conteudo != null && !conteudo.isBlank();
    }
}
