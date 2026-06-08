package model.projetos;

import java.util.ArrayList;
import java.util.Iterator;
import model.usuarios.Aluno;

public class Participantes {
    
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
                    return true; //printar no main: System.out.println("A participação do aluno " + aluno.getNome() + " foi cancelada com sucesso!");
                }
            }
        return false; //printar no main: "Participante não encontrado!"
    } //atualizar o status no main

    public boolean concluirParticipacao(String nome) {
        Iterator<Aluno> it = participantes.iterator();
            while(it.hasNext()) {
                Aluno aluno = it.next();
                if (nome.equals(aluno.getNome())) {
                    it.remove();
                    return true; //printar no main: System.out.println("A participação do aluno " + aluno.getNome() + " foi concluída com sucesso!");
                }
            }
        return false; //
    } //atualizar o status no main

    public void exibirParticipantes() {
        System.out.println("PARTICIPANTES:");
       
        for (Aluno aluno : participantes) {
            System.out.println(aluno);
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
