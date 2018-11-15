package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidSyntax
import com.yukihitoho.sclsp.evaluator._

object Lambda extends VariableFactory {
  private val variable = BuiltinVariable(new SpecialFormValue {
    override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
      for {
        result <- pair match {
          case PairValue(SymbolValue(_, position), PairValue(paramsPair: PairValue, PairValue(body, NilValue, _), _), _) =>
            for {
              params <- collectParams(paramsPair, evaluator.stackTrace)
            } yield CompoundProcedureValue(params, body, environment, position)
          case _ => Left(InvalidSyntax(pair, evaluator.stackTrace.toList))
        }
      } yield result

    override def builtinSymbol: String = "lambda"
  })

  private def collectParams(pair: PairValue, stackTrace: StackTrace): Either[EvaluationError, List[SymbolValue]] =
    for {
      values <- pair.toList.toRight(InvalidSyntax(pair, stackTrace.toList))
      symbols <- values.foldLeft[Either[EvaluationError, List[SymbolValue]]](Right(List())) {
        case (Right(acc), s: SymbolValue) => Right(s :: acc)
        case (Right(_), _) => Left(InvalidSyntax(pair, stackTrace.toList))
        case (Left(e), _) => Left(e)
      }
    } yield symbols.reverse

  override def create(): Variable = variable
}
