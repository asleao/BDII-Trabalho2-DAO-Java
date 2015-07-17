/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.util.datafactory;

import br.edu.ifes.bd2dao.cdp.Disciplina;
import java.util.ArrayList;
import java.util.List;
import org.fluttercode.datafactory.impl.DataFactory;

/**
 *
 * @author aleao
 */
public class DisciplinaData {
    
    public void criaDisciplinaRandom(DataFactory df, Integer qtdDis){
        for (int i = 0; i < qtdDis; i++) {
            salvarDisciplina(criarDisciplina(df));
        }
    }
    public Disciplina criarDisciplina(DataFactory df){  
        Disciplina disciplina = new Disciplina();
        List<String> listaDisciplina = getListDisciplina();

        disciplina.setNome(df.getItem(listaDisciplina));
        disciplina.setPeriodo(String.valueOf(df.getNumberBetween(1990, 2015))+"/"+String.valueOf(df.getNumberBetween(1, 2)));
        disciplina.setProfessor(df.getName());
        disciplina.setVagas(df.getNumberBetween(0, 40));

        return disciplina;
    }
     
    public List<String> getListDisciplina(){
        List<String> listaDisciplina = new ArrayList();

        listaDisciplina.add("Calculo 1");
        listaDisciplina.add("Calculo 2");
        listaDisciplina.add("Banco de Dados 1");
        listaDisciplina.add("Banco de Dados 2");
        listaDisciplina.add("Comunicação Empresarial");
        listaDisciplina.add("Lógica");
        listaDisciplina.add("Programação 1");
        listaDisciplina.add("Programação 2");
        listaDisciplina.add("Programação Orientada a Objetos 1");
        listaDisciplina.add("Programação Orientada a Objetos 2");
        listaDisciplina.add("Estrutura de Dados");
        listaDisciplina.add("Informática e Sociedade");
        listaDisciplina.add("Técnicas de Programação Avançada");
        listaDisciplina.add("Engenharia de Software");
        listaDisciplina.add("Sistemas de Apoio a Decisão");
        listaDisciplina.add("Teoria Geral da Administração");
        listaDisciplina.add("Administração Financeira");
        listaDisciplina.add("Administração da Produção e Logística");
        listaDisciplina.add("Tópicos Especiais em Gestão de Negócios");
        listaDisciplina.add("Sociologia");
        listaDisciplina.add("Analise de Sistemas");
        listaDisciplina.add("Projeto de Sistemas");
        listaDisciplina.add("Sistemas Operacionais");


        return listaDisciplina;
    }
    
    public void salvarDisciplina(Disciplina disciplina){
        try {
            disciplina.inserir(disciplina);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
