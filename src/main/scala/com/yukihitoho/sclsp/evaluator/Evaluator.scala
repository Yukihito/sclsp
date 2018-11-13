package com.yukihitoho.sclsp.evaluator

import com.yukihitoho.sclsp.ast.{Node, NodePair, NumberLiteral, StringLiteral, Symbol}

trait Evaluator {
  import EvaluationError._
  def stackTrace: StackTrace

  def evaluate(node: Node, environment: Environment): Either[EvaluationError, Value] =
    evaluate(nodeToValue(node), environment)

  def evaluate(value: Value, environment: Environment): Either[EvaluationError, Value] =
    value match {
      case symbol: SymbolValue => environment.find(symbol).map(_.value).toRight(UnboundVariable(symbol, stackTrace.toList))
      case pair: PairValue => call(pair, environment)
      case v => Right(v)
    }

  private def call(pair: PairValue, environment: Environment): Either[EvaluationError, Value] =
    for {
      operator <- evaluate(pair.car, environment)
      result <- operator match {
        case callable: CallableValue => stackTrace.trace(Call(pair.car, callable)) {
          callable.call(pair, environment, this)
        }
        case _ =>
          Left(InvalidProcedureCall(pair, operator, stackTrace.toList))
      }
    } yield result

  private def nodeToValue(node: Node): Value = node match {
    case StringLiteral(value, _) => StringValue(value)
    case NumberLiteral(value, _) => NumberValue(value)
    case Symbol("#nil", _) => NilValue
    case Symbol("#t", _) => BooleanValue(true)
    case Symbol("#f", _) => BooleanValue(false)
    case Symbol(value, position) => SymbolValue(value, position)
    case NodePair(car, cdr, position) => PairValue(nodeToValue(car), nodeToValue(cdr), Some(position))
  }
}
