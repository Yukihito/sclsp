package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidArgumentType
import com.yukihitoho.sclsp.evaluator._

object Exit extends VariableFactory {
  private val variable = BuiltinVariable("exit", new UnaryOperatorValue {
    override protected def call(argument: Value, stackTrace: StackTrace): Either[EvaluationError, Value] =
      for {
        _ <- argument match {
          case code: NumberValue => Left(EvaluationError.Exit(code.value.toInt, stackTrace.toList))
          case invalidTypeValue => Left(InvalidArgumentType("number", invalidTypeValue, stackTrace.toList))
        }
      } yield NilValue
  })

  override def create(): Variable = variable
}
