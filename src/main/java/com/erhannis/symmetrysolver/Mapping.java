/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhannis.symmetrysolver;

import java.util.function.Function;

/**
 *
 * @author erhannis
 */
@FunctionalInterface
public interface Mapping extends Function<Integer, Integer> {
  public Integer apply(Integer x);
  
  /**
   * A mapping such that 1 -> the first param, 2 -> the second param, etc.
   * If input is negative, output sign is flipped.
   * E.g., -2 -> (second param * -1).
   * 
   * For a mapping F to be valid:
   * F(x)=y => F(y)=x
   * F(null)=null
   * 
   * @param array
   * @return 
   */
  public static Mapping fromArray(Integer... array) {
    Mapping m = (x) -> {
      if (x == null) {
        return null;
      }
      int neg = 1;
      if (x < 0) {
        neg = -1;
        x = Math.abs(x);
      }
      return array[x-1]*neg;
    };
    //TODO Export into `validate` function?
    for (int j = -1; j <= 1; j+=2) {
      for (int i = 1; i <= array.length; i++) {
        int x = i*j;
        if (m.apply(m.apply(x)) != x) {
          throw new IllegalArgumentException("F(F("+x+"))="+m.apply(m.apply(x)) + " but should ="+x);
        }
        if (m.apply(x*-1) != (m.apply(x)*-1)) {
          throw new IllegalArgumentException("F(-"+x+")="+m.apply(x*-1) + " but should =-"+m.apply(x));
        }
      }
    }
    return m;
  }
}
