package model.notificacao;

import java.util.ArrayList;

public class NotificacaoRepositorio {

    private static final ArrayList<Notificacoes> notificacoes = new ArrayList<>();

    public static void adicionar(Notificacoes notificacao) {
        if (notificacao == null) {
            throw new IllegalArgumentException("Notificação inválida!");
        }
        notificacoes.add(notificacao);
    }

    public static ArrayList<Notificacoes> getNotificacoes() {
        return notificacoes;
    }

}
