/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.cgd;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author 20141BSI0566
 */
public class ConexaoPostgres {

    private static Connection conexao;
    private static String url = "jdbc:postgresql://localhost:5432/";
    private static String user = "postgres";
    private static String pass = "123456";
    private static String db = "bd2";
    
    private ConexaoPostgres() {
        
    }
    
    private static void createConnection(){
        try {
            conexao = DriverManager.getConnection(url+db,user,pass);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized Connection getInstance(){
        
        if(conexao == null){
            createConnection();
        }
        
        return conexao;
    }
    
}
