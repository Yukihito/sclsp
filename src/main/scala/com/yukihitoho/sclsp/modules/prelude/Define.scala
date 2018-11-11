package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidSyntax
import com.yukihitoho.sclsp.evaluator._

object Define extends VariableFactory {
  private val variable = BuiltinVariable("define", new SpecialFormValue {
    override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
      for {
        result <- pair match {
          case PairValue(_, PairValue(symbol: SymbolValue, PairValue(body, NilValue, _), _), _) =>
            for {
              value <- evaluator.evaluate(body, environment)
            } yield {
              environment.store(UserDefinedVariable(symbol, value))
              symbol
            }
          case _ => Left(InvalidSyntax(pair, evaluator.stackTrace.toList))
        }
      } yield result
  })

  override def create(): Variable = variable
}
