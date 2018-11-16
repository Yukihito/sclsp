package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidArgumentType, InvalidSyntax}
import com.yukihitoho.sclsp.evaluator._

import scala.annotation.tailrec

object While extends VariableFactory {
  private val variable = BuiltinVariable(value = new SpecialFormValue {
    override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
      for {
        _ <- pair match {
          case PairValue(_, PairValue(condition, PairValue(body, NilValue, _), _), _) =>
            evaluateWhile(condition, body, evaluator, environment)
          case _ => Left(InvalidSyntax(pair, evaluator.stackTrace.toList))
        }
      } yield NilValue

    @tailrec
    private def evaluateWhile(condition: Value, body: Value, evaluator: Evaluator, environment: Environment): Either[EvaluationError, Boolean] = {
      val latestCondition = for {
        conditionValue <- evaluator.evaluate(condition, environment)
        conditionBoolean <- conditionValue match {
          case BooleanValue(bool) => Right(bool)
          case invalidTypeValue => Left(InvalidArgumentType("boolean", invalidTypeValue, evaluator.stackTrace.toList))
        }
        _ <- if (conditionBoolean) {
          for {
            _ <- evaluator.evaluate(body, environment)
          } yield ()
        } else {
          Right(())
        }
      } yield conditionBoolean

      latestCondition match {
        case Right(true) => evaluateWhile(condition, body, evaluator, environment)
        case other => other
      }
    }

    override def builtinSymbol: String = "while"
  })

  override def create(): Variable = variable
}
