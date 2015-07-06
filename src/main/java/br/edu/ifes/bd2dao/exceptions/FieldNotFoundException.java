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
public class FieldNotFoundException extends Exception {
  public FieldNotFoundException() { 
      super("O objeto passado não contém o atributo com o nome referido!");
  }
  public FieldNotFoundException(String message) { super(message); }
  public FieldNotFoundException(String message, Throwable cause) { super(message, cause); }
  public FieldNotFoundException(Throwable cause) { super(cause); }
}