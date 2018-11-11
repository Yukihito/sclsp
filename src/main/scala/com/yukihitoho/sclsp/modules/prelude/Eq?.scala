package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object `Eq?` extends VariableFactory {
  private val variable = BuiltinVariable("eq?", new BinaryOperatorValue {
    override protected def call(x: Value, y: Value): Either[EvaluationError, Value] = Right(BooleanValue(x valueEquals y))
  })

  override def create(): Variable = variable
}
