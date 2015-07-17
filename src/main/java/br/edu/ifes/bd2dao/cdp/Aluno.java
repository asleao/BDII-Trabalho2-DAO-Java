/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cdp;

import br.edu.ifes.bd2dao.cgd.AlunoDAO;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 20141BSI0566
 */
public class Aluno extends AlunoDAO{
    
    private Long id;
    private String nome;
    private Calendar dataNascimento;
    private Genero genero;
    private String cpf;

    public Aluno() {
    }

    public Aluno(String nome, Calendar dataNascimento, Genero genero, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.cpf = cpf;
    }
    

    public Aluno(Long id, String nome, Calendar dataNascimento, Genero genero, String cpf) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean setCpf(String cpf) {
        
        if (!validarCpf(cpf)) {
            System.out.println("Padrão de CPF inválido. Por favor, note que são necessários os pontos e o traços.");
            return false;
        }
        
        this.cpf = cpf;
        return true;
    }
        
    public static boolean validarCpf(String cpf) {
        Pattern p = Pattern.compile("^([0-9]{3}\\.){2}[0-9]{3}\\-[0-9]{2}$");
        Matcher m = p.matcher(cpf);
        return m.find();
    }
    
    private String beautifyDate(Calendar date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date.getTime());
    }
    
    @Override
    public String toString() {
        return "Aluno{" + "id=" + id + ", nome=" + nome + ", dataNascimento=" + beautifyDate(dataNascimento) + ", genero=" + genero + ", cpf=" + cpf + '}';
    }
}
