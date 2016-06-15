package cl.fbd

import scalanative.native._
import scalanative.libc.stdlib._

class A(d: Double)
class B(d: Double, i: Int) extends A(d)

/*
 * TO-DO:
 * - use assert() (working now)
 */

object Main {
  def main (args : Array [String]) : Unit = {   
    fprintf (stdout, c"Test related to array copy\n")
    
    testcopyInt ()
    testcopyByte ()    
    testcopyDouble ()
    testcopyBoolean ()
    
    others()
    
    ()
  }
    
  // not related to copy, but sanity check after big refactoring
  def others() {
    val arr = new Array[Int] (10)
    
    fprintf (stdout, c"apply() and update()\n")
    val i = 100
    arr (5) = i
    val j = arr (5)
    assert (i == j)
    
    fprintf (stdout, c"apply() out of range\n")
    fprintf (stdout, c"Negative index\n")
    try {
      val i = arr (-1)
      fprintf (stdout, c"fail %d\n", i)
    }
    catch {
      case th: Throwable => fprintf (stdout, c"exception\n")
    }
    
    fprintf (stdout, c"index >= array length\n")
    try {
      val i = arr (10)
      fprintf (stdout, c"fail %d\n", i)
    }
    catch {
      case th: Throwable => fprintf (stdout, c"exception\n")
    }
    
    fprintf (stdout, c"update() out of range\n")
    fprintf (stdout, c"Negative index\n")
    try {
      arr (-1) = 1
      fprintf (stdout, c"fail %d\n")
    }
    catch {
      case th: Throwable => fprintf (stdout, c"exception\n")
    }
    
    fprintf (stdout, c"index >= array length\n")
    try {
      arr (10) = 1
      fprintf (stdout, c"fail %d\n")
    }
    catch {
      case th: Throwable => fprintf (stdout, c"exception\n")
    }
  }
    
  //            ^
  
  def testcopyDouble () {
    fprintf (stdout, c"\ntest Array Double\n")

    def init (arr: Array[Double]) = {    
      var c = 0
    
      while (c < arr.length) {
        arr (c) = c.toDouble
      
        c += 1
      }
    }

    
    val len = 10
    
    val arrDouble = new Array[Double] (len)
    init (arrDouble)
    
        
    fprintf (stdout, c"arrDouble: Array[Double] -> arrDouble2: Array[Double]\n")
    fprintf (stdout, c"arrDouble\n")    
    dump(arrDouble)
    
    val arrDouble2 = new Array[Double] (len * 2)
    
    scala.Array.copy (arrDouble, 0, arrDouble2, 5, 10)
    
    fprintf (stdout, c"arrDouble2\n")    
    dump(arrDouble2)

    fprintf (stdout, c"arrDouble -> arrDouble without overlap (1/2)\n")
    scala.Array.copy (arrDouble, 0, arrDouble, 5, 5)      
    fprintf (stdout, c"arrDouble\n")
    dump(arrDouble)
    
    fprintf (stdout, c"arrDouble -> arrDouble without overlap (2/2)\n")
    scala.Array.copy (arrDouble, 6, arrDouble, 4, 2)      
    fprintf (stdout, c"arrDouble\n")
    dump(arrDouble)

    fprintf (stdout, c"arrDouble -> arrDouble with overlap -> backward copy\n")
    init (arrDouble)
    fprintf (stdout, c"arrDouble before\n")
    dump(arrDouble)
    scala.Array.copy (arrDouble, 0, arrDouble, 2, 6)      
    fprintf (stdout, c"arrDouble after\n")
    dump(arrDouble)

    fprintf (stdout, c"arrDouble -> arrDouble with overlap -> forward copy\n")
    init (arrDouble)
    fprintf (stdout, c"arrDouble before\n")
    dump(arrDouble)
    scala.Array.copy (arrDouble, 2, arrDouble, 0, 6)      
    fprintf (stdout, c"arrDouble after\n")
    dump(arrDouble)
  }
  
