/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgt;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Genero;
import br.edu.ifes.bd2dao.util.DateValidator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aleao
 */
public class Menu {

    DateValidator dateValidator = new DateValidator();

    public void load() {
        Scanner menuEntrada = new Scanner(System.in);
        int menu = -1;
        while (menu != 0) {
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Atualizar Aluno");
            System.out.println("3 - Deletar Aluno");
            System.out.println("4 - Buscar alunos matriculados");
            System.out.println("5 - Listar todos os alunos");
            System.out.println("0 - Sair");
            menu = menuEntrada.nextInt();
            switch (menu) {
                case 1:
                    new Aluno().inserir(lerAluno());
                    break;
                case 2:
                    
                    break;
                case 3:
                    deletarAluno();
                    break;
                case 4:
                    listarMatriculados();
                    break;
                case 5:
                    listarAlunos();
                    break;
            }
        }
    }

    public Aluno lerAluno() {
        Scanner alunoScan = new Scanner(System.in);
        Aluno aluno = new Aluno();
        System.out.println("Nome:");
        aluno.setNome(alunoScan.nextLine());

        String data = lerData(alunoScan);

        if (!validarData(data)) {
            System.out.println("Padrão de data inválido. Por favor, insira no formato (dd/MM/yyyy).");
            data = lerData(alunoScan);
        }

        aluno.setDataNascimento(convertStringToCalendar(data));

        System.out.println("Genero (MASCULINO, FEMININO, OUTROS):");
        aluno.setGenero(Genero.valueOf(alunoScan.nextLine().toUpperCase()));

        String cpf = lerCpf(alunoScan);

        if (!validarCpf(cpf)) {
            System.out.println("Padrão de CPF inválido. Por favor, note que são necessários os pontos e o traços.");
            cpf = lerCpf(alunoScan);
        }

        aluno.setCpf(cpf);

        return aluno;
    }

    private String lerCpf(Scanner alunoScan) {
        System.out.println("CPF (xxx.xxx.xxx-xx):");
        return alunoScan.nextLine();
    }

    private boolean validarCpf(String cpf) {
        Pattern p = Pattern.compile("/^d{3}.d{3}.d{3}-d{2}$/");
        Matcher m = p.matcher(cpf);
        return m.find();
    }

    private boolean validarData(String data) {
        return this.dateValidator.validate(data);
    }

    private Calendar convertStringToCalendar(String dataRecebida) {

        Calendar dataNascimento = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNas = df.parse(dataRecebida);
            dataNascimento = Calendar.getInstance();
            dataNascimento.setTime(dataNas);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return dataNascimento;
    }

    public String lerData(Scanner alunoScan) {
        System.out.println("Data de Nascimento (dd/MM/yyyy):");
        String dataRecebida = alunoScan.nextLine();

        return dataRecebida;
    }
    
    public void listarAlunos(){
        List<Aluno> alunos = buscarAlunos();
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }
    
    private List<Aluno> buscarAlunos(){
        return new Aluno().selecionarTodos();
    }
    
    private void deletarAluno(){
        System.out.println("Selecione um dos alunos abaixo: \n");
        listarAlunos();
        System.out.println("Informe o id do aluno desejado: \n");
        Scanner alunoScan = new Scanner(System.in);
        int id = alunoScan.nextInt();
        new Aluno().deletar(new Long(id));
    }
    
    private void listarMatriculados(){
        List<Aluno> alunos = buscarMatriculados();
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }
    
    private List<Aluno> buscarMatriculados(){
        Disciplina d = selecionarDisciplina();
        return new Aluno().selecionarMatriculados(d.getId().intValue());
    }
    
    private Disciplina selecionarDisciplina(){
        System.out.println("Selecione uma das disciplinas abaixo: \n");
        listarDisciplinas();
        System.out.println("Selecionar por:\n1 - ID\n2 - Nome e Período");
        Scanner opc = new Scanner(System.in);
        switch(opc.nextInt()){
            case 1:
                return buscarPorId();
            case 2:
                return buscarPorNomeEPeriodo();
            default:
                return null;
        }
    }
    
    private Disciplina buscarPorNomeEPeriodo(){
        Scanner scanId = new Scanner(System.in);
        System.out.println("Infome o nome desejado: ");
        String nome = scanId.nextLine();
        System.out.println("Infome o período desejado: ");
        String periodo = scanId.nextLine();
        return new Disciplina().selecionar(nome, periodo);
    }
    
    private Disciplina buscarPorId(){
        Scanner scanId = new Scanner(System.in);
        System.out.println("Infome o id desejado: ");
        int id = scanId.nextInt();
        return new Disciplina().selecionar(new Long(id));
    }
    
    private List<Disciplina> buscarDisciplinas(){
        return new Disciplina().selecionarTodos();
    }
    
    private void listarDisciplinas(){
        List<Disciplina> disciplinas = buscarDisciplinas();
        for (Disciplina disciplina : disciplinas) {
            System.out.println(disciplina);
        }
    }
}
