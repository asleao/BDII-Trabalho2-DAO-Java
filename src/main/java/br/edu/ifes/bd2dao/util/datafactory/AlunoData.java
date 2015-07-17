/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.util.datafactory;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Genero;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.fluttercode.datafactory.impl.DataFactory;

/**
 *
 * @author aleao
 */
public class AlunoData {
    
    public void criaAlunoRandom(DataFactory df, Integer qtdAlu){
        for (int i = 0; i < qtdAlu; i++) {
                salvarAluno(criarAluno(df));
        }
    }
    
    public Aluno criarAluno(DataFactory df){
        Aluno aluno = new Aluno();
        Calendar dataNascimento = Calendar.getInstance();
        List<Genero> listaGenero = getListGenero();
        
        aluno.setNome(df.getName());
        dataNascimento.setTime(df.getBirthDate());
        aluno.setDataNascimento(dataNascimento);
        aluno.setCpf(df.getNumberText(3)+"."+df.getNumberText(3)+"."+df.getNumberText(3)+"-"+df.getNumberText(2));
        aluno.setGenero(df.getItem(listaGenero));
        
        return aluno;
    }
    
    public List<Genero> getListGenero(){
        List<Genero> listaGenero = new ArrayList<>();
        
        listaGenero.add(Genero.MASCULINO);
        listaGenero.add(Genero.FEMININO);
        listaGenero.add(Genero.OUTROS);
        
        return listaGenero;
    }
    
    public void salvarAluno(Aluno aluno){
        try {
            aluno.inserir(aluno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
