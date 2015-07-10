/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgd;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Genero;
import br.edu.ifes.bd2dao.cdp.Matricula;
import br.edu.ifes.bd2dao.exceptions.FieldNotFoundException;
import br.edu.ifes.bd2dao.exceptions.IdNotFoundException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20141BSI0566
 */
public abstract class AlunoDAO extends DAO{
    
    private Connection conexao = ConexaoPostgres.getInstance();
    
    @Override
    public void inserir(Object obj) {
        Aluno aluno = (Aluno) obj;
        
        String query = "INSERT INTO alunos (nome,dataNascimento,genero,cpf) "
                      + "VALUES (?,?,?,?)";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            st.setString(1, aluno.getNome());
            st.setDate(2,calendarToDate(aluno.getDataNascimento()));
            st.setString(3,aluno.getGenero().name());
            st.setString(4,aluno.getCpf());     
            
            st.execute();    
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private Date calendarToDate(Calendar cal){
        return new java.sql.Date(cal.getTimeInMillis());           
    }
    
    @Override
    public void atualizar(Object obj) throws IdNotFoundException{
        Aluno aluno = (Aluno) obj;
        
        if(aluno.getId() == null){
            throw new IdNotFoundException();
        }
        
        String query = "UPDATE alunos SET (nome, dataNascimento, genero, cpf) "
                      + "= (?,?,?,?) WHERE id = ?";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            st.setString(1, aluno.getNome());
            st.setDate(2,calendarToDate(aluno.getDataNascimento()));
            st.setString(3,aluno.getGenero().name());
            st.setString(4,aluno.getCpf());
            st.setLong(5, aluno.getId());
            
            st.execute();    
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void deletar(Object obj) throws IdNotFoundException{
        Aluno aluno = (Aluno) obj;
        
        if(aluno.getId() == null){
            throw new IdNotFoundException();
        }
        
        String query = "DELETE FROM alunos WHERE id = ?";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            st.setLong(1, aluno.getId());
            
            st.execute();  
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        try {
            Aluno aluno = new Aluno();
            aluno.setId(id);
            this.deletar(aluno);
        } catch (IdNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Aluno> selecionarTodos(){
        List<Aluno> alunos = new ArrayList<>();
        
        PreparedStatement stmt;
        try {
            String sql = "SELECT * FROM alunos ;";
            stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            alunos = fetchAlunos(rs);

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
        
        return alunos;
    }

    @Override
    public List<Aluno> selecionarPor(HashMap<String, String> conditions) throws FieldNotFoundException{
        
        Class c = Aluno.class;
        
        //Verificar se os campos são válidos
        Set<String> campos = conditions.keySet();

        this.validateFields(c, campos);
        
        String where = buildWhereClause(campos);
        String sql = "SELECT * FROM alunos WHERE "+where;
        
        List<Aluno> alunos = new ArrayList<>();
        
        PreparedStatement stmt;
        try {
            stmt = conexao.prepareStatement(sql);

            this.bindParams(stmt, conditions);

            ResultSet rs = stmt.executeQuery();

            alunos = fetchAlunos(rs);

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return alunos;
    }

    @Override
    public Aluno selecionar(Long id){
        
        try {
            HashMap<String, String> conditions = new HashMap<String, String>();
            conditions.put("id", id.toString());
            
            List<Aluno> alunos = this.selecionarPor(conditions);
            if(alunos.size() > 0)
                return alunos.get(0);
        } catch (FieldNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    
    
    private List<Aluno> fetchAlunos(ResultSet rs){
        
        List<Aluno> fetchedAlunos = new ArrayList<>();
        
        try {
            while (rs.next())
            {
                Aluno a = new Aluno();
                
                a.setId(rs.getLong("id"));
                a.setNome(rs.getString("nome"));
                a.setGenero(Genero.valueOf(rs.getString("genero")));
                a.setCpf(rs.getString("cpf"));
                Calendar c = Calendar.getInstance();
                c.setTime(rs.getDate("dataNascimento"));
                a.setDataNascimento(c);
                
                fetchedAlunos.add(a);
            }
            
            rs.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return fetchedAlunos;
    }
    
    private boolean validateFields(Class c, String campo){
        
        Field fields[] = c.getDeclaredFields();
        
        for(Field f : fields){
            if (f.getName().equalsIgnoreCase(campo))
                return true;
        }
        
        return false;
    }
    
    public List<Aluno> selecionarMatriculados(int idDisciplina){
        
        List<Aluno> alunos = new ArrayList<>();
        Matricula m = new Matricula();
        HashMap<String, String> mCond = new HashMap<>();
        mCond.put("disciplina", Integer.toString(idDisciplina));
        
        try {
            List<Matricula> matriculas = m.selecionarPor(mCond);
            for (Matricula mat : matriculas) {
                alunos.add(mat.getAluno());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return alunos;
    }
    
    public List<Aluno> selecionarMatriculados(String nomeDisciplina, String periodo){
        
        Disciplina d = new Disciplina();
        HashMap<String, String> dCond = new HashMap<>();
        dCond.put("nome", nomeDisciplina);
        dCond.put("periodo", periodo);
        List<Disciplina> disciplinas;
        
        try {
            disciplinas = d.selecionarPor(dCond);
            if(disciplinas.size() > 0)
                d = disciplinas.get(0);
        } catch (FieldNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return this.selecionarMatriculados(d.getId().intValue());
    }
}
