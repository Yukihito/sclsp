package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.EvaluationError.DivisionByZero
import com.yukihitoho.sclsp.evaluator.NumberValue
import com.yukihitoho.sclsp.interpreter.InterpretationError.EvaluationError
import org.scalatest._

class WhileOperatorSpec extends FlatSpec with Matchers {
  "The while operator" should "repeat body procedure" in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define i 0)
        |  (while (< i 3) (set! i (+ i 1)))
        |  i)
      """.stripMargin

    interpreter.interpret(src) should be (Right(NumberValue(3)))
  }

  it should "stop if an error occurred." in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define i 0)
        |  (while (< i 3) (/ i 0))
        |  i)
      """.stripMargin

    interpreter.interpret(src) should matchPattern {case Left(EvaluationError(DivisionByZero(_))) => }
  }
}
