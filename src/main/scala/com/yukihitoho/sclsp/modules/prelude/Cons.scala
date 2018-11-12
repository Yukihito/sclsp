package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object Cons extends VariableFactory {
  private val variable = BuiltinVariable(new BinaryOperatorValue {
    override protected def call(x: Value, y: Value): Either[EvaluationError, Value] = Right(PairValue(x, y, None))

    override def builtinSymbol: String = "cons"
  })

  override def create(): Variable = variable
}

