/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgd;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Genero;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20141BSI0566
 */
public abstract class DisciplinaDAO implements DAO{
    
    private Connection conexao = ConexaoPostgres.getInstance();
    
    private void setStatementParameters(PreparedStatement st, Disciplina disciplina){
        try {
            st.setString(1, disciplina.getNome());
            st.setString(2,disciplina.getPeriodo());
            st.setString(3,disciplina.getProfessor());
            st.setInt(4,disciplina.getVagas()); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void inserir(Object obj) {
        Disciplina disciplina = (Disciplina) obj;
        
        String query = "INSERT INTO disciplinas (nome,periodo,nomeProfessor,vagas) "
                      + "VALUES (?,?,?,?)";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            this.setStatementParameters(st, disciplina);
            
            st.execute();    
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void atualizar(Object obj) throws IdNotFoundException{
        Disciplina disciplina = (Disciplina) obj;
        
        if(disciplina.getId() == null){
            throw new IdNotFoundException();
        }
        
        String query = "UPDATE disciplinas SET (nome,periodo,nomeProfessor,vagas) "
                      + "= (?,?,?,?) WHERE id = ?";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            this.setStatementParameters(st, disciplina);
            
            st.setLong(5, disciplina.getId());
            
            st.execute();    
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void deletar(Object obj) throws IdNotFoundException{
        Disciplina disciplina = (Disciplina) obj;
        
        if(disciplina.getId() == null){
            throw new IdNotFoundException();
        }
        
        String query = "DELETE FROM disciplinas WHERE id = ?";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            st.setLong(1, disciplina.getId());
            
            st.execute();  
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        try {
            Disciplina disciplina = new Disciplina();
            disciplina.setId(id);
            this.deletar(disciplina);
        } catch (IdNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Disciplina> selecionarTodos(){
        List<Disciplina> disciplinas = new ArrayList<>();
        
        PreparedStatement stmt;
        try {
            String sql = "SELECT * FROM disciplinas ;";
            stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            disciplinas = fetchDisciplinas(rs);

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
        
        return disciplinas;
    }

    @Override
    public List<Disciplina> selecionarPor(String campo, String valor) throws FieldNotFoundException{
        Class c = Disciplina.class;
        List<Disciplina> disciplinas = new ArrayList<>();
        
        if(validateFields(c, campo)){
            PreparedStatement stmt;
            try {
                String sql = "SELECT * FROM disciplinas WHERE "+campo+" = ?; ";
                stmt = conexao.prepareStatement(sql);
                
                stmt.setString(1, valor);
                
                ResultSet rs = stmt.executeQuery();
                
                disciplinas = fetchDisciplinas(rs);
                
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }else{
            throw new FieldNotFoundException();
        }
        
        return disciplinas;
    }

    @Override
    public Disciplina selecionar(Long id){
        try {
            List<Disciplina> disciplinas = this.selecionarPor("id", id.toString());
            if(disciplinas.size() > 0)
                return disciplinas.get(0);
        } catch (FieldNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    
    
    private List<Disciplina> fetchDisciplinas(ResultSet rs){
        
        List<Disciplina> fetchedDisciplinas = new ArrayList<>();
        
        try {
            while (rs.next())
            {
                Disciplina d = new Disciplina();
                
                d.setId(rs.getLong("id"));
                d.setNome(rs.getString("nome"));
                d.setPeriodo(rs.getString("periodo"));
                d.setProfessor(rs.getString("nomeProfessor"));
                d.setVagas(rs.getInt("vagas"));
                
                fetchedDisciplinas.add(d);
            }
            
            rs.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return fetchedDisciplinas;
    }
    
    private boolean validateFields(Class c, String campo){
        
        Field fields[] = c.getDeclaredFields();
        
        for(Field f : fields){
            if (f.getName().equalsIgnoreCase(campo))
                return true;
        }
        
        return false;
    }
    
}
