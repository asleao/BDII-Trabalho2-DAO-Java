/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cdp;

import br.edu.ifes.bd2dao.exceptions.FieldNotFoundException;
import br.edu.ifes.bd2dao.exceptions.IdNotFoundException;
import java.util.List;

/**
 *
 * @author 20141BSI0566
 */
public interface DAO {
    public void inserir(Object obj);
    public void atualizar(Object obj) throws IdNotFoundException;
    public void deletar(Object obj) throws IdNotFoundException;
    public void deletar(Long id) ;
    public List<?> selecionarTodos();
    public List<?> selecionarPor(String campo, String valor)  throws FieldNotFoundException;
    public Object selecionar (Long id);
}
