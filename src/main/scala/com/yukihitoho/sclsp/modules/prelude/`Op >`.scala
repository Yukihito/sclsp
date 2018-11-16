package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidArgumentType
import com.yukihitoho.sclsp.evaluator._

object `Op >` extends VariableFactory {
  private val variable = BuiltinVariable(new BinaryOperatorValue {
    override protected def call(x: Value, y: Value, stackTrace: StackTrace): Either[EvaluationError, Value] =
      for {
        numberX <- x match {
          case numberX: NumberValue => Right(numberX)
          case invalidTypeValue => Left(InvalidArgumentType("number", invalidTypeValue, stackTrace.toList))
        }
        numberY <- y match {
          case numberY: NumberValue => Right(numberY)
          case invalidTypeValue => Left(InvalidArgumentType("number", invalidTypeValue, stackTrace.toList))
        }
      } yield BooleanValue(numberX.value > numberY.value)

    override def builtinSymbol: String = ">"
  })

  override def create(): Variable = variable
}
