package model.projetos;

import exceptions.LimiteDeVagasException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import model.usuarios.Aluno;
import model.usuarios.Aluno.AlunoStatus;
import model.usuarios.Professor;

public class ProjetoPesquisa {

    private static final ArrayList<ProjetoPesquisa> projetos = new ArrayList<>();
    private final Participantes participante = new Participantes();
    
    private String nome;
    private Professor orientador;
    private String area;
    private LocalDate dataInicio;
    private final int vagas = 30;

    public ProjetoPesquisa(String nome, Professor orientador, String area, LocalDate dataInicio) {
        this.nome = nome;
        this.orientador = orientador;
        this.area = area;
        this.dataInicio = dataInicio;
    }
    
    public static ArrayList<ProjetoPesquisa> getProjetos() {
        return projetos;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getOrientador() {
        return orientador;
    }
    
    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public boolean possuiVagas() throws LimiteDeVagasException {
        if (participante.getParticipantes().size() >= vagas) {
            throw new LimiteDeVagasException("Limite de vagas atingido!"); //mostrar só o e.getMessage() com try catch no main
        }

        return true;
    }

    public boolean adicionarParticipante(Aluno aluno) throws LimiteDeVagasException {
    /*  
        implementar isso no main, já que é interaçao com o usuário
        
        if (aluno == null || aluno.isBlank()) { //verifica se a entrada do usuario é vazia ou apenas um espaço em branco
            System.err.println("Digite um nome vãlido!");
        }
    */

        if (possuiVagas()) {
            participante.getParticipantes().add(aluno);
            aluno.setStatus(AlunoStatus.INSCRITO);
            return true;
        } else {
            throw new LimiteDeVagasException("Limite de vagas atingido!"); //mostrar só o e.getMessage() no try catch
        }

    }

    public boolean removerParticipante(String nome) {
    /*  
        implementar isso no main, já que é interaçao com o usuário
        
        if (aluno == null || aluno.isBlank()) { //verifica se a entrada do usuario é vazia ou apenas um espaço em branco
            System.err.println("Digite um nome vãlido!"); 
        }
    */

        Iterator<Aluno> it = participante.getParticipantes().iterator();
        while (it.hasNext()) {
            Aluno aluno = it.next();
            if (nome.equals(aluno.getNome())) {
                aluno.setStatus(AlunoStatus.PARTICIPACAO_CANCELADA);
                it.remove();
                return true;
            }
        }
        return false;
    }
}
