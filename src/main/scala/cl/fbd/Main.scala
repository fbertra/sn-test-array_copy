package cl.fbd

import scalanative.native._
import scalanative.libc.stdlib._

/*
 *
 */

object Main {
  def main (args : Array [String]) = {
    
    myassert (true)
    
    fprintf (stdout, c"Test Array clone\n")
    
    val len = 10
    
    val arrInt = new Array[Int] (len)
    // val arrInt = IntArray.alloc (len)
    
    var c = 0
    
    while (c < len) {
      arrInt (c) = c
      
      c += 1
    }
    
    
    fprintf (stdout, c"arrInt\n")    
    dump(arrInt)
    
    val arrInt2 = arrInt.clone()
    fprintf (stdout, c"arrInt2\n")    
    dump(arrInt2)
    
    ()
  }
  
  def dump (arr : Array [Int]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      fprintf (stdout, c"(%d -> %d)", c, arr (c))
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n", c, arr (c))
      else
        fprintf (stdout, c", ", c, arr (c))
      
      c += 1
    }
  }

  def myassert(cond: Boolean) = if (!cond) throw new RuntimeException("assert failed")
}


