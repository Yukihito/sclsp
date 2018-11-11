package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object Or extends VariableFactory {
  private val variable = BuiltinVariable("or", new LogicalOperatorValue {
    override protected def calc(x: Boolean, y: Boolean): Boolean = x || y
  })

  override def create(): Variable = variable
}
