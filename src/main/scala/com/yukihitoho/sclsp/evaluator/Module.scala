package com.yukihitoho.sclsp.evaluator

trait Module {
  def variableFactories: Seq[VariableFactory]
}
