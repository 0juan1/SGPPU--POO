package utilidades;

public class LimparTela {
    public static void Limpar(){
            System.out.print("\033[H\033[2J");  
            System.out.flush();
    }
}
