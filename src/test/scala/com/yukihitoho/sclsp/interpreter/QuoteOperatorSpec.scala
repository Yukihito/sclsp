package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.ast.Position
import com.yukihitoho.sclsp.evaluator.{NilValue, NumberValue, PairValue, SymbolValue}
import org.scalatest._

class QuoteOperatorSpec extends FlatSpec with Matchers {
  // scalastyle:off
  "The quote operator" should "not evaluate the argument" in new WithInterpreter {
    interpreter.interpret(
      """
        |(begin
        |  (define x 1)
        |  (quote x))
      """.stripMargin) should be (Right(SymbolValue("x", Position(4, 10))))

    interpreter.interpret("(quote (1 2))") should be (
      Right(PairValue(NumberValue(1), PairValue(NumberValue(2), NilValue, Some(Position(1, 8))), Some(Position(1, 8)))))

    interpreter.interpret("(quote (1 . 2))") should be (
      Right(PairValue(NumberValue(1), NumberValue(2), Some(Position(1, 8)))))
  }

  "A ' character" should "be evaluated as a quote operator" in new WithInterpreter {
    interpreter.interpret(
      """
        |(begin
        |  (define x 1)
        |  'x)
      """.stripMargin) should be (Right(SymbolValue("x", Position(4, 4))))

    interpreter.interpret("'(1 2)") should be (
      Right(PairValue(NumberValue(1), PairValue(NumberValue(2), NilValue, Some(Position(1, 2))), Some(Position(1, 2)))))

    interpreter.interpret("'(1 . 2)") should be (
      Right(PairValue(NumberValue(1), NumberValue(2), Some(Position(1, 2)))))
  }
  // scalastyle:on
}
