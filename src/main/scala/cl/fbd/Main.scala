package cl.fbd

import scalanative.native._
import scalanative.libc.stdlib._

/*
 *
 */

object Main {
  def main (args : Array [String]) = {
    
    testArrayCopy ()
    
    ()
  }
  
  def testArrayCopy () {
    val len = 10
    
    val arrInt = new Array[Int] (len)
    
    var c = 0
    
    while (c < len) {
      arrInt (c) = c
      
      c += 1
    }
    
    
    fprintf (stdout, c"arrInt\n")    
    dump(arrInt)
    
    val arrInt2 = new Array[Int] (len * 2)
    
    scalanative.runtime.Array.arraycopy (arrInt, 0, arrInt2, 5, 10)
    
    fprintf (stdout, c"arrInt2\n")    
    dump(arrInt2) 
  }
  
  def testPtrInfoInstanceType (arrInt: Array[Int]) = {
    val len = 10
    
    val arrInt = new Array[Int] (len)
    
    import scalanative.runtime._
    
    val ptrinfo = arrInt.cast[Ptr[Ptr[Ptr[Type]]]]
    
    fprintf (stdout, c"ptrinfo from instance of Array[Int]: %d\n", (!(!(!ptrinfo))).id)    
  }
  
  def testPtrInfoType () = {
    import scalanative.runtime._
    
    val arrayIntTypeId = typeId (infoof[scalanative.runtime.IntArray])
    
    fprintf (stdout, c"ptrinfo from IntArray: %d\n", arrayIntTypeId)
  }
  
  def typeId (ptrInfo : Ptr[_]) : Int = {
    import scalanative.runtime._
    
    val ptr = ptrInfo.cast[Ptr[Ptr[Type]]]
    
    (!(!ptr)).id    
  }
  
  def testClone () = {
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