  def testcopyBoolean () {
    fprintf (stdout, c"\ntest Array Boolean\n")

    def init (arr: Array[Boolean]) = {    
      arr(0)=true
      arr(1)=false
      arr(2)=true
      arr(3)=true
      arr(4)=false
    
      arr(5)=false
      arr(6)=true
      arr(7)=false
      arr(8)=false
      arr(9)=true
    }

    
    val len = 10
    
    val arrBoolean = new Array[Boolean] (len)
    init (arrBoolean)
    
        
    fprintf (stdout, c"arrBoolean: Array[Boolean] -> arrBoolean2: Array[Boolean]\n")
    fprintf (stdout, c"arrBoolean\n")    
    dump(arrBoolean)
    
    val arrBoolean2 = new Array[Boolean] (len * 2)
    
    scala.Array.copy (arrBoolean, 0, arrBoolean2, 5, 10)
    
    fprintf (stdout, c"arrBoolean2\n")    
    dump(arrBoolean2)

    fprintf (stdout, c"arrBoolean -> arrBoolean without overlap (1/2)\n")
    scala.Array.copy (arrBoolean, 0, arrBoolean, 5, 5)      
    fprintf (stdout, c"arrBoolean\n")
    dump(arrBoolean)
    
    fprintf (stdout, c"arrBoolean -> arrBoolean without overlap (2/2)\n")
    scala.Array.copy (arrBoolean, 6, arrBoolean, 4, 2)      
    fprintf (stdout, c"arrBoolean\n")
    dump(arrBoolean)

    fprintf (stdout, c"arrBoolean -> arrBoolean with overlap -> backward copy\n")
    init (arrBoolean)
    fprintf (stdout, c"arrBoolean before\n")
    dump(arrBoolean)
    scala.Array.copy (arrBoolean, 0, arrBoolean, 2, 6)      
    fprintf (stdout, c"arrBoolean after\n")
    dump(arrBoolean)

    fprintf (stdout, c"arrBoolean -> arrBoolean with overlap -> forward copy\n")
    init (arrBoolean)
    fprintf (stdout, c"arrBoolean before\n")
    dump(arrBoolean)
    scala.Array.copy (arrBoolean, 2, arrBoolean, 0, 6)      
    fprintf (stdout, c"arrBoolean after\n")
    dump(arrBoolean)
  }
  
  /*
  def testcopyChar () {
    fprintf (stdout, c"\ntestArrayChar\n")
    
    val len = 10
    
    val arrChar = new Array[Char] (len)
    
    arrChar (0) = 'A'
    arrChar (1) = 'B'
    arrChar (2) = 'C'
    arrChar (3) = 'D'
    arrChar (4) = 'E'
    arrChar (5) = 'F'
    arrChar (6) = 'G'
    arrChar (7) = 'H'
    arrChar (8) = 'I'
    arrChar (9) = 'J'
    
    
    fprintf (stdout, c"arrChar\n")    
    dump(arrChar)
    
    val arrChar2 = new Array[Char] (len * 2)
    
    scala.Array.copy (arrChar, 0, arrChar2, 5, 10)
    
    fprintf (stdout, c"arrChar2\n")    
    dump(arrChar2)
    
    scala.Array.copy (arrChar, 0, arrChar, 5, 5)      
    fprintf (stdout, c"arrChar after copy without overlap\n")
    dump(arrChar)
    
    scala.Array.copy (arrChar, 6, arrChar, 4, 2)      
    fprintf (stdout, c"arrChar after copy without overlap\n")
    dump(arrChar)
    
    // <<<    
    scala.Array.copy (arrInt, 4, arrInt, 5, 2)      
    fprintf (stdout, c"Fail, src == dst with overlap\n")

    scala.Array.copy (null, 0, arrInt2, 5, 2)      
    fprintf (stdout, c"Fail, src null\n")
    
    scala.Array.copy (arrInt, 0, null, 5, -2)      
    fprintf (stdout, c"Fail, src null\n")
    
    
    // compiler error 
    // val i = 2
    // val j = 3
    
    //
    val i = new Vec(1.0, 1)
    val j = new Vec(2.0, 2)
    scala.Array.copy (i, 0, j, 5, -1)      
    fprintf (stdout, c"Fail, no arrays\n")
    
    val arrObject = new Array[String] (len)    
    scala.Array.copy (arrInt, 0, arrObject, 5, -1)      
    fprintf (stdout, c"Fail, diferrent type of array\n")
    
    scala.Array.copy (arrInt, 0, arrInt2, 5, -1)      
    fprintf (stdout, c"Fail, len negative\n")
    
    scala.Array.copy (arrInt, 0, arrInt2, 15, 10)      
    fprintf (stdout, c"Fail, destPos + len > dest.length\n")
    
    scala.Array.copy (arrInt, 5, arrInt2, 5, 10)      
    fprintf (stdout, c"Fail, fromPos + len > from.length\n")
    
    scala.Array.copy (arrInt, 0, arrInt2, -1, 10)      
    fprintf (stdout, c"Fail, toPos negative\n")
    
    scala.Array.copy (arrInt, -1, arrInt2, 5, 10)      
    fprintf (stdout, c"Fail, src fromPos negative\n")
    // >>> 
  }
  */
  
