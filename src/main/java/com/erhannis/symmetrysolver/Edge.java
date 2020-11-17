/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.symmetrysolver;

/**
 *
 * @author erhannis
 */
public class Edge {
  public Face parent;
  public Edge dual;
  public Integer color;
  
  public void match(Edge e) {
    this.dual = e;
    e.dual = this;
  }
}
