/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.bd2dao.exceptions;

/**
 *
 * @author aleao
 */
public class IdNotFoundException extends Exception {
  public IdNotFoundException() { 
      super("O objeto passado não contém o atributo Id preenchido!");
  }
  public IdNotFoundException(String message) { super(message); }
  public IdNotFoundException(String message, Throwable cause) { super(message, cause); }
  public IdNotFoundException(Throwable cause) { super(cause); }
}