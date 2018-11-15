package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.EvaluationError.UnboundVariable
import com.yukihitoho.sclsp.evaluator.NumberValue
import com.yukihitoho.sclsp.evaluator.PairValue
import com.yukihitoho.sclsp.evaluator.SymbolValue
import com.yukihitoho.sclsp.interpreter.InterpretationError.EvaluationError
import org.scalatest._

class `Set!OperatorSpec` extends FlatSpec with Matchers {
  "The set! operator" should "update a variable" in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define x 0)
        |  (define y 2)
        |  (define f (lambda (arg) (begin
        |    (set! x arg)
        |    (define y 3)
        |    y)))
        |  (define z (f 1))
        |  (cons x (cons y (cons z #nil))))
      """.stripMargin
    interpreter.interpret(src).right.map(_.asInstanceOf[PairValue].toList.get) should be (
      Right(List(NumberValue(1), NumberValue(2), NumberValue(3))))
  }

  it should "return UnboundVariable if an unbound variable specified" in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define x 0)
        |  (define y 2)
        |  (define f (lambda (arg) (begin
        |    (set! unbound arg)
        |    (define y 3)
        |    y)))
        |  (define z (f 1))
        |  (cons x (cons y (cons z #nil))))
      """.stripMargin
    interpreter.interpret(src) should matchPattern {
      case Left(EvaluationError(UnboundVariable(SymbolValue("unbound", _), _))) =>
    }
  }
}
