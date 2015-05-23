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
class ComplexReflectionSuite extends FunSuite {
  val MutableValue = 1
  val MutableChangedValue = 2
  val ImmutableValue = 3d
  val ImmutableChangedValue = 4d
  val GeneralX = "abc"
  val SpecificX = "xyz"
  val SpecificY = math.Pi

  var refType: RefType[TestData] = _
  var refCtor: RefConstructor[TestData] = _
  var refFieldMutable: RefField[TestData, Int] = _
  var refFieldMutableUnlocked: RefField[TestData, Int] = _
  var refFieldImmutable: RefField[TestData, Double] = _
  var refFieldImmutableUnlocked: RefField[TestData, Double] = _
  var refMethodGeneral: RefMethod[TestData, String] = _
  var refMethodSpecific: RefMethod[TestData, String] = _

  def acquireInstance = new TestData(MutableValue, ImmutableValue)

  test("introspection on example type") {
    refType = typeOf[TestData]
    assert(refType != null)
    refCtor = refType.constructor(typeOf[Int], typeOf[Double])
    refFieldMutable = refType.fields.mutableField[Int]
    refFieldMutableUnlocked = refFieldMutable.unlocked
    refFieldImmutable = refType.fields.immutableField[Double]
    refFieldImmutableUnlocked = refFieldImmutable.unlocked
    refMethodGeneral = refType.methods.complexTestMethod[String](typeOf[String])
    refMethodSpecific = refType.methods.complexTestMethod[String](typeOf[String], typeOf[Double])

    assert(refCtor != null)
    assert(refFieldMutable != null)
    assert(refFieldMutableUnlocked != null)
    assert(refFieldImmutable != null)
    assert(refFieldImmutableUnlocked != null)
    assert(refMethodGeneral != null)
    assert(refMethodSpecific != null)
  }

  test("instantiation using reflective constructor lacking parameters") {
    intercept[IllegalArgumentException] {
      refCtor()
    }
  }

  test("instantiation using reflective constructor") {
    val result = refCtor(MutableValue, ImmutableValue)

    assert(result != null)
    assert(result.mutableField == MutableValue)
    assert(result.immutableField == ImmutableValue)
  }

  // Mutable Field

  test("locked mutable field access using reflection") {
    val data = acquireInstance
    intercept[IllegalAccessException] {
      val result = refFieldMutable.value(data)
    }
    assert(data.mutableField == MutableValue)
  }

  test("locked mutable field modification using reflection") {
    val data = acquireInstance
    val oldValue = data.mutableField
    intercept[IllegalAccessException] {
      refFieldMutable(data) = MutableChangedValue
    }
    val newValue = data.mutableField

    assert(newValue == oldValue)
  }

  test("unlocked mutable field access using reflection") {
    val data = acquireInstance
    val result = refFieldMutableUnlocked.value(data)

    assert(result == MutableValue)
  }

  test("unlocked mutable field modification using reflection") {
    val data = acquireInstance
    refFieldMutableUnlocked(data) = MutableChangedValue
    val newValue = data.mutableField

    assert(newValue == MutableChangedValue)
  }

  test("reassure locked mutable field access using reflection") {
    val data = acquireInstance
    intercept[IllegalAccessException] {
      val result = refFieldMutable.value(data)
    }
    assert(data.mutableField == MutableValue)
  }

  test("reassure locked mutable field modification using reflection") {
    val data = acquireInstance
    val oldValue = data.mutableField
    intercept[IllegalAccessException] {
      refFieldMutable(data) = MutableChangedValue
    }
    val newValue = data.mutableField

    assert(newValue == oldValue)
  }

  // Immutable Field

  test("check immutable field immutability using reflection") {
    assert(refFieldImmutableUnlocked.mutable == false)
  }

  test("locked immutable field access using reflection") {
    val data = acquireInstance
    intercept[IllegalAccessException] {
      val result = refFieldImmutable.value(data)
    }
    assert(data.immutableField == ImmutableValue)
  }

  test("locked immutable field modification using reflection") {
    val data = acquireInstance
    val oldValue = data.immutableField
    intercept[IllegalAccessException] {
      refFieldImmutable(data) = ImmutableChangedValue
    }
    val newValue = data.immutableField

    assert(newValue == oldValue)
  }

  test("unlocked immutable field access using reflection") {
    val data = acquireInstance
    val result = refFieldImmutableUnlocked.value(data)

    assert(result == ImmutableValue)
  }

  test("unlocked field immutable modification using reflection") {
    val data = acquireInstance
    refFieldImmutableUnlocked(data) = ImmutableChangedValue
    val newValue = data.immutableField

    assert(newValue == ImmutableChangedValue)
  }

  test("reassure locked immutable field access using reflection") {
    val data = acquireInstance
    intercept[IllegalAccessException] {
      val result = refFieldImmutable.value(data)
    }
    assert(data.immutableField == ImmutableValue)
  }

  test("reassure locked immutable field modification using reflection") {
    val data = acquireInstance
    val oldValue = data.immutableField
    intercept[IllegalAccessException] {
      refFieldImmutable(data) = ImmutableChangedValue
    }
    val newValue = data.immutableField

    assert(newValue == oldValue)
  }
}