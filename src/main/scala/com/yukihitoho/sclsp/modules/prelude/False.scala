package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object False extends VariableFactory {
  private val variable = BuiltinVariable("#f", BooleanValue(false))

  override def create(): Variable = variable
}
