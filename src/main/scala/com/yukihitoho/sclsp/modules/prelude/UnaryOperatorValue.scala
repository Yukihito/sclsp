package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidNumberOfArguments
import com.yukihitoho.sclsp.evaluator.{EvaluationError, PrimitiveProcedureValue, StackTrace, Value}

trait UnaryOperatorValue extends PrimitiveProcedureValue {
  override protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value] =
    for {
      _ <- Either.cond(arguments.length == 1, (), InvalidNumberOfArguments(1, arguments.length, variadic = false, stackTrace.toList))
      value <- call(arguments.head, stackTrace)
    } yield value

  protected def call(argument: Value, stackTrace: StackTrace): Either[EvaluationError, Value]
}
