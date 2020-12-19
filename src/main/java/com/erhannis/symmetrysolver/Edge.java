/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.symmetrysolver;

import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Half-edge, maybe.  There are two associated with an actual "edge", one for
 * each face attached to it.
 * @author erhannis
 */
public class Edge {
  public Face parent;
  public Edge dual;
  public Integer color;
  // CCW vertex
  public Vertex va; // Not required for symmetry, only for rendering
  // CW vertex
  public Vertex vb; // Not required for symmetry, only for rendering
  
  public void match(Edge e) {
    this.dual = e;
    e.dual = this;
  }

  public Edge prev() {
    int idx;
    for (idx = 0; idx < parent.edges.length; idx++) {
      if (parent.edges[idx] == this) {
        break;
      }
    }
    return parent.edges[(idx-1+parent.edges.length) % parent.edges.length];
  }
  
  public Edge next() {
    int idx;
    for (idx = 0; idx < parent.edges.length; idx++) {
      if (parent.edges[idx] == this) {
        break;
      }
    }
    return parent.edges[(idx+1) % parent.edges.length];
  }
  
  public int countVaFaces() {
      HashSet<Face> faces = new HashSet<>();
      Edge e = this;
      while (e != null) {
          if (!faces.add(e.parent)) {
              break;
          }
          e = e.prev().dual;
      }
      e = this.dual;
      while (e != null) {
          if (!faces.add(e.parent)) {
              break;
          }
          e = e.next().dual;
      }
      return faces.size();
  }
}
