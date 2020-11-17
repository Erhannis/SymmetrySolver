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
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    for (int a = -5; a <= 5; a++) { if (a==0){continue;}
    for (int b = -5; b <= 5; b++) { if (b==0){continue;}
    for (int c = -5; c <= 5; c++) { if (c==0){continue;}
    for (int d = -5; d <= 5; d++) { if (d==0){continue;}
    for (int e = -5; e <= 5; e++) { if (e==0){continue;}
    Polyhedron p = Polyhedron.dodecahedron();
    
    Mapping m;
    try {
      m = Mapping.fromArray(a,b,c,d,e);
    } catch (IllegalArgumentException err) {
      //System.out.println("illegal mapping ("+a+","+b+","+c+","+d+","+e+")");
      continue;
    }
    
    for (int i = 0; i < p.faces[0].edges.length; i++) {
      p.faces[0].edges[i].color = i+1;
    }
    
    if (propagate(p, m)) {
      System.out.println("success ("+a+","+b+","+c+","+d+","+e+")");
    } else {
      //System.out.println("failure ("+a+","+b+","+c+","+d+","+e+")");
    }
    /*
    for (int j = 0; j < p.faces.length; j++) {
      Face f = p.faces[j];
      System.out.println("face f");
      for (int i = 0; i < f.edges.length; i++) {
        System.out.println(f.edges[i].color + " " + f.edges[i].dual.color);
      }
    }
    */
    
    }
    }
    }
    }
    }
    System.out.println("done");
  }
  
  public static boolean propagate(Polyhedron p, Mapping m) {
    boolean modified = true;
    while (modified) {
//      System.out.println("loop");
      modified = false;
      // Propagate across edges
      for (Face f: p.faces) {
//        System.out.println("1 face");
        for (Edge e: f.edges) {
//          System.out.println("1 edge");
          if (e.color != null) {
            if (e.dual.color == null) {
              e.dual.color = m.apply(e.color);
              modified = true;
            } else if (e.dual.color != e.color) {
              //TODO I'm not even sure how to give an informative error, here
//              System.err.println("Color mismatch across edges");
              return false;
            }
          }
        }
      }
      
      // Propagate around faces
      for (Face f: p.faces) {
//        System.out.println("2 face");
        boolean hasBlank = false;
        Integer nonBlank = null;
        for (int i = 0; i < f.edges.length; i++) {
//          System.out.println("2 edge");
          if (f.edges[i].color == null) {
            hasBlank = true;
          } else {
            nonBlank = i;
          }
        }
        if (hasBlank && nonBlank != null) {
          int i = nonBlank;
          do {
//            System.out.println("3 edge");
            int next = (i+1) % f.edges.length;
            Integer nextColor = f.nextColor(f.edges[i].color);
            if (f.edges[next].color == null) {
              f.edges[next].color = nextColor;
              modified = true;
            } else if (f.edges[next].color == nextColor) {
              // Nothing
            } else {
//              System.err.println("Color mismatch around face");
              return false;
            }
            i = next;
          } while (i != nonBlank);
        }
      }
    }
    return true;
  }
}