  def testcopyByte () {      
    fprintf (stdout, c"\ntest copy array[Byte]\n")
    
    def init(arr: Array[Byte]) = {
      var c = 0
    
      arr (0) = 'A'.toByte
      arr (1) = 'B'.toByte
      arr (2) = 'C'.toByte
      arr (3) = 'D'.toByte
      arr (4) = 'E'.toByte
      arr (5) = 'F'.toByte
      arr (6) = 'G'.toByte
      arr (7) = 'H'.toByte
      arr (8) = 'I'.toByte
      arr (9) = 'J'.toByte
    }
    
    val len = 10
    
    val arrByte = new Array[Byte] (len)
    init (arrByte)
    
        
    fprintf (stdout, c"arrByte: Array[Byte] -> arrByte2: Array[Byte]\n")
    fprintf (stdout, c"arrByte\n")    
    dump(arrByte)
    
    val arrByte2 = new Array[Byte] (len * 2)
    
    scala.Array.copy (arrByte, 0, arrByte2, 5, 10)
    
    fprintf (stdout, c"arrByte2\n")    
    dump(arrByte2)

    fprintf (stdout, c"arrByte -> arrByte without overlap (1/2)\n")
    scala.Array.copy (arrByte, 0, arrByte, 5, 5)      
    fprintf (stdout, c"arrByte\n")
    dump(arrByte)
    
    fprintf (stdout, c"arrByte -> arrByte without overlap (2/2)\n")
    scala.Array.copy (arrByte, 6, arrByte, 4, 2)      
    fprintf (stdout, c"arrByte\n")
    dump(arrByte)

    fprintf (stdout, c"arrByte -> arrByte with overlap -> backward copy\n")
    init (arrByte)
    fprintf (stdout, c"arrByte before\n")
    dump(arrByte)
    scala.Array.copy (arrByte, 0, arrByte, 2, 6)      
    fprintf (stdout, c"arrByte after\n")
    dump(arrByte)

    fprintf (stdout, c"arrByte -> arrByte with overlap -> forward copy\n")
    init (arrByte)
    fprintf (stdout, c"arrByte before\n")
    dump(arrByte)
    scala.Array.copy (arrByte, 2, arrByte, 0, 6)      
    fprintf (stdout, c"arrByte after\n")
    dump(arrByte)
  }
  
  
  def testcopyInt () {
    fprintf (stdout, c"\ntest copy array[Int]\n")
    
    def init(arr: Array[Int]) = {
      var c = 0
    
      while (c < arr.length) {
        arr (c) = c
      
        c += 1
      }         
    }
    
    val len = 10
    
    val arrInt = new Array[Int] (len)
    init (arrInt)
    // val arrJavaInteger = new Array[java.lang.Integer] (10)
    
        
    fprintf (stdout, c"arrInt: Array[Int] -> arrInt2: Array[Int]\n")
    fprintf (stdout, c"arrInt\n")    
    dump(arrInt)
    
    val arrInt2 = new Array[Int] (len * 2)
    
    scala.Array.copy (arrInt, 0, arrInt2, 5, 10)
    
    fprintf (stdout, c"arrInt2\n")    
    dump(arrInt2)

    fprintf (stdout, c"arrInt -> arrInt without overlap (1/2)\n")
    scala.Array.copy (arrInt, 0, arrInt, 5, 5)      
    fprintf (stdout, c"arrInt\n")
    dump(arrInt)
    
    fprintf (stdout, c"arrInt -> arrInt without overlap (2/2)\n")
    scala.Array.copy (arrInt, 6, arrInt, 4, 2)      
    fprintf (stdout, c"arrInt\n")
    dump(arrInt)

    fprintf (stdout, c"arrInt -> arrInt with overlap -> backward copy\n")
    init (arrInt)
    fprintf (stdout, c"arrInt before\n")
    dump(arrInt)
    scala.Array.copy (arrInt, 0, arrInt, 2, 6)      
    fprintf (stdout, c"arrInt after\n")
    dump(arrInt)

    fprintf (stdout, c"arrInt -> arrInt with overlap -> forward copy\n")
    init (arrInt)
    fprintf (stdout, c"arrInt before\n")
    dump(arrInt)
    scala.Array.copy (arrInt, 2, arrInt, 0, 6)      
    fprintf (stdout, c"arrInt after\n")
    dump(arrInt)

    //
    // from now on, exceptions are expected 
    //
    
    // null's
    
    fprintf (stdout, c"src null\n")
    try {
      scala.Array.copy (null, 0, arrInt2, 5, 2)      
      fprintf (stdout, c"-> fail\n")
    }
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
    
    fprintf (stdout, c"dest null\n")
    try {
      scala.Array.copy (arrInt, 0, null, 5, -2)      
      fprintf (stdout, c"-> fail\n")
    }
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
    
    // test boundaries
    
    fprintf (stdout, c"length negative\n")
    try {
      scala.Array.copy (arrInt, 0, arrInt2, 5, -1)
      fprintf (stdout, c"-> fail\n")
    }
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
    
    fprintf (stdout, c"destPos + len > dest.length\n")    
    try {
      scala.Array.copy (arrInt, 0, arrInt2, 15, 10)
      fprintf (stdout, c"-> fail\n")
    }    
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
  
    fprintf (stdout, c"fromPos + len > from.length\n")
    try {
      scala.Array.copy (arrInt, 5, arrInt2, 5, 10)      
      fprintf (stdout, c"-> fail\n")
    }    
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
  
    fprintf (stdout, c"toPos negative\n")
    try {
      scala.Array.copy (arrInt, 0, arrInt2, -1, 10)
      fprintf (stdout, c"-> fail\n")
    }
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }

