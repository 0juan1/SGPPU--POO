package model.notificacao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NotificacaoRepositorio {

    private static final String NOTIFICACOES_FILE = "notificacoes.txt";
    private static final ArrayList<Notificacoes> notificacoes = new ArrayList<>();

    public static void adicionar(Notificacoes notificacao) {
        if (notificacao == null) {
            throw new IllegalArgumentException("Notificação inválida!");
        }
        notificacoes.add(notificacao);
        try {
            salvarNotificacao(notificacao);
        } catch (IOException e) {
            System.err.println("Falha ao salvar notificação: " + e.getMessage());
        }
    }

    public static ArrayList<Notificacoes> getNotificacoes() {
        return notificacoes;
    }

    private static void salvarNotificacao(Notificacoes notificacao) throws IOException {
        if (notificacao instanceof NotificacaoMensagem msg) {
            File arquivo = new File(NOTIFICACOES_FILE);
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(arquivo, true), StandardCharsets.UTF_8))) {
                writer.write(formatNotificacao(msg));
                writer.newLine();
            }
        }
    }

    private static String formatNotificacao(NotificacaoMensagem notificacao) {
        return "NOTIFICACAO|"
                + escapeCampo(notificacao.getTitulo()) + "|"
                + escapeCampo(notificacao.getAutor()) + "|"
                + escapeCampo(notificacao.getConteudo());
    }

    private static String escapeCampo(String campo) {
        return campo == null ? "" : campo.replace("|", "\\|");
    }
}
