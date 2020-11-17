/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.symmetrysolver;

import com.erhannis.mathnstuff.MeUtils;
import java.util.HashSet;
import java.util.LinkedList;

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
        f.edges[j].parent = f;
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

    p.validateOrThrow();    
    return p;
  }

  public static Polyhedron d8() {
    Polyhedron p = new Polyhedron();
    p.faces = new Face[8];
    for (int i = 0; i < p.faces.length; i++) {
      Face f = new Face();
      f.parent = p;
      f.edges = new Edge[3];
      for (int j = 0; j < f.edges.length; j++) {
        f.edges[j] = new Edge();
        f.edges[j].parent = f;
      }
      p.faces[i] = f;
    }
    p.faces[0].edges[0].match(p.faces[4].edges[0]);
    p.faces[0].edges[1].match(p.faces[1].edges[2]);
    p.faces[0].edges[2].match(p.faces[3].edges[1]);

    p.faces[1].edges[0].match(p.faces[5].edges[0]);
    p.faces[1].edges[1].match(p.faces[2].edges[2]);

    p.faces[2].edges[0].match(p.faces[6].edges[0]);
    p.faces[2].edges[1].match(p.faces[3].edges[2]);

    p.faces[3].edges[0].match(p.faces[7].edges[0]);

    p.faces[4].edges[1].match(p.faces[7].edges[2]);
    p.faces[4].edges[2].match(p.faces[5].edges[1]);

    p.faces[5].edges[2].match(p.faces[6].edges[1]);

    p.faces[6].edges[2].match(p.faces[7].edges[1]);
    
    p.validateOrThrow();
    return p;
  }
  
  public void validateOrThrow() {
    int edgeCount = 0;
    HashSet<Edge> edgeSet = new HashSet<>();
    HashSet<Edge> dualSet = new HashSet<>();
    for (Face f: this.faces) {
      edgeCount += f.edges.length;
      for (Edge e: f.edges) {
        if (e.parent == null) {
          throw new RuntimeException("found edge with null parent");
        }
        if (e == null) {
          throw new RuntimeException("found null edge");
        }
        if (e.dual == null) {
          throw new RuntimeException("found null edge.dual");
        }
        if (e.dual.parent == null) {
          throw new RuntimeException("found edge.dual with null parent");
        }
        if (e.dual.dual != e) {
          throw new RuntimeException("edge.dual.dual != edge");
        }
        if (edgeSet.contains(e)) {
          throw new RuntimeException("edge already seen");
        }
        edgeSet.add(e);
        if (dualSet.contains(e.dual)) {
          throw new RuntimeException("edge.dual already seen");
        }
        dualSet.add(e.dual);
      }
    }
    if (edgeSet.size() != edgeCount) {
      throw new RuntimeException("edgeSet size == " + edgeSet.size() + " != " + edgeCount);
    }
    if (dualSet.size() != edgeCount) {
      throw new RuntimeException("dualSet size == " + dualSet.size() + " != " + edgeCount);
    }
    
    HashSet<Face> unprocessedFaceSet = new HashSet<>();
    unprocessedFaceSet.add(this.faces[0]);
    HashSet<Face> processedFaceSet = new HashSet<>();
    while (!unprocessedFaceSet.isEmpty()) {
      Face f = MeUtils.pop(unprocessedFaceSet);
      processedFaceSet.add(f);
      for (Edge e: f.edges) {
        if (!processedFaceSet.contains(e.dual.parent)) {
          unprocessedFaceSet.add(e.dual.parent);
        }
      }
    }
    if (processedFaceSet.size() != this.faces.length) {
      throw new RuntimeException("traversed faces count == " + processedFaceSet.size() + " != " + this.faces.length);
    }
    for (Face f: this.faces) {
      if (!processedFaceSet.contains(f)) {
        throw new RuntimeException("not all faces reached by traversal");
      }
    }
  }
}
