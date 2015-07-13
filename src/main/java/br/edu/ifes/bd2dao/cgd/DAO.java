/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgd;

import br.edu.ifes.bd2dao.exceptions.FieldNotFoundException;
import br.edu.ifes.bd2dao.exceptions.IdNotFoundException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20141BSI0566
 */
public abstract class DAO {
    public abstract void inserir(Object obj);
    public abstract void atualizar(Object obj) throws IdNotFoundException;
    public abstract void deletar(Object obj) throws IdNotFoundException;
    public abstract void deletar(Long id) ;
    public abstract List<?> selecionarTodos();
    public abstract List<?> selecionarPor(HashMap<String, String> conditions)  throws FieldNotFoundException;
    public abstract Object selecionar (Long id);
    
    public String buildWhereClause(Set<String> campos){
        String sql = "";
        
        int i = 1;
        for (String campo : campos) {
            
            sql += "CAST("+campo+" as varchar)"+" = CAST( ? as varchar) ";
            if(i < campos.size())
                sql += " AND ";
            i++;
        }
        
        if("".equals(sql))
            return "true";
        
        return sql;
    }
    
    public void bindParams(PreparedStatement stmt, HashMap<String, String> conditions){
        int i = 1;
        Set<String> campos = conditions.keySet();
        for(String c : campos){
            try {
                String v = conditions.get(c);
                stmt.setString(i, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }
    
    public boolean validateField(Class c, String campo) throws FieldNotFoundException{
        
        Field fields[] = c.getDeclaredFields();
        boolean finded = false;
        for(Field f : fields){
            if (f.getName().equalsIgnoreCase(campo)){
                finded = true;
                return finded;
            }
                
        }
        
        if(! finded){
            throw new FieldNotFoundException("O campo "+campo+" não existe na classe "+c.getSimpleName());
        }
        
        return false;
    }
    
    public boolean validateFields(Class c, Set<String> campos){

        boolean validFields = true;
        for (String campo : campos) {
            try {
                validFields = validFields && (validateField(c, campo));
            } catch (FieldNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        
        return validFields;
    }
}
