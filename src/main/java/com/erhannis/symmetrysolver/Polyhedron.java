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
public class Polyhedron {
  public Face[] faces;

  /**
   * There is probably an "easier" way of doing this.  Buuuut that'd require
   * figuring it out, and doing it by hand didn't take that long.  Hopefully
   * it's right.
   * 
   */
  public static Polyhedron dodecahedron() {
    Polyhedron p = new Polyhedron();
    p.faces = new Face[12];
    for (int i = 0; i < 12; i++) {
      Face f = new Face();
      f.parent = p;
      f.edges = new Edge[5];
      for (int j = 0; j < 5; j++) {
        f.edges[j] = new Edge();
      }
      p.faces[i] = f;
    }
    p.faces[0].edges[0].match(p.faces[1].edges[0]);
    p.faces[0].edges[1].match(p.faces[2].edges[0]);
    p.faces[0].edges[2].match(p.faces[3].edges[0]);
    p.faces[0].edges[3].match(p.faces[4].edges[0]);
    p.faces[0].edges[4].match(p.faces[5].edges[0]);

    p.faces[1].edges[1].match(p.faces[5].edges[4]);
    p.faces[1].edges[2].match(p.faces[6].edges[0]);
    p.faces[1].edges[3].match(p.faces[7].edges[1]);
    p.faces[1].edges[4].match(p.faces[2].edges[1]);

    p.faces[2].edges[2].match(p.faces[7].edges[0]);
    p.faces[2].edges[3].match(p.faces[8].edges[1]);
    p.faces[2].edges[4].match(p.faces[3].edges[1]);

    p.faces[3].edges[2].match(p.faces[8].edges[0]);
    p.faces[3].edges[3].match(p.faces[9].edges[1]);
    p.faces[3].edges[4].match(p.faces[4].edges[1]);

    p.faces[4].edges[2].match(p.faces[9].edges[0]);
    p.faces[4].edges[3].match(p.faces[10].edges[1]);
    p.faces[4].edges[4].match(p.faces[5].edges[1]);

    p.faces[5].edges[2].match(p.faces[10].edges[0]);
    p.faces[5].edges[3].match(p.faces[6].edges[1]);

    p.faces[6].edges[2].match(p.faces[10].edges[4]);
    p.faces[6].edges[3].match(p.faces[11].edges[0]);
    p.faces[6].edges[4].match(p.faces[7].edges[2]);

    p.faces[7].edges[3].match(p.faces[11].edges[4]);
    p.faces[7].edges[4].match(p.faces[8].edges[2]);

    p.faces[8].edges[3].match(p.faces[11].edges[3]);
    p.faces[8].edges[4].match(p.faces[9].edges[2]);

    p.faces[9].edges[3].match(p.faces[11].edges[2]);
    p.faces[9].edges[4].match(p.faces[10].edges[2]);

    p.faces[10].edges[3].match(p.faces[11].edges[1]);
    
    return p;
  }
}
