package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidArgumentType
import com.yukihitoho.sclsp.evaluator._

object Cdr extends VariableFactory {
  private val variable = BuiltinVariable("cdr", new UnaryOperatorValue {
    override protected def call(argument: Value, stackTrace: StackTrace): Either[EvaluationError, Value] =
      for {
        cdr <- argument match {
          case PairValue(_, cdr, _) => Right(cdr)
          case invalidTypeValue => Left(InvalidArgumentType("pair", invalidTypeValue, stackTrace.toList))
        }
      } yield cdr
  })

  override def create(): Variable = variable
}
