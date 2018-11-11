package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.DivisionByZero
import com.yukihitoho.sclsp.evaluator._

object Division extends VariableFactory {
  private val variable = BuiltinVariable("/", new ArithmeticOperatorValue {
    override protected def additionalCondition(numbers: List[Double], stackTrace: StackTrace): Either[EvaluationError, Unit] =
      Either.cond(!numbers.tail.contains(0.0), (), DivisionByZero(stackTrace.toList))
    override protected def calc(x: Double, y: Double): Double = x / y
  })

  override def create(): Variable = variable
}
