package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object Nil extends VariableFactory {
  private val variable = BuiltinVariable("#nil", NilValue)

  override def create(): Variable = variable
}
