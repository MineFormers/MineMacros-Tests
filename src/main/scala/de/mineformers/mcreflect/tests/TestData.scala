package de.mineformers.mcreflect.tests

object TestData {
  object InnerData {
    class InnerMostData
  }
  
  class InnerData
}

class TestData(var mutableField: Int, final val immutableField: Double) {
  def this() = this(0, 0)
  
  var simpleTestField = 0

  def simpleTestMethod(x: String) = x
  
  def *(x: Float) = x.toInt
  
  def complexTestMethod(x: String): String = complexTestMethod(x, immutableField)
  
  def complexTestMethod(x: String, y: Double): String = x + ":" + y
}