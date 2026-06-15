package model.projetos;

import java.util.ArrayList;
import java.util.Iterator;
import model.usuarios.Aluno;
import model.usuarios.Aluno.AlunoStatus;

public class Participacao {
    
    private final ArrayList<Aluno> participantes = new ArrayList<>();
    
    public ArrayList<Aluno> getParticipantes() {
        return participantes;
    }
    
    public boolean cancelarParticipacao(String nome) {
        Iterator<Aluno> it = participantes.iterator();
            while(it.hasNext()) { //enquanto existir um próximo elemento o loop continua
                Aluno aluno = it.next();
                if (nome.equals(aluno.getNome())) { //se nome for um participante
                    it.remove(); //o participante será removido do array participantes
                    aluno.setStatus(AlunoStatus.PARTICIPACAO_CANCELADA);
                    return true; //printar no main: System.out.println("A participação do aluno " + aluno.getNome() + " foi cancelada com sucesso!");
                }
            }
        return false; //printar no main: "Participante não encontrado!"
    }


    // o professor irá chamar esse método para confirmar que o aluno concluiu o projeto
    public boolean concluirParticipacao(String nome) {
        Iterator<Aluno> it = participantes.iterator();
            while(it.hasNext()) {
                Aluno aluno = it.next();
                if (nome.equals(aluno.getNome())) {
                    it.remove();
                    aluno.setStatus(AlunoStatus.PARTICIPACAO_CONCLUIDA);
                    return true; //printar no main: System.out.println("O aluno " + aluno.getNome() + " concluiu o prejeto sucesso!");
                }
            }
        return false; 
    }

    public void exibirParticipantes() {
        System.out.println("PARTICIPANTES:");
       
        for (Aluno aluno : participantes) {
            System.out.println(aluno.getNome() + " - " + aluno.getStatus());
        }
    }

    public void statusParticipante(String nome) {
        for (Aluno aluno : participantes) {
            if (nome.equals(aluno.getNome())) {
                System.out.println(aluno.getNome() + ": " + aluno.getStatus());
                return;
            }
        }
        System.out.println("Participante não encontrado!");
        
    }
}