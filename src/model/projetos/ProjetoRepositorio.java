package model.projetos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ProjetoRepositorio {

    private static final String PROJETOS_FILE = "projetos.txt";

    public static void adicionarProjeto(ProjetoPesquisa projeto) {
        if (projeto == null) {
            throw new IllegalArgumentException("Projeto inválido!");
        }

        ProjetoPesquisa.getProjetos().add(projeto);
        try {
            salvarProjeto(projeto);
        } catch (IOException e) {
            System.err.println("Falha ao salvar projeto: " + e.getMessage());
        }
    }

    private static void salvarProjeto(ProjetoPesquisa projeto) throws IOException {
        File arquivo = new File(PROJETOS_FILE);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo, true), StandardCharsets.UTF_8))) {
            writer.write(formatProjeto(projeto));
            writer.newLine();
        }
    }

    private static String formatProjeto(ProjetoPesquisa projeto) {
        return "PROJETO|"
                + escapeCampo(projeto.getNome()) + "|"
                + escapeCampo(projeto.getOrientador().getLogin()) + "|"
                + escapeCampo(projeto.getArea()) + "|"
                + escapeCampo(projeto.getDescricao()) + "|"
                + escapeCampo(projeto.getDataInicio().toString());
    }

    private static String escapeCampo(String campo) {
        return campo == null ? "" : campo.replace("|", "\\|");
    }
}
