package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidSyntax
import com.yukihitoho.sclsp.evaluator._

object Quote extends VariableFactory {
  private val variable = BuiltinVariable(new SpecialFormValue {
    override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
      for {
        value <- pair match {
          case PairValue(_, PairValue(car, NilValue, _), _) => Right(car)
          case _ => Left(InvalidSyntax(pair, evaluator.stackTrace.toList))
        }
      } yield value

    override def builtinSymbol: String = "quote"
  })

  override def create(): Variable = variable
}
