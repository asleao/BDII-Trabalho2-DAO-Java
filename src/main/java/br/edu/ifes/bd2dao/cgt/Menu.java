/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgt;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Genero;
import br.edu.ifes.bd2dao.exceptions.IdNotFoundException;
import br.edu.ifes.bd2dao.util.DateValidator;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

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
                    atualizarAluno();
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

    
    private void atualizarAluno(){
        Scanner sc = new Scanner(System.in);
        
        listarAlunos();
        
        System.out.println("\n Informe um ID:");
        int id = sc.nextInt();
        
        Aluno a = new Aluno().selecionar(new Long(id));
        
        Field fields[] = Aluno.class.getDeclaredFields();
        for (Field field : fields) {
            if(! field.getName().equals("id")){
                System.out.println("Deseja atualizar o campo ("+field.getName().toUpperCase()+") [n/s]?");
                String opt = sc.next().toUpperCase();
                if(opt.contains("S")){
                    setValue(field, a);
                }
            }
        }
        
        try {
            a.atualizar(a);
        } catch (IdNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    //TODO: Melhore num FORK
    private Object scanSpecificType(String type, Scanner sc){
        
        switch (type.toUpperCase()) {
            case "STRING":
                return sc.next();
            case "GENERO":
                
                return Genero.valueOf(sc.nextLine());
            case "CALENDAR":
                return convertStringToCalendar(lerData(sc));
            default:
                return null;
        }
    }
    
    private void setValue(Field field, Aluno aluno){
        
        Object valorModificado = null;
        
        PropertyAccessor propAccessor = PropertyAccessorFactory.forBeanPropertyAccess(aluno);
        Object valorAtual = propAccessor.getPropertyValue(field.getName());
        
        while(! valorAtual.equals(valorModificado)){
            Scanner sc = new Scanner(System.in);
            System.out.println("Informe o valor de "+field.getName().toUpperCase()+": ");

            valorModificado = scanSpecificType(field.getType().getSimpleName(), sc);

            propAccessor.setPropertyValue(field.getName(), valorModificado);
            valorAtual = propAccessor.getPropertyValue(field.getName());
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

        aluno.setGenero(lerGenero(alunoScan));

        String cpf = lerCpf(alunoScan);
        while(! aluno.setCpf(cpf)){
            cpf = lerCpf(alunoScan);
        }

        return aluno;
    }
    
    private Genero lerGenero(Scanner sc){
        String possiveis = "(MASCULINO, FEMININO, OUTROS)";

        String wanted = "";
        
        while(! existsInGenero(wanted)){
            System.out.println("Genero "+possiveis+":");
            wanted = sc.nextLine().toUpperCase();
        }
        
        return Genero.valueOf(wanted);
    }
    
    private boolean existsInGenero(String wanted){
        Genero generos[] = Genero.values();
        for (Genero genero : generos) {
            if(genero.name().equals(wanted.toUpperCase()))
                return true;
        }
        return false;
    }

    private String lerCpf(Scanner alunoScan) {
        System.out.println("CPF (xxx.xxx.xxx-xx):");
        return alunoScan.nextLine();
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
        if(d == null){
            System.out.println("Disciplina não encontrada!");
            List<Aluno> alunos = new ArrayList<>();
            return alunos;
        }
        
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
