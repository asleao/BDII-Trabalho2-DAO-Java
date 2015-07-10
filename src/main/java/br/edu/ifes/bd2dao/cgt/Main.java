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
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
        
        Aluno aluno = new Aluno();
        Disciplina d = new Disciplina();
        d = d.selecionarTodos().get(0);
        List<Aluno> as = aluno.selecionarMatriculados("Cálculo II","2015/1");
        for (Aluno a : as) {
            System.out.println(a);
        }
    }
    
    public static void DisciplinaTest(){
//        //Disciplina d = new Disciplina("Cálculo II", "2015/1", "Bruna Dalle Prane", 40);
//        //d.inserir(d);
//        
        Disciplina d = new Disciplina();
        d = d.selecionarTodos().get(0);
        HashMap<String, String> conditions = new HashMap<>();

        try {
            
            d = d.selecionarPor(conditions).get(0);
        } catch (FieldNotFoundException ex) {
            ex.printStackTrace();
        }
        
        d = d.selecionar(d.getId());
        
        System.out.println(d);
    }
    
    public static void MatriculaTest(){
        Aluno a = new Aluno();
        a = a.selecionarTodos().get(0);
//        
        Disciplina d = new Disciplina();
        d = d.selecionarTodos().get(0);
//        
        Matricula m = new Matricula();
        m.setAluno(a);
        m.setDisciplina(d);
        
//        m.inserir(m);
        
        m =  m.selecionarTodos().get(0);
        System.out.println(m);
        
        
    }
        
    public static void menu(){
        
        Scanner menuEntrada = new Scanner(System.in);
        int menu=-1;
        while(menu!=0){
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Atualizar Aluno");
            System.out.println("3 - Deletar Aluno");                               
            System.out.println("4 - Buscar alunos matriculados");
            System.out.println("5 - Listar todos os alunos");     
            System.out.println("0 - Sair");
            menu = menuEntrada.nextInt();
            switch (menu){
               case 1:
                        Scanner alunoScan = new Scanner(System.in);
                        Aluno aluno = new Aluno();
                        System.out.println("Nome:");
                        aluno.setNome(alunoScan.nextLine());
                        System.out.println("Data de Nascimento:");
//                        aluno.setDataNascimento(alunoScan.nextLine());
                        System.out.println("Genero:");
//                        aluno.setGenero(alunoScan.nextLine());
                        System.out.println("CPF:");
//                        aluno.setCpf(alunoScan.next(null));
                        break;
               case 2: 
                       break;
               case 3: 
                       break;
               case 4: 
                       break;
               case 5: 
                       break;
               case 6: 
                       break;
               case 7: 
                       break;
            }
        
        }
    }
    public static void main(String args[]){

        Aluno aluno = new Aluno();
        Disciplina d = new Disciplina();
        d = d.selecionarTodos().get(0);
        List<Aluno> as = aluno.selecionarMatriculados("Cálculo II","2015/1");
        for (Aluno a : as) {
            System.out.println(a);
        }
    }
}
