package model.projetos;

import exceptions.LimiteDeVagasException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import model.relatorio.Relatorio;
import model.usuarios.Aluno;
import model.usuarios.Aluno.AlunoStatus;
import model.usuarios.Professor;

public class ProjetoPesquisa {

    private static final ArrayList<ProjetoPesquisa> projetos = new ArrayList<>();
    private final ArrayList<Aluno> solicitantes = new ArrayList<>();
    private final Participacao participantes = new Participacao();
    private final ArrayList<Relatorio> relatorios = new ArrayList<>();
    
    private String nome;
    private Professor orientador;
    private String area;
    private String descricao;
    private LocalDate dataInicio;
    private final int vagas = 30;

    public ProjetoPesquisa(String nome, Professor orientador, String area, String descricao, LocalDate dataInicio) {
        this.nome = nome;
        this.orientador = orientador;
        this.area = area;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
    }
    
    public static ArrayList<ProjetoPesquisa> getProjetos() {
        return projetos;
    }
    
    public ArrayList<Aluno> getSolicitantes() {
        return solicitantes;
    }

    public ArrayList<Aluno> getParticipantes() {
        return participantes.getParticipantes();
    }
    
    public ArrayList<Relatorio> getRelatorios() {
        return relatorios;
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
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public void adicionarRelatorio(Relatorio relatorio) {
        if (relatorio == null) {
            throw new IllegalArgumentException("Relatório inválido!");
        }
        relatorios.add(relatorio);
    }
    
    // verifica se o projeto ainda tem vagas disponiveis
    public boolean possuiVagas() throws LimiteDeVagasException {
        if (participantes.getParticipantes().size() >= vagas) {
            throw new LimiteDeVagasException("Limite de vagas atingido!"); //mostrar só o e.getMessage() com try catch no main
        }
        return true;
    }

    // verifica se um aluno está entre os participantes deste projeto
    public boolean contemParticipante(Aluno aluno) {
        if (aluno == null) return false;
            for (Aluno a : participantes.getParticipantes()) {
                if (a.getLogin() != null && a.getLogin().equals(aluno.getLogin())) {
                    return true;
                }
            }
        return false;
    }

    // será chamado pelo professor para aceitar a solicitacao e adicionar o aluno ao projeto escolhido
    public boolean adicionarParticipante(Aluno aluno) throws LimiteDeVagasException {
    /*  
        implementar isso no main, já que é interaçao com o usuário
        
        if (aluno == null || aluno.isBlank()) { //verifica se a entrada do usuario é vazia ou apenas um espaço em branco
            System.err.println("Digite um nome vãlido!");
        }
    */

        // se o projeto tiver vagas disponiveis, o aluno é adicionado ao array de participantes do projeto
        if (possuiVagas()) {
            participantes.getParticipantes().add(aluno);
            solicitantes.remove(aluno);
            aluno.setStatus(AlunoStatus.INSCRITO);
            return true;
        } else {
            throw new LimiteDeVagasException("Limite de vagas atingido!"); //mostrar só o e.getMessage() no try catch
        }
    }

    // vai ser chamado em Aluno.solicitarParticipação() quando o aluno for se inscrever em um projeto 
    public boolean adicionarSolicitacao(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno inválido!");
        }

        if (aluno.getStatus() == AlunoStatus.INSCRITO || aluno.getStatus() == AlunoStatus.EM_ANALISE) {
            return false;
        }

        for (Aluno solicitante : solicitantes) {
            // verifica se o aluno já tem uma solicitação pendente no projeto
            if (solicitante.getLogin().equals(aluno.getLogin())) {
                return false;
            }
        }

        solicitantes.add(aluno);
        aluno.setStatus(AlunoStatus.EM_ANALISE);
        return true;
    }

    // será chamado pelo método desistir, que será chamado por um aluno
    public boolean removerParticipante(String nome) {
    /*  
        implementar isso no main, já que é interaçao com o usuário
        
        if (aluno == null || aluno.isBlank()) { //verifica se a entrada do usuario é vazia ou apenas um espaço em branco
            System.err.println("Digite um nome válido!"); 
        }
    */
        Iterator<Aluno> it = participantes.getParticipantes().iterator();
            while (it.hasNext()) {
                Aluno aluno = it.next();
                if (nome.equals(aluno.getNome())) {
                    it.remove();
                    aluno.setStatus(AlunoStatus.PARTICIPACAO_CANCELADA);
                    return true;
                }
            }
        return false;
    }
    
    // o coordenador terá acesso às solicitações por meio desse void
    public void exibirSolicitantes() {
        if (solicitantes.isEmpty()) {
            System.out.println("Nenhuma solicitação pendente neste projeto!");
            return;
        }
        
        System.out.println("Solicitações do projeto: " + nome);
        for (Aluno solicitante : solicitantes) {
            System.out.println("Nome: " + solicitante.getNome());
            System.out.println("Curso: " + solicitante.getCurso());
        }
    }

    public void exibirParticipantes() {
        if (participantes.getParticipantes().isEmpty()) {
            System.out.println("Nenhum participante inscrito neste projeto!");
            return;
        }
        participantes.exibirParticipantes();
    }
}