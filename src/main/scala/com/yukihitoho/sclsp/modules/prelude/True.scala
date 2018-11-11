package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object True extends VariableFactory {
  private val variable = BuiltinVariable("#t", BooleanValue(true))

  override def create(): Variable = variable
}
