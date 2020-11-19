/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.symmetrysolver;

import com.erhannis.javastl.Stl;
import com.erhannis.mathnstuff.MeUtils;
import com.erhannis.mathnstuff.Multivector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author erhannis
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    if (1==0) {
      Multivector a = Multivector.fromVector(1,0.0001,0);
      Multivector b = Multivector.fromVector(-1,0,0);
      Multivector c = Multivector.fromVector(1,0,0);
      Multivector x = a.add(b).divSIP(2);
      x = x.mulSIP(1/x.vectorNorm());
      System.out.println("a " + a);
      System.out.println("b " + b);
      System.out.println("c " + c);
      
      System.out.println("");
      System.out.println("x     " + x);
      Multivector z = x.mul(a);
      System.out.println("xa    " + z);
      System.out.println("xac   " + z.mulIP(c));
      System.out.println("xaca  " + z.mulIP(a));
      System.out.println("xacax " + z.mulIP(x));
      if (1 == 1) return;
    }
    if (1==0) {
      Polyhedron p;

      p = Polyhedron.dodecahedron();
      p.computeVertices();
      MeUtils.writeToFileOrDie("/home/erhannis/temp/dodecahedron.stl", p.render());

      p = Polyhedron.tubeThing();
      p.computeVertices();
      MeUtils.writeToFileOrDie("/home/erhannis/temp/tubething.stl", p.render());
      
      if (1 == 1) return;
    }
    
    int FACES = 12;
    int N;
    switch (FACES) {
      case 8:
        N = 3;
        break;
      case 12:
        N = 5;
        break;
      default:
        throw new IllegalArgumentException("Unhandled face count: " + FACES);
    }    
    
    ArrayList<Integer> dims = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      dims.add(N*2);
    }
    ArrayList<Integer> dimToColor = new ArrayList<>();
    for (int i = -N; i <= -1; i++) {
      dimToColor.add(i);
    }
    for (int i = 1; i <= 5; i++) {
      dimToColor.add(i);
    }
    
    
    MeUtils.runRecursion((idxs) -> {
      idxs = IntStream.of(idxs)
                .map(i -> dimToColor.get(i))
                .toArray();
      Polyhedron p;
      switch (FACES) {
        case 8:
          p = Polyhedron.d8();
          break;
        case 12:
          p = Polyhedron.dodecahedron();
          break;
        default:
          throw new IllegalArgumentException("Unhandled face count: " + FACES);
      }

      Mapping m;
      try {
        m = Mapping.fromArray(idxs);
      } catch (IllegalArgumentException err) {
//        System.out.println("illegal mapping "+Arrays.toString(idxs));
        return;
      }

      for (int i = 0; i < p.faces[0].edges.length; i++) {
        p.faces[0].edges[i].color = i+1;
      }

      if (propagate(p, m)) {
        System.out.println("success "+Arrays.toString(idxs));
        
        p.computeVertices();
        double[][][] mtx = p.calcSymmetryMatrices();
        for (int f = 0; f < mtx.length; f++) {
          System.out.println("face " + f);
          for (int y = 0; y < mtx[f].length; y++) {
            System.out.println(Arrays.toString(mtx[f][y]));
          }
        }
        MeUtils.writeToFileOrDie("/home/erhannis/temp/dodecahedron.stl", p.render());
        
        System.exit(0);
        
      } else {
        //System.out.println("failure "+Arrays.toString(idxs));
      }
/*      
      for (int j = 0; j < p.faces.length; j++) {
        Face f = p.faces[j];
        System.out.println("face " + j);
        for (int i = 0; i < f.edges.length; i++) {
          System.out.println(i + ": " + f.edges[i].color + " " + f.edges[i].dual.color);
        }
      }
*/
    }, dims.stream().mapToInt(i -> i).toArray());
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
            } else if (e.dual.color != m.apply(e.color)) {
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
