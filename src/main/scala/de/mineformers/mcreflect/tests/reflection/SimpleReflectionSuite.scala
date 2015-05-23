package de.mineformers.mcreflect.tests.reflection

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import de.mineformers.mcreflect.introspection._
import de.mineformers.mcreflect.introspection.RefType.typeOf
import de.mineformers.mcreflect.tests.TestData
import org.scalatest.junit.JUnitRunner

/**
 * @author Marvin
 */

@RunWith(classOf[JUnitRunner])
class SimpleReflectionSuite extends FunSuite {
  var refType: RefType[TestData] = _
  var refCtor: RefConstructor[TestData] = _
  var refField: RefField[TestData, Int] = _
  var refFieldUnlocked: RefField[TestData, Int] = _
  var refMethod: RefMethod[TestData, String] = _
  
  test("introspection on example type") {
    refType = typeOf[TestData]
    assert(refType != null)
    refCtor = refType.constructor()
    refField = refType.fields.simpleTestField[Int]
    refFieldUnlocked = refField.unlocked
    refMethod = refType.methods.simpleTestMethod[String]
    println(refField)
    
    assert(refCtor != null)
    assert(refField != null)
    assert(refFieldUnlocked != null)
    assert(refMethod != null)
  }
  
  test("instantiation using reflective constructor") {
    val result = refCtor()
    
    assert(result != null)
  }
  
  test("locked field access using reflection") {
    val data = new TestData
    intercept[IllegalAccessException] {
      val result = refField.value(data)
    }
    assert(data.simpleTestField == 0)
  }
  
  test("locked field modification using reflection") {
    val data = new TestData
    val oldValue = data.simpleTestField
    intercept[IllegalAccessException] {
      refField(data) = 1
    }
    val newValue = data.simpleTestField
    
    assert(newValue == oldValue)
  }
  
  test("unlocked field access using reflection") {
    val data = new TestData
    val result = refFieldUnlocked.value(data)
    assert(result == 0)
  }
  
  test("unlocked field modification using reflection") {
    val data = new TestData
    refFieldUnlocked(data) = 1
    val newValue = data.simpleTestField
    
    assert(newValue == 1)
  }
  
  test("reassure locked field access using reflection") {
    val data = new TestData
    intercept[IllegalAccessException] {
      val result = refField.value(data)
    }
    assert(data.simpleTestField == 0)
  }
  
  test("reassure locked field modification using reflection") {
    val data = new TestData
    val oldValue = data.simpleTestField
    intercept[IllegalAccessException] {
      refField(data) = 1
    }
    val newValue = data.simpleTestField
    
    assert(newValue == oldValue)
  }
  
  test("method invocation using reflection") {
    val data = new TestData
    val result = refMethod(data)("xyz")
    
    assert(result == "xyz")
  }
}