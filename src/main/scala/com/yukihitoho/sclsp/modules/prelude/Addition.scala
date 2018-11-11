package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object Addition extends VariableFactory {
  private val variable = BuiltinVariable("+", new ArithmeticOperatorValue {
    override protected def calc(x: Double, y: Double): Double = x + y
  })

  override def create(): Variable = variable
}
