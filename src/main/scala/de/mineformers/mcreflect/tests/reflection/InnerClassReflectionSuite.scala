package de.mineformers.mcreflect.tests.reflection

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.mineformers.mcreflect.introspection._
import RefType.typeOf
import de.mineformers.mcreflect.tests.TestData

@RunWith(classOf[JUnitRunner])
class InnerClassReflectionSuite extends FunSuite {

  test("acquire inner class type") {
    val result = typeOf[TestData.InnerData.InnerMostData]
    assert(result.runtimeClass != null)
  }
  
}