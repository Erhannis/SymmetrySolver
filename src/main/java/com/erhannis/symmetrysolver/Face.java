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
public class Face {
  public Polyhedron parent;
  public Edge[] edges;
  /**
   * clockwise: vertex, edge, vertex, edge...
   */
  public Vertex[] vertices; // Not required for symmetry, only for rendering
  
  public Integer nextColor(Integer color) {
    if (color == null) {
      return null; //TODO Throw?
    } else if (color == 0) {
      throw new IllegalArgumentException("Color must be non-zero");
    } else if (color > 0) {
      return (((color-1)+1) % edges.length)+1;
    } else { // color < 0
      return (((((color*-1)-1)-1+edges.length) % edges.length)+1)*-1;
    }
  }
}
