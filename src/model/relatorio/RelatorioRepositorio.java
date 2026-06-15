package model.relatorio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class RelatorioRepositorio {

    private static final String RELATORIOS_FILE = "relatorios.txt";

    public static void adicionarRelatorio(Relatorio relatorio) {
        if (relatorio == null) {
            throw new IllegalArgumentException("Relatório inválido!");
        }

        try {
            salvarRelatorio(relatorio);
        } catch (IOException e) {
            System.err.println("Falha ao salvar relatório: " + e.getMessage());
        }
    }

    private static void salvarRelatorio(Relatorio relatorio) throws IOException {
        File arquivo = new File(RELATORIOS_FILE);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo, true), StandardCharsets.UTF_8))) {
            writer.write(formatRelatorio(relatorio));
            writer.newLine();
        }
    }

    private static String formatRelatorio(Relatorio relatorio) {
        return "RELATORIO|"
                + escapeCampo(relatorio.getAutor().getLogin()) + "|"
                + escapeCampo(relatorio.getProjeto().getNome()) + "|"
                + escapeCampo(relatorio.getConteudo());
    }

    private static String escapeCampo(String campo) {
        return campo == null ? "" : campo.replace("|", "\\|");
    }
}
