/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgt;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Genero;
import br.edu.ifes.bd2dao.cdp.Matricula;
import br.edu.ifes.bd2dao.exceptions.IdNotFoundException;
import br.edu.ifes.bd2dao.util.DateValidator;
import br.edu.ifes.bd2dao.util.datafactory.AlunoData;
import br.edu.ifes.bd2dao.util.datafactory.DisciplinaData;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

/**
 *
 * @author aleao
 */
public class Menu {

    DateValidator dateValidator = new DateValidator();
    HashMap<String, String> messages = new HashMap<>();
    DataFactory df = new DataFactory();
    AlunoData alunoData = new AlunoData();
    DisciplinaData disciplinaData = new DisciplinaData();
    Matricula matricula = new Matricula();
    
    public Menu(){
        this.messages.put("CPF", "CPF: (xxx.xxx.xxx-xx)");
        this.messages.put("NOME", "Nome: ");
        this.messages.put("ID", "Id: ");
        this.messages.put("DATANASCIMENTO", "Data de Nascimento (dd/MM/yyyy):");
        this.messages.put("GENERO", "Genero (MASCULINO, FEMININO, OUTROS):");
        this.messages.put("PERIODO", "Período: (YYYY/S) : ");
        this.messages.put("PROFESSOR", "Professor: ");
        this.messages.put("VAGAS", "Vagas: ");
        alunoData.criaAlunoRandom(df, 10);
        disciplinaData.criaDisciplinaRandom(df, 10);        
        matricula.matriculaAlunos();
    }

    public void load() {
        Scanner menuEntrada = new Scanner(System.in);
        int menu = -1;
        while (menu != 0) {
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Atualizar Aluno");
            System.out.println("3 - Deletar Aluno");
            System.out.println("4 - Buscar alunos matriculados");
            System.out.println("5 - Listar todos os alunos");
            System.out.println("6 - Cadastrar Disciplina");
            System.out.println("7 - Listar Disciplinas");
            System.out.println("8 - Buscar Disciplina");
            System.out.println("9 - Disciplinas de um aluno");
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
                case 6:
                    new Disciplina().inserir(lerDisciplina());
                    break;
                case 7:
                    listar(buscarDisciplinas());
                    break;
                case 8:
                    System.out.println(selecionarDisciplina());
                case 9:
                    listarDisciplinasDeUmAluno();
            }
        }
    }

    
    private void atualizarAluno(){
        Scanner sc = new Scanner(System.in);
        
        listarAlunos();
        
        System.out.println("\nInforme um ID:");
        int id = sc.nextInt();
        
        Aluno a = new Aluno().selecionar(new Long(id));
        
        Field fields[] = Aluno.class.getDeclaredFields();
        for (Field field : fields) {
            if(! field.getName().equals("id")){
                System.out.println("Deseja atualizar o campo ("+field.getName().toUpperCase()+") [n/s]?");
                String opt = sc.next().toUpperCase();
                if(opt.contains("S")){
                    setValue(field.getName(), a);
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
    private Object scanSpecific(String fieldName, Scanner sc, Class c) throws NoSuchFieldException{
        
        Field field = c.getDeclaredField(fieldName);
        boolean valid = false;
        Object value = null;
        
        while(!valid){
            System.out.println(this.messages.get(fieldName.toUpperCase()));
            String type = field.getType().getSimpleName();

            switch (type.toUpperCase()) {
                case "STRING":
                    value = sc.nextLine();
                    valid = true;
                    break;
                case "GENERO":
                    String genero = sc.nextLine();
                    valid = Genero.exists(genero);
                    if(valid)
                        value = Genero.valueOf(genero);
                    break;
                case "CALENDAR":
                    String date = sc.nextLine();
                    valid = this.dateValidator.validate(date);
                    if(valid)
                        value = convertStringToCalendar(date);
                    break;
                case "INT":
                    value = sc.nextInt();
                    valid = true;
                    break;
            }
        }
        
        return value;
    }
    
    
    private void setValue(String field, Object objeto){
        
        PropertyAccessor propAccessor = PropertyAccessorFactory.forBeanPropertyAccess(objeto);   
        Scanner sc = new Scanner(System.in);
        
        Object valorModificado = null;
        Object valorAtual;
        
        do {
            try {
                valorModificado = scanSpecific(field, sc, objeto.getClass());
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            }

            propAccessor.setPropertyValue(field, valorModificado);
            valorAtual = propAccessor.getPropertyValue(field);
            
            if(valorAtual == null)
                valorAtual = new Object();
            
        } while (! valorAtual.equals(valorModificado)) ;
    }
    
    public Aluno lerAluno() {
        Aluno aluno = new Aluno();
        setValue("genero",aluno);
        setValue("nome", aluno);
        setValue("dataNascimento",aluno);
        setValue("cpf",aluno);
        return aluno;
    }
    
    public Disciplina lerDisciplina() {
        Disciplina disciplina = new Disciplina();
        setValue("nome",disciplina);
        setValue("periodo",disciplina);
        setValue("professor",disciplina);
        setValue("vagas",disciplina);
        return disciplina;
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

    public void listarAlunos(){
        listar(buscarAlunos());
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
        listar(buscarMatriculados());
    }
    
    private void listar(List<?> objects){
        for (Object object : objects) {
            System.out.println(object);
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
        listar(buscarDisciplinas());
        System.out.println("Selecionar por:\n1 - ID\n2 - Nome e Período");
        Scanner opc = new Scanner(System.in);
        switch(opc.nextInt()){
            case 1:
                return buscarDisciplinaPorId();
            case 2:
                return buscarDisciplinaPorNomeEPeriodo();
            default:
                return null;
        }
    }
    
    private Disciplina buscarDisciplinaPorNomeEPeriodo(){
        Scanner scanId = new Scanner(System.in);
        System.out.println("Infome o nome desejado: ");
        String nome = scanId.nextLine();
        System.out.println("Infome o período desejado: ");
        String periodo = scanId.nextLine();
        return new Disciplina().selecionar(nome, periodo);
    }
    
    private Disciplina buscarDisciplinaPorId(){
        Scanner scanId = new Scanner(System.in);
        System.out.println("Infome o id do elemento desejado: ");
        int id = scanId.nextInt();
        return new Disciplina().selecionar(new Long(id));
    }
    
    private List<Disciplina> buscarDisciplinas(){
        return new Disciplina().selecionarTodos();
    }
    
    private Aluno buscarAlunoPorId(){
        Scanner scanId = new Scanner(System.in);
        listar(buscarAlunos());
        System.out.println("Infome o id do elemento desejado: ");
        int id = scanId.nextInt();
        return new Aluno().selecionar(new Long(id));
    }
    
    private void listarDisciplinasDeUmAluno(){
        Aluno a = buscarAlunoPorId();
        List<Disciplina> disciplinas = a.selecionarDisciplinas();
        listar(disciplinas);
    }
}