    fprintf (stdout, c"fromPos negative\n")
    try {    
      scala.Array.copy (arrInt, -1, arrInt2, 5, 10)
      fprintf (stdout, c"-> fail\n")
    }      
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
    
    // invalid copy ()
    fprintf (stdout, c"Different type of array\n")
    try {
      val arrObject = new Array[String] (len)    
      scala.Array.copy (arrInt, 0, arrObject, 5, -1)
      fprintf (stdout, c"-> fail\n")
    }      
    catch {
      case th: Throwable => fprintf (stdout, c"-> exception\n")         
    }
    
    /*
     Not implemented yet
    
    fprintf (stdout, c"arrInt: Array[Int] -> arrObj: Array[Any]\n")
    val arrObj = new Array[Any] (10)
    scala.Array.copy (arrInt, 0, arrObj, 0, 10)      
    fprintf (stdout, c"arrObj\n")
    dumpObjAsInt(arrObj)
    
    fprintf (stdout, c"arrJavaInteger: Array[java.lang.Integer] -> arrInt: Array[Int]\n")
    fprintf (stdout, c"arrJavaInteger\n")
    dumpJavaLangInteger(arrJavaInteger)
    val arrInt3 = new Array[Int] (len * 2)    
    // fprintf (stdout, c"NOT YET\n")
    scala.Array.copy (arrJavaInteger, 0, arrInt3, 0, 10)      
    fprintf (stdout, c"arrInt\n")
    dump(arrInt3)
    */
    
    /*    
    
    // compiler error 
    // val i = 2
    // val j = 3
    
    //
    val i = new Vec(1.0, 1)
    val j = new Vec(2.0, 2)
    scala.Array.copy (i, 0, j, 5, -1)      
    fprintf (stdout, c"Fail, no arrays\n")
    
    */
    
  }
  
  def testClone () = {
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
  
  /*
   * helper methods
   */
    
  def dump (arr : Array [Byte]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      fprintf (stdout, c"(%d -> %d)", c, arr (c))
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }

  
  def dump (arr : Array [Char]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      fprintf (stdout, c"(%d -> %c)", c, arr (c))
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }

  def dump (arr : Array [Boolean]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      fprintf (stdout, c"(%d -> %d)", c, arr (c))
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }

  def dump (arr : Array [Double]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      fprintf (stdout, c"(%d -> %f)", c, arr (c))
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }

  def dump (arr : Array [Int]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      fprintf (stdout, c"(%d -> %d)", c, arr (c))
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }
  
  def dumpJavaLangInteger (arr : Array [java.lang.Integer]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      
      val value: Int = if (arr(c) == null) -1 else arr (c).intValue()
      
      fprintf (stdout, c"(%d -> %d)", c, value)
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }
  def dumpObjAsInt (arr : Array [Any]) {
    var c = 0
    
    fprintf (stdout, c"len: %d\n", arr.length)
    
    while (c < arr.length) {
      
      val value: Int = if (arr(c) == null) -1 else arr (c).asInstanceOf[java.lang.Integer].intValue()
      
      fprintf (stdout, c"(%d -> %d)", c, value)
      
      if (c == arr.length - 1)
        fprintf (stdout, c"\n")
      else
        fprintf (stdout, c", ")
      
      c += 1
    }
  }
}

