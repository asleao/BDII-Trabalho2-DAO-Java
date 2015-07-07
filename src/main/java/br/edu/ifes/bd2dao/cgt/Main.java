/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgt;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Matricula;
import br.edu.ifes.bd2dao.exceptions.FieldNotFoundException;

/**
 *
 * @author aleao
 */
public class Main {
    
    public static void AlunoTest(){
        
//        Aluno aluno = new Aluno("Breno Sampaio", Calendar.getInstance(), Genero.MASCULINO, "144.872.877-07");
//        aluno.inserir(aluno);

//        Aluno aluno = new Aluno(new Long(1), "Breno Sampaio Grillo", Calendar.getInstance(), Genero.MASCULINO, "144.872.877-55");
        
//        try {
//            aluno.atualizar(aluno);
//        } catch (IdNotFoundException ex) {
//            ex.printStackTrace();
//        }
          
//        Aluno aluno = new Aluno(new Long(1), "Breno Sampaio Grillo", Calendar.getInstance(), Genero.MASCULINO, "144.872.877-55");
//        
//        try {
//            aluno.deletar(aluno);
//        } catch (IdNotFoundException ex) {
//            ex.printStackTrace();
//        }
        
//        Aluno aluno = new Aluno();
        
//        try {
//            List<Aluno> alunos = aluno.selecionarPor("genero", "MASCULINO");
//            System.out.println("Qtd Alunos: "+alunos.size());
//            for(Aluno a : alunos){
//                System.out.println(a));
//            }
//        } catch (FieldNotFoundException ex) {
//            ex.printStackTrace();
//        }
    }
    
    public static void DisciplinaTest(){
//        //Disciplina d = new Disciplina("Cálculo II", "2015/1", "Bruna Dalle Prane", 40);
//        //d.inserir(d);
//        
        Disciplina d = new Disciplina();
        d = d.selecionarTodos().get(0);
        

        try {
            d = d.selecionarPor("nome", "Cálculo II").get(0);
        } catch (FieldNotFoundException ex) {
            ex.printStackTrace();
        }
        
        d = d.selecionar(d.getId());
        
        System.out.println(d);
    }
    
    public static void MatriculaTest(){
//        Aluno a = new Aluno();
//        a = a.selecionarTodos().get(0);
//        
//        Disciplina d = new Disciplina();
//        d = d.selecionarTodos().get(0);
//        
//        Matricula m = new Matricula();
//        m.setAluno(a);
//        m.setDisciplina(d);
//        
//        m.inserir(m);
        
//        m =  m.selecionarTodos().get(0);
//        System.out.println(m);
        
        
    }
    
    public static void main(String args[]){
//        AlunoTest();
//        DisciplinaTest();
        MatriculaTest();
    }
}
