package com.yukihitoho.sclsp.evaluator

trait EvaluationError {
  val stackTrace: List[Call]
}

object EvaluationError {
  case class UnboundVariable(symbol: SymbolValue, override val stackTrace: List[Call]) extends EvaluationError
  case class InvalidNumberOfArguments(expected: Int, actual: Int, variadic: Boolean, override val stackTrace: List[Call]) extends EvaluationError
  case class InvalidProcedureCall(node: Value, override val stackTrace: List[Call]) extends EvaluationError
  case class InvalidArgumentType(expectedType: String, actualValue: Value, override val stackTrace: List[Call]) extends EvaluationError
  case class DivisionByZero(override val stackTrace: List[Call]) extends EvaluationError
  case class InvalidSyntax(pair: PairValue, override val stackTrace: List[Call]) extends EvaluationError
  case class Exit(code: Int, override val stackTrace: List[Call]) extends EvaluationError
}
