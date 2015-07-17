/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cdp;

/**
 *
 * @author 20141BSI0566
 */
public enum Genero {
    MASCULINO,FEMININO,OUTROS;
    
    public static boolean exists(String name){
        Genero generos[] = values();
        for (Genero genero : generos) {
            if(genero.name().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }
}
