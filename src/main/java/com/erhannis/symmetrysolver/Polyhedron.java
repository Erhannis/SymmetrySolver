/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.symmetrysolver;

import com.erhannis.javastl.Stl;
import com.erhannis.mathnstuff.MeMath;
import com.erhannis.mathnstuff.MeUtils;
import com.erhannis.mathnstuff.Multivector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    for (int i = 0; i < p.faces.length; i++) {
      Face f = new Face();
      f.parent = p;
      f.edges = new Edge[5];
      for (int j = 0; j < f.edges.length; j++) {
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

  /**
   * This is an experiment, and may not work well.
   * @return 
   */
  public static Polyhedron tubeThing() {
    Polyhedron p = new Polyhedron();
    p.faces = new Face[10];
    for (int i = 0; i < p.faces.length; i++) {
      Face f = new Face();
      f.parent = p;
      f.edges = new Edge[4];
      for (int j = 0; j < f.edges.length; j++) {
        f.edges[j] = new Edge();
        f.edges[j].parent = f;
      }
      p.faces[i] = f;
    }
    p.faces[0].edges[0].match(p.faces[1].edges[2]);
    p.faces[0].edges[1].match(p.faces[2].edges[3]);
    p.faces[0].edges[2].match(p.faces[3].edges[0]);
    p.faces[0].edges[3].match(p.faces[4].edges[1]);

    p.faces[1].edges[3].match(p.faces[4].edges[0]);
    p.faces[1].edges[0].match(p.faces[5].edges[2]);
    p.faces[1].edges[1].match(p.faces[2].edges[0]);

    p.faces[5].edges[3].match(p.faces[8].edges[0]);
    p.faces[5].edges[0].match(p.faces[9].edges[2]);
    p.faces[5].edges[1].match(p.faces[6].edges[0]);

    p.faces[2].edges[1].match(p.faces[6].edges[3]);
    p.faces[2].edges[2].match(p.faces[3].edges[1]);

    p.faces[6].edges[1].match(p.faces[9].edges[1]);
    p.faces[6].edges[2].match(p.faces[7].edges[1]);

    p.faces[3].edges[2].match(p.faces[7].edges[0]);
    p.faces[3].edges[3].match(p.faces[4].edges[2]);

    p.faces[7].edges[2].match(p.faces[9].edges[0]);
    p.faces[7].edges[3].match(p.faces[8].edges[2]);

    p.faces[4].edges[3].match(p.faces[8].edges[1]);

    p.faces[8].edges[3].match(p.faces[9].edges[3]);

    p.validateOrThrow();    
    return p;
  }

  public static Polyhedron coin(int edges) {
    Polyhedron p = new Polyhedron();
    p.faces = new Face[2];
    for (int i = 0; i < p.faces.length; i++) {
      Face f = new Face();
      f.parent = p;
      f.edges = new Edge[edges];
      for (int j = 0; j < f.edges.length; j++) {
        f.edges[j] = new Edge();
        f.edges[j].parent = f;
      }
      p.faces[i] = f;
    }
    Face fa = p.faces[0];
    Face fb = p.faces[1];
    for (int i = 0; i < edges; i++) {
      p.faces[0].edges[i].match(p.faces[1].edges[edges-i-1]);
    }

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

  /**
   * Don't call this more than once
   */
  public void computeVertices() {
    HashSet<Vertex> vertices = new HashSet<>();
    for (Face f: this.faces) {
      for (Edge e: f.edges) {
        if (e.va == null) {
          e.va = new Vertex();
          vertices.add(e.va);
          
          Edge e2 = e;
          do {
            e2.dual.vb = e2.va;
            e2 = e2.dual;
            e2.next().va = e2.vb;
            e2 = e2.next();
          } while (e2 != e); //TODO Like, I guess with a malformed polyhedron this might not happen
        }
      }
    }
    for (Face f: this.faces) {
      for (Edge e: f.edges) {
        if (e.va == null) {
          throw new RuntimeException("e.va == null!!");
        }
        if (e.vb == null) {
          throw new RuntimeException("e.vb == null!!");
        }
      }    
    }
    for (Face f: this.faces) {
      ArrayList<Vertex> vs = new ArrayList<>();
      for (Edge e: f.edges) {
        vs.add(e.va);
      }
      f.vertices = vs.toArray(new Vertex[0]);
    }
    
    //System.out.println("Computed " + vertices.size() + " vertices");
    // Distribute vertices in space
    Random r = new Random();
    for (Vertex v: vertices) {
      v.position = new double[]{r.nextDouble(), r.nextDouble(), r.nextDouble()};
    }
    
    HashMap<Vertex, HashSet<Vertex>> neighbors = new HashMap<>();
    for (Face f: this.faces) {
      for (Edge e: f.edges) {
        HashSet<Vertex> nbs = neighbors.getOrDefault(e.va, new HashSet<Vertex>());
        nbs.add(e.vb);
        neighbors.put(e.va, nbs);
      }
    }
    
    for (int iters = 0; iters < 10000; iters++) {
      // Calc center of mass
      double[] sum = new double[]{0,0,0};
      for (Vertex v: vertices) {
        sum[0] += v.position[0];
        sum[1] += v.position[1];
        sum[2] += v.position[2];
      }
      sum[0] /= vertices.size();
      sum[1] /= vertices.size();
      sum[2] /= vertices.size();
      
      // Center on origin
      for (Vertex v: vertices) {
        v.position[0] -= sum[0];
        v.position[1] -= sum[1];
        v.position[2] -= sum[2];
      }
      
      // Find max dist
      double maxSqr = 0;
      for (Vertex v: vertices) {
        double distSqr = MeMath.vectorLengthSqr(v.position);
        if (distSqr > maxSqr) {
          maxSqr = distSqr;
        }
      }
      // Rescale
      for (Vertex v: vertices) {
        MeMath.vectorScaleIP(v.position, 1.0/Math.sqrt(maxSqr));
      }
      
      // Apply forces
      HashMap<Vertex, double[]> forces = new HashMap<>();
      for (Vertex v0: vertices) {
        double[] force = new double[]{0,0,0};
        //for (Vertex v1: neighbors.get(v0)) {
        for (Vertex v1: vertices) {
          if (v1 == v0) {
            continue;
          }
          double[] f0 = MeMath.vectorSubtract(v1.position, v0.position);
          double d0 = MeMath.vectorLength(f0);
          MeMath.vectorNormalizeIP(f0);
          MeMath.vectorAddIP(force, MeMath.vectorScaleIP(f0, -1.0/d0));
        }
        MeMath.vectorScaleIP(force, 0.05/vertices.size());
        for (Vertex v1: neighbors.get(v0)) {
          double[] f0 = MeMath.vectorSubtract(v1.position, v0.position);
          double d0 = MeMath.vectorLength(f0);
          MeMath.vectorAddIP(force, MeMath.vectorScaleIP(f0, d0));
        }
        forces.put(v0, MeMath.vectorScaleIP(force, 0.1));
      }
      for (Vertex v0: vertices) {
        MeMath.vectorAddIP(v0.position, forces.get(v0));
      }
    }
  }
  
  /**
   * This is, ehh, not exactly correct.<br/>
   * <br/>
   * Example:<br/>
   * <code>
   * Polyhedron p = Polyhedron.dodecahedron();<br/>
   * p.computeVertices();<br/>
   * System.out.println(p.render());<br/>
   * </code>
   */
  public String render() {
    ArrayList<double[]> points = new ArrayList<>();
    for (Face f: faces) {
      for (int i = 0; i < f.vertices.length; i++) {
        double[] a = f.vertices[(i+2)%f.vertices.length].position;
        double[] b = f.vertices[(i+1)%f.vertices.length].position;
        double[] c = f.vertices[i].position;
        points.add(a);
        points.add(b);
        points.add(c);
      }
    }
    return Stl.pointsToStl(points.toArray(new double[0][0]), "polyhedron");
  }

  public String render(ArrayList<double[]> extras) {
    ArrayList<double[]> points = new ArrayList<>();
    for (Face f: faces) {
      for (int i = 0; i < f.vertices.length; i++) {
        double[] a = f.vertices[(i+2)%f.vertices.length].position;
        double[] b = f.vertices[(i+1)%f.vertices.length].position;
        double[] c = f.vertices[i].position;
        points.add(a);
        points.add(b);
        points.add(c);
      }
    }
    points.addAll(extras);
    return Stl.pointsToStl(points.toArray(new double[0][0]), "polyhedron");
  }
  
  /**
   * Calculate symmetry matrices
   * @return double[FACE][Y][X]
   */
  public double[][][] calcSymmetryMatrices() {
    int primeIdx = 0;
    
    double[][][] result = new double[faces.length][][];
    result[primeIdx] = new double[][]{{1,0,0},{0,1,0},{0,0,1}};
    
    Face prime = faces[primeIdx];
    double[] primeCenter = new double[3];
    for (Edge e: prime.edges) {
      MeMath.vectorAddIP(primeCenter, e.va.position);
    }
    MeMath.vectorScaleIP(primeCenter, 1.0/prime.edges.length);
    double[] primeEdge = null;
    boolean primeMirrored = false;
    for (Edge e: prime.edges) {
      if (e.color == 1) {
        primeMirrored = false;
        primeEdge = MeMath.vectorSubtract(e.va.position, primeCenter);
        break;
      } else if (e.color == -1) {
        primeMirrored = true;
        primeEdge = MeMath.vectorSubtract(e.vb.position, primeCenter);
        break;
      }
    }
    MeMath.vectorNormalizeIP(primeCenter);
    MeMath.vectorNormalizeIP(primeEdge);

    ArrayList<double[]> arrows2 = new ArrayList<>();
    arrows2.add(MeMath.vectorScale(primeCenter, 2.0));
    arrows2.add(MeMath.vectorScale(primeCenter, 0.7));
    arrows2.add(MeMath.vectorAdd(MeMath.vectorScale(primeCenter, 0.7), MeMath.vectorScale(primeEdge, 0.2)));
    
    ArrayList<double[]> arrows1 = new ArrayList<>();
    arrows1.add(MeMath.vectorScale(primeCenter, 2.0));
    arrows1.add(MeMath.vectorScale(primeCenter, 0.7));
    arrows1.add(MeMath.vectorAdd(MeMath.vectorScale(primeCenter, 0.7), MeMath.vectorScale(primeEdge, 0.2)));
    for (Edge e: prime.edges) {
      if (e.color == 1) {
        arrows1.add(MeMath.vectorSubtract(e.va.position, MeMath.vectorScale(primeEdge, 0.1)));
        arrows1.add(MeMath.vectorSubtract(e.vb.position, MeMath.vectorScale(primeEdge, 0.1)));
        arrows1.add(MeMath.vectorAdd(MeMath.vectorSubtract(e.vb.position, MeMath.vectorScale(primeEdge, 0.1)), MeMath.vectorScale(primeCenter, 0.1)));
        break;
      } else if (e.color == -1) {
        arrows1.add(MeMath.vectorSubtract(e.vb.position, MeMath.vectorScale(primeEdge, 0.1)));
        arrows1.add(MeMath.vectorSubtract(e.va.position, MeMath.vectorScale(primeEdge, 0.1)));
        arrows1.add(MeMath.vectorAdd(MeMath.vectorSubtract(e.va.position, MeMath.vectorScale(primeEdge, 0.1)), MeMath.vectorScale(primeCenter, 0.1)));
        break;
      }
    }
    List<Integer> idxs = new ArrayList<>(IntStream.range(0, faces.length).mapToObj(i -> (Integer)i).collect(Collectors.toList()));
    idxs.remove((Integer)primeIdx);
    Collections.shuffle(idxs);
    for (int i0 = 1; i0 < faces.length; i0++) {
      int i = idxs.get(i0-1);
      Face secondary = faces[i];
      double[] secondaryCenter = new double[3];
      for (Edge e: secondary.edges) {
        MeMath.vectorAddIP(secondaryCenter, e.va.position);
      }
      MeMath.vectorScaleIP(secondaryCenter, 1.0/secondary.edges.length);
      double[] secondaryEdge = null;
      boolean secondaryMirrored = false;
      for (Edge e: secondary.edges) {
        if (e.color == 1) {
          secondaryMirrored = false;
          secondaryEdge = MeMath.vectorSubtract(e.va.position, secondaryCenter);
          
          arrows1.add(MeMath.vectorSubtract(e.va.position, MeMath.vectorScale(secondaryEdge, 0.1)));
          arrows1.add(MeMath.vectorSubtract(e.vb.position, MeMath.vectorScale(secondaryEdge, 0.1)));
          arrows1.add(MeMath.vectorAdd(MeMath.vectorSubtract(e.vb.position, MeMath.vectorScale(secondaryEdge, 0.1)), MeMath.vectorScale(secondaryCenter, 0.1)));
          break;
        } else if (e.color == -1) {
          secondaryMirrored = true;
          secondaryEdge = MeMath.vectorSubtract(e.vb.position, secondaryCenter);

          arrows1.add(MeMath.vectorSubtract(e.vb.position, MeMath.vectorScale(secondaryEdge, 0.1)));
          arrows1.add(MeMath.vectorSubtract(e.va.position, MeMath.vectorScale(secondaryEdge, 0.1)));
          arrows1.add(MeMath.vectorAdd(MeMath.vectorSubtract(e.va.position, MeMath.vectorScale(secondaryEdge, 0.1)), MeMath.vectorScale(secondaryCenter, 0.1)));
          break;
        }
      }
      MeMath.vectorNormalizeIP(secondaryCenter);
      MeMath.vectorNormalizeIP(secondaryEdge);
      
      double[][] basis = {{1,0,0},{0,1,0},{0,0,1}};

      double[] localPrimeEdge;
      if ((Math.PI-MeMath.vectorAngle(primeCenter, secondaryCenter)) < 0.01 || MeMath.vectorLength(MeMath.vectorAdd(primeCenter, secondaryCenter)) < 0.01) {
        System.out.println("face " + i + " center a: " + (Math.PI-MeMath.vectorAngle(primeCenter, secondaryCenter)));
        System.out.println("face " + i + " center l: " + MeMath.vectorLength(MeMath.vectorAdd(primeCenter, secondaryCenter)));
        // Centers opposed; rotate somehow else
        double[] pivot = MeMath.crossProduct3d(primeCenter, primeEdge);
        localPrimeEdge = Multivector.rotate2(primeEdge, primeCenter, pivot);
        basis = Multivector.rotate2(basis, primeCenter, pivot);
      } else {
        localPrimeEdge = Multivector.rotate(primeEdge, primeCenter, secondaryCenter);
        basis = Multivector.rotate(basis, primeCenter, secondaryCenter);
      }

      
      if ((Math.PI-MeMath.vectorAngle(localPrimeEdge, secondaryEdge)) < 0.01 || MeMath.vectorLength(MeMath.vectorAdd(localPrimeEdge, secondaryEdge)) < 0.01) {
        System.out.println("face " + i + " edges a: " + (Math.PI-MeMath.vectorAngle(localPrimeEdge, secondaryEdge)));
        System.out.println("face " + i + " edges l: " + MeMath.vectorLength(MeMath.vectorAdd(localPrimeEdge, secondaryEdge)));
        // Edges are approx opposed; rotate some other way
        basis = Multivector.lineMirror(basis, secondaryCenter);
      } else {
        basis = Multivector.rotate(basis, localPrimeEdge, secondaryEdge);
      }
      
      if (primeMirrored != secondaryMirrored) {
        basis = Multivector.mirror(basis, secondaryCenter, secondaryEdge);
      }
      
      double[][] basisT = {{basis[0][0],basis[1][0],basis[2][0]},{basis[0][1],basis[1][1],basis[2][1]},{basis[0][2],basis[1][2],basis[2][2]}};
      result[i] = basisT;

      /*
      double[] checkCenter = Multivector.rotate(primeCenter, primeCenter, secondaryCenter);
      checkCenter = Multivector.rotate(checkCenter, localPrimeEdge, secondaryEdge);

      double[] checkEdge = Multivector.rotate(primeEdge, primeCenter, secondaryCenter);
      checkEdge = Multivector.rotate(checkEdge, localPrimeEdge, secondaryEdge);
      */      

//      double[][] basisT = {{basis[0][0],basis[1][0],basis[2][0]},{basis[0][1],basis[1][1],basis[2][1]},{basis[0][2],basis[1][2],basis[2][2]}};
      double[][] b = basisT;
      double[] checkCenter = new double[]{MeMath.dotProduct(b[0], primeCenter),MeMath.dotProduct(b[1], primeCenter),MeMath.dotProduct(b[2], primeCenter)};
      double[] checkEdge = new double[]{MeMath.dotProduct(b[0], primeEdge),MeMath.dotProduct(b[1], primeEdge),MeMath.dotProduct(b[2], primeEdge)};

      arrows1.add(MeMath.vectorScale(secondaryCenter, 1.5));
      arrows1.add(MeMath.vectorScale(secondaryCenter, 0.4));
      arrows1.add(MeMath.vectorAdd(MeMath.vectorScale(secondaryCenter, 0.4), MeMath.vectorScale(secondaryEdge, 0.2)));
      
      arrows1.add(MeMath.vectorScale(checkCenter, 1.25));
      arrows1.add(MeMath.vectorScale(checkCenter, 0.8));
      arrows1.add(MeMath.vectorAdd(MeMath.vectorScale(checkCenter, 0.8), MeMath.vectorScale(checkEdge, 0.2)));
      
      double diff = MeMath.vectorLength(MeMath.vectorSubtract(secondaryCenter, checkCenter));
      if (diff > 0.001) {
        System.out.println("face " + i + " diff " + diff);
        System.out.println(Arrays.toString(secondaryCenter));
        System.out.println(Arrays.toString(checkCenter));
      }
    }
    //TODO Remove
    MeUtils.writeToFileOrDie("/home/erhannis/temp/d"+faces.length+"_plus.stl", this.render(arrows1));
    
    return result;
  }
  
  public boolean verifyColors(Mapping m) {
    for (Face f: this.faces) {
      for (int i = 0; i < f.edges.length; i++) {
        Edge e0 = f.edges[i];
        Edge e1 = f.edges[(i+1)%f.edges.length];
        if (e0.color == null) {
          return false;
        }
        if (e1.color != f.nextColor(e0.color)) {
          return false;
        }
        if (e0.dual.color != m.apply(e0.color)) {
          return false;
        }
      }
    }
    return true;
  }
}
