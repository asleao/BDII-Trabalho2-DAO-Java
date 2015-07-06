/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cdp;

/**
 *
 * @author 20141BSI0566
 */
public class Disciplina {
    
    private String nome;
    private String periodo;
    private String professor;
    private int vagas;

    public Disciplina() {
    }

    public Disciplina(String nome, String periodo, String professor, int vagas) {
        this.nome = nome;
        this.periodo = periodo;
        this.professor = professor;
        this.vagas = vagas;
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
    
}
