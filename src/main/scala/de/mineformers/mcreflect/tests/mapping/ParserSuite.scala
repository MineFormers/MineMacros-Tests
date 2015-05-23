package de.mineformers.mcreflect.tests.mapping

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.mineformers.mcreflect.mapping._

@RunWith(classOf[JUnitRunner])
class ParserSuite extends FunSuite {

  test("parse file") {
    val parser = new SrgParser
    val result = Mappings.fromFile("mcp-srg.srg")
    assert(result != null)
  }

}