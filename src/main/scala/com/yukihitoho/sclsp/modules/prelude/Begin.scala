package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidNumberOfArguments, InvalidSyntax}
import com.yukihitoho.sclsp.evaluator._

object Begin extends VariableFactory {
  private val variable = BuiltinVariable("begin", new SpecialFormValue {
    override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
      for {
        result <- pair match {
          case PairValue(_, body: PairValue, _) =>
            for {
              values <- body.toList.toRight(InvalidSyntax(body, evaluator.stackTrace.toList))
              _ <- Either.cond(values.nonEmpty, (), InvalidNumberOfArguments(1, values.length, variadic = true, evaluator.stackTrace.toList))
              lastResult <- evaluateValues(values, environment, evaluator)
            } yield lastResult
          case _ => Left(InvalidSyntax(pair, evaluator.stackTrace.toList))
        }
      } yield result
  })

  private def evaluateValues(
    values: List[Value],
    environment: Environment,
    evaluator: Evaluator
  ): Either[EvaluationError, Value] = values.foldLeft[Either[EvaluationError, Value]](Right(NilValue)) {
    case (Right(_), value) => evaluator.evaluate(value, environment)
    case (Left(e), _) => Left(e)
  }

  override def create(): Variable = variable
}
