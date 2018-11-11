package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object `Atom?` extends VariableFactory {
  private val variable = BuiltinVariable("atom?", new UnaryOperatorValue {
    override protected def call(argument: Value, stackTrace: StackTrace): Either[EvaluationError, Value] =
      Right(BooleanValue(argument.isInstanceOf[AtomValue]))
  })

  override def create(): Variable = variable
}

