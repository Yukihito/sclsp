package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidArgumentType
import com.yukihitoho.sclsp.evaluator._

object Car extends VariableFactory {
  private val variable = BuiltinVariable(new UnaryOperatorValue {
    override protected def call(argument: Value, stackTrace: StackTrace): Either[EvaluationError, Value] =
      for {
        car <- argument match {
          case PairValue(car, _, _) => Right(car)
          case invalidTypeValue => Left(InvalidArgumentType("pair", invalidTypeValue, stackTrace.toList))
        }
      } yield car

    override def builtinSymbol: String = "car"
  })

  override def create(): Variable = variable
}
