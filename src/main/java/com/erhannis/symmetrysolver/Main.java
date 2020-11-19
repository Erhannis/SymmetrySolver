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
    {
      double[] x = {1,1,1};
      double[] a = {1,-3.5,0};
      double[] b = {-2,0.5,0};
      System.out.println("cxc " + Arrays.toString(Multivector.mirror(x, a, b)));
      
      if (1 == 1) return;
    }
    {
      Multivector x = Multivector.fromVector(1,0,0).mulIP(Multivector.fromVector(0,1,0)).mulIP(Multivector.fromVector(0,0,1));
      Multivector a = Multivector.fromVector(1.1,-0.3,0);
      Multivector b = Multivector.fromVector(1.1,-0.3,-2);
      Multivector c = Multivector.fromVector(0,0,1);
      Multivector d = Multivector.fromVector(-1,-2,-3);

//      System.out.println("a   " + a);
//      System.out.println("xa " + x.mul(a));
//      System.out.println("ax " + a.mul(x));
//      System.out.println("xax " + x.mul(a).mul(x));

      System.out.println("x   " + x);
      System.out.println("a   " + a);
      System.out.println("xax " + x.mul(a).mul(x));
      System.out.println("b   " + b);
      System.out.println("xbx " + x.mul(b).mul(x));
      System.out.println("c   " + c);
      System.out.println("xcx " + x.mul(c).mul(x));
      System.out.println("a   " + d);
      System.out.println("xdx " + x.mul(d).mul(x));
      System.out.println("");
      System.out.println("a-xax " + a.sub(x.mul(a).mul(x)));
      System.out.println("b-xbx " + b.sub(x.mul(b).mul(x)));
      System.out.println("c-xcx " + c.sub(x.mul(c).mul(x)));
      System.out.println("d-xdx " + d.sub(x.mul(d).mul(x)));
      
      if (1 == 1) return;
    }
    {
      System.out.println(Multivector.fromVector(1,0,0).innerIP(Multivector.fromVector(0,1,0)));
      System.out.println(Multivector.fromVector(1,0,0).outerIP(Multivector.fromVector(0,1,0)));
      Multivector a = Multivector.fromVector(1,0,0);
      Multivector b = Multivector.fromVector(0,1,0);
      Multivector c = Multivector.fromVector(1,1,1);
      Multivector x = a.add(b).divSIP(2);
      x = x.mulSIP(1/x.vectorNorm());
      System.out.println("a " + a);
      System.out.println("c " + c);
      System.out.println("a*c " + a.inner(c));

      System.out.println("");
      System.out.println("x     " + x);
      Multivector z = x.mul(a);
      System.out.println("xa    " + z);
      System.out.println("xac   " + z.mulIP(c));
      System.out.println("xaca  " + z.mulIP(a));
      System.out.println("xacax " + z.mulIP(x));
      System.out.println("");

      System.out.println("xacax " + x.mul(a).mulIP(c).mulIP(a).mulIP(x));
      System.out.println("xacax " + Multivector.fromVector(Multivector.rotate(c.vectorComponent(), a.vectorComponent(), b.vectorComponent())));

      double[][] xs = {c.vectorComponent(), c.vectorComponent(), c.vectorComponent(), {1,1,1},{1,0,0},{0,1,0},{0,0,1}};
      double[][] ys = Multivector.rotate(xs, a.vectorComponent(), b.vectorComponent());
      for (int i = 0; i < ys.length; i++) {
        System.out.println("xa?ax " + i + " " + Arrays.toString(ys[i]));
      }
        
      if (1 == 1) return;
    }        
    
    
    {
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
