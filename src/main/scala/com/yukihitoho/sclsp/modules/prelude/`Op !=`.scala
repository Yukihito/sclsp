package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object `Op !=` extends VariableFactory {
  private val variable = BuiltinVariable(new BinaryOperatorValue {
    override protected def call(x: Value, y: Value, stackTrace: StackTrace): Either[EvaluationError, Value] = Right(BooleanValue(!(x valueEquals y)))

    override def builtinSymbol: String = "!="
  })

  override def create(): Variable = variable
}
