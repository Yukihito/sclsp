package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidNumberOfArguments
import com.yukihitoho.sclsp.evaluator.{EvaluationError, PrimitiveProcedureValue, StackTrace, Value}

trait BinaryOperatorValue extends PrimitiveProcedureValue {
  override protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value] =
    for {
      _ <- Either.cond(arguments.length == 2, (), InvalidNumberOfArguments(2, arguments.length, variadic = false, stackTrace.toList))
      value <- call(arguments.head, arguments(1))
    } yield value

  protected def call(x: Value, y: Value): Either[EvaluationError, Value]
}

