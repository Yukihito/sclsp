package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object And extends VariableFactory {
  private val variable = BuiltinVariable(new LogicalOperatorValue {
    override protected def calc(x: Boolean, y: Boolean): Boolean = x && y

    override def builtinSymbol: String = "and"
  })

  override def create(): Variable = variable
}
