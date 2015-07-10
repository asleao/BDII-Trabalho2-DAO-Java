/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgd;

import br.edu.ifes.bd2dao.cdp.Aluno;
import br.edu.ifes.bd2dao.cdp.Disciplina;
import br.edu.ifes.bd2dao.cdp.Matricula;
import br.edu.ifes.bd2dao.exceptions.FieldNotFoundException;
import br.edu.ifes.bd2dao.exceptions.IdNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author 20141BSI0566
 */
public abstract class MatriculaDAO extends DAO{
    
    private Connection conexao = ConexaoPostgres.getInstance();
    
    @Override
    public void inserir(Object obj) {
        Matricula matricula = (Matricula) obj;
        
        String query = "INSERT INTO matriculas (idAluno,idDisciplina) "
                      + "VALUES (?,?)";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            st.setLong(1, matricula.getAluno().getId());
            st.setLong(2,matricula.getDisciplina().getId());     
            
            st.execute();    
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void atualizar(Object obj) throws IdNotFoundException{
        Matricula matricula = (Matricula) obj;
        
        if(matricula.getId() == null){
            throw new IdNotFoundException();
        }
        
        String query = "UPDATE matriculas SET (idAluno, idDisciplina) "
                      + "= (?,?) WHERE id = ?";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            
            st.setLong(1, matricula.getAluno().getId());
            st.setLong(2, matricula.getDisciplina().getId());
            st.setLong(3, matricula.getId());
            
            st.execute();    
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void deletar(Object obj) throws IdNotFoundException{
        Matricula matricula = (Matricula) obj;
        
        if(matricula.getId() == null){
            throw new IdNotFoundException();
        }
        
        String query = "DELETE FROM matriculas WHERE id = ?";
                        
        try {
            PreparedStatement st = conexao.prepareStatement(query);
            
            st.setLong(1, matricula.getId());
            
            st.execute();  
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        try {
            Matricula matricula = new Matricula();
            matricula.setId(id);
            this.deletar(matricula);
        } catch (IdNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Matricula> selecionarTodos(){
        List<Matricula> matriculas = new ArrayList<>();
        
        PreparedStatement stmt;
        try {
            String sql = "SELECT * FROM matriculas ;";
            stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            matriculas = fetchMatriculas(rs);

            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
        
        return matriculas;
    }

    @Override
    public List<Matricula> selecionarPor(HashMap<String, String> conditions) throws FieldNotFoundException{
        Class c = Matricula.class;
        
        //Verificar se os campos são válidos
        Set<String> campos = conditions.keySet();

        this.validateFields(c, campos);
        
        String where = buildWhereClause(campos);
        String sql = "SELECT * FROM matriculas WHERE "+where;
        
        List<Matricula> matriculas = new ArrayList<>();
        
        PreparedStatement stmt;
        try {
            stmt = conexao.prepareStatement(sql);

            this.bindParams(stmt, conditions);
            
            ResultSet rs = stmt.executeQuery();

            matriculas = fetchMatriculas(rs);

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
        return matriculas;
    }

    @Override
    public Matricula selecionar(Long id){
        try {
            HashMap<String, String> conditions = new HashMap<>();
            conditions.put("id", id.toString());
            
            List<Matricula> matriculas = this.selecionarPor(conditions);
            if(matriculas.size() > 0)
                return matriculas.get(0);
        } catch (FieldNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    
    
    private List<Matricula> fetchMatriculas(ResultSet rs){
        
        List<Matricula> fetchedMatriculas = new ArrayList<>();
        
        try {
            while (rs.next())
            {
                Matricula m = new Matricula();
                
                m.setId(rs.getLong("id"));
                Long idAluno = rs.getLong("aluno");
                Long idDisciplina = rs.getLong("disciplina");
                
                Aluno a = new Aluno();
                a = a.selecionar(idAluno);
                m.setAluno(a);
                
                Disciplina d = new Disciplina();
                d = d.selecionar(idDisciplina);
                m.setDisciplina(d);
                
                fetchedMatriculas.add(m);
            }
            
            rs.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return fetchedMatriculas;
    }
    
    
}
