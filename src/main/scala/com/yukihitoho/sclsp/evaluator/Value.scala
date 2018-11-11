package com.yukihitoho.sclsp.evaluator

import com.yukihitoho.sclsp.ast.Position
import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidNumberOfArguments, InvalidProcedureCall}

sealed trait Value {
  def valueEquals(other: Value): Boolean = this == other
}

trait AtomValue extends Value

case class StringValue(value: String) extends AtomValue

case class NumberValue(value: Double) extends AtomValue

case class BooleanValue(value: Boolean) extends AtomValue

case object NilValue extends AtomValue

case class SymbolValue(value: String, position: Position) extends AtomValue {
  override def valueEquals(other: Value): Boolean = other match {
    case symbol: SymbolValue => value == symbol.value
    case _ => false
  }
}

sealed trait CallableValue extends AtomValue {
  def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value]
}

trait ProcedureValue extends CallableValue {
  override def call(pair: PairValue, environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
    for {
      pairs <- pair.toList.toRight(InvalidProcedureCall(pair, evaluator.stackTrace.toList))
      arguments <- evaluateList(pairs, environment, evaluator)
      result <- call(arguments, environment, evaluator)
    } yield result

  protected def call(arguments: List[Value], environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value]

  private def evaluateList(pairs: List[Value], environment: Environment, evaluator: Evaluator): Either[EvaluationError, List[Value]] =
    pairs.tail.foldLeft[Either[EvaluationError, List[Value]]](Right(List())) {
      case (Right(acc), n) => evaluator.evaluate(n, environment).right.map(_ :: acc)
      case (Left(e), _) => Left(e)
    }.right.map(_.reverse)
}

case class CompoundProcedureValue(
  parameters: Seq[SymbolValue],
  body: Value,
  environment: Environment,
  position: Position
) extends ProcedureValue {
  override protected def call(arguments: List[Value], environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
    if (parameters.length != arguments.length) {
      Left(InvalidNumberOfArguments(parameters.length, arguments.length, variadic = false, evaluator.stackTrace.toList))
    } else {
      val variables = parameters.zip(arguments).map { case (param, arg) => UserDefinedVariable(param, arg) }
      evaluator.evaluate(body, environment.extend(variables))
    }
}

trait PrimitiveProcedureValue extends ProcedureValue {
  override protected def call(arguments: List[Value], environment: Environment, evaluator: Evaluator): Either[EvaluationError, Value] =
    call(arguments, evaluator.stackTrace)

  protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value]
}

trait SpecialFormValue extends CallableValue

case class PairValue(car: Value, cdr: Value, position: Option[Position]) extends Value {
  override def valueEquals(other: Value): Boolean = other match {
    case pair: PairValue => (pair.car valueEquals car) && (pair.cdr valueEquals cdr)
    case _ => false
  }

  def toList: Option[List[Value]] = cdr match {
    case pair: PairValue =>
      pair.toList.map(car :: _)
    case NilValue =>
      Some(List(car))
    case _ =>
      None
  }
}
