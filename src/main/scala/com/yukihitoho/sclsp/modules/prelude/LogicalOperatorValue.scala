package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidArgumentType, InvalidNumberOfArguments}
import com.yukihitoho.sclsp.evaluator._

trait LogicalOperatorValue extends PrimitiveProcedureValue {
  override protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value] =
    for {
      booleans <- arguments.foldLeft[Either[EvaluationError, List[Boolean]]](Right(List())) {
        case (Right(acc), BooleanValue(v)) => Right(v :: acc)
        case (Right(_), invalidTypeValue) => Left(InvalidArgumentType("boolean", invalidTypeValue, stackTrace.toList))
        case (Left(e), _) => Left(e)
      }.right.map(_.reverse)
      _ <- Either.cond(booleans.nonEmpty, (), InvalidNumberOfArguments(1, booleans.length, variadic = true, stackTrace.toList))
    } yield BooleanValue(booleans.tail.foldLeft(booleans.head)(calc))

  protected def calc(x: Boolean, y: Boolean): Boolean
}
