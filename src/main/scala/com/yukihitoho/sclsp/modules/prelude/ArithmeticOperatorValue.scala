package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidArgumentType, InvalidNumberOfArguments}
import com.yukihitoho.sclsp.evaluator._

trait ArithmeticOperatorValue extends PrimitiveProcedureValue {
  protected def calc(x: Double, y: Double): Double

  protected def additionalCondition(numbers: List[Double], stackTrace: StackTrace): Either[EvaluationError, Unit] = Right(())

  override protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value] =
    for {
      numbers <- arguments.foldLeft[Either[EvaluationError, List[Double]]] (Right(List())) {
        case (Right(acc), NumberValue(value)) => Right(value :: acc)
        case (Right(_), invalidTypeValue) => Left(InvalidArgumentType("number", invalidTypeValue, stackTrace.toList))
        case (Left(e), _) => Left(e)
      }.right.map(_.reverse)
      _ <- Either.cond(numbers.nonEmpty, (), InvalidNumberOfArguments(1, numbers.length, variadic = true, stackTrace.toList))
      _ <- additionalCondition(numbers, stackTrace)
    } yield NumberValue(numbers.tail.foldLeft(numbers.head)(calc))
}
