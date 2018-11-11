package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.ast.Position
import com.yukihitoho.sclsp.evaluator.{Call, CompoundProcedureValue, SymbolValue}
import com.yukihitoho.sclsp.evaluator.EvaluationError.Exit
import com.yukihitoho.sclsp.interpreter.InterpretingError.EvaluationError
import org.scalatest._

class StackTraceSpec extends FlatSpec with Matchers{
  // scalastyle:off
  "A interpreter" should "provide a stack trace" in new WithInterpreter {
    val src: String =
      """
        |(begin
        |  (define f
        |    (lambda (n)
        |      (g n)))
        |  (define g
        |    (lambda (n)
        |      (exit n)))
        |  (f 0))
      """.stripMargin
    interpreter.interpret(src) should matchPattern  {
      case Left(EvaluationError(Exit(0, List(
        Call(SymbolValue("exit", Position(8, 8)), _),
        Call(SymbolValue("g", Position(5, 8)), CompoundProcedureValue(_, _, _, Position(7, 6))),
        Call(SymbolValue("f", Position(9, 4)), CompoundProcedureValue(_, _, _, Position(4, 6))),
        Call(SymbolValue("begin", Position(2, 2)), _)
      )))) =>
    }
  }
  // scalastyle:on
}
