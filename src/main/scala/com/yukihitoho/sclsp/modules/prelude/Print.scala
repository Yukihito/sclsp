package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object Print extends VariableFactory {
  private val variable = BuiltinVariable(new UnaryOperatorValue {
    override protected def call(argument: Value, stackTrace: StackTrace): Either[EvaluationError, Value] = {
      // scalastyle:off
      println(argument)
      // scalastyle:on
      Right(NilValue)
    }

    override def builtinSymbol: String = "print"
  })

  override def create(): Variable = variable
}
