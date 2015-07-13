/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cdp;

import br.edu.ifes.bd2dao.cgd.MatriculaDAO;

/**
 *
 * @author breno
 */
public class Matricula extends MatriculaDAO{
    
    private Long id;
    private Disciplina disciplina;
    private Aluno aluno;

    public Matricula(Long id, Disciplina disciplina, Aluno aluno) {
        this.id = id;
        this.disciplina = disciplina;
        this.aluno = aluno;
    }

    public Matricula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public String toString() {
        return "Matricula{" + "id=" + id + ", disciplina=" + disciplina + ", aluno=" + aluno + '}';
    }
}
