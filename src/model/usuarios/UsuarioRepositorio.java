package model.usuarios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioRepositorio {

    private static final String USUARIOS_FILE = "usuarios.txt";
    private static final ArrayList<Usuario> usuarios = new ArrayList<>();

    static {
        carregarUsuarios();
    }

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void adicionarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário inválido!");
        }

        usuarios.add(usuario);
        try {
            salvarUsuario(usuario);
        } catch (IOException e) {
            System.err.println("Falha ao salvar usuário: " + e.getMessage());
        }
    }

    private static void salvarUsuario(Usuario usuario) throws IOException {
        File arquivo = new File(USUARIOS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(formatUsuario(usuario));
            writer.newLine();
        }
    }

    private static String formatUsuario(Usuario usuario) {
        StringBuilder builder = new StringBuilder();
        switch (usuario) {
            case Aluno aluno -> builder.append("ALUNO|")
                    .append(escapeCampo(aluno.getNome())).append('|')
                    .append(escapeCampo(aluno.getCurso())).append('|')
                    .append(escapeCampo(aluno.getLogin())).append('|')
                    .append(escapeCampo(aluno.getPassword()));
            case Professor professor -> builder.append("PROFESSOR|")
                    .append(escapeCampo(professor.getNome())).append('|')
                    .append(escapeCampo(professor.getArea())).append('|')
                    .append(escapeCampo(professor.getLogin())).append('|')
                    .append(escapeCampo(professor.getPassword()));
            case Coordenador coordenador -> builder.append("COORDENADOR|")
                    .append(escapeCampo(coordenador.getNome())).append('|')
                    .append(escapeCampo(coordenador.getArea())).append('|')
                    .append(escapeCampo(coordenador.getLogin())).append('|')
                    .append(escapeCampo(coordenador.getPassword()));
            default -> throw new IllegalArgumentException("Tipo de usuário não suportado para gravação.");
        }
        return builder.toString();
    }

    private static String escapeCampo(String campo) {
        return campo == null ? "" : campo.replace("|", "\\|");
    }

    private static void carregarUsuarios() {
        File arquivo = new File(USUARIOS_FILE);
        if (!arquivo.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(arquivo, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                Usuario usuario = parseUsuario(linha);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            System.err.println("Falha ao carregar usuários: " + e.getMessage());
        }
    }

    private static Usuario parseUsuario(String linha) {
        if (linha == null || linha.isBlank()) {
            return null;
        }

        String[] partes = linha.split("\\|", -1);
        if (partes.length != 5) {
            return null;
        }

        String tipo = partes[0];
        String nome = unescapeCampo(partes[1]);
        String cursoOuArea = unescapeCampo(partes[2]);
        String login = unescapeCampo(partes[3]);
        String password = unescapeCampo(partes[4]);

        return switch (tipo) {
            case "ALUNO" -> new Aluno(nome, cursoOuArea, login, password);
            case "PROFESSOR" -> new Professor(nome, cursoOuArea, login, password);
            case "COORDENADOR" -> new Coordenador(nome, cursoOuArea, login, password);
            default -> null;
        };
    }

    private static String unescapeCampo(String campo) {
        return campo == null ? null : campo.replace("\\|", "|");
    }
}
