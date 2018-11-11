package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidArgumentType, InvalidSyntax}
import com.yukihitoho.sclsp.evaluator._

object If extends VariableFactory {
  private val variable = BuiltinVariable("if", new SpecialFormValue {
    override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
      for {
        result <- pair match {
          case PairValue(_, PairValue(condition, PairValue(thenValue, PairValue(elseValue, NilValue, _), _), _), _) =>
            for {
              boolean <- evaluator.evaluate(condition, environment) match {
                case Right(BooleanValue(b)) => Right(b)
                case Right(invalidTypeValue) => Left(InvalidArgumentType("boolean", invalidTypeValue, evaluator.stackTrace.toList))
                case Left(e) => Left(e)
              }
              result <- if (boolean) {
                evaluator.evaluate(thenValue, environment)
              } else {
                evaluator.evaluate(elseValue, environment)
              }
            } yield result
          case _ => Left(InvalidSyntax(pair, evaluator.stackTrace.toList))
        }
      } yield result
  })

  override def create(): Variable = variable
}
