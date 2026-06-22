package utilidades;

import java.io.IOException;

public class LimparTela {
    public static void Limpar() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
                // Fallback se não for possível limpar com cls
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
