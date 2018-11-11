package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.BooleanValue
import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidNumberOfArguments
import com.yukihitoho.sclsp.interpreter.InterpretingError.EvaluationError
import org.scalatest._

class `Eq?OperatorSpec` extends FlatSpec with Matchers {
  "The eq? operator" should "return the equivalence of arguments" in new WithInterpreter {
    interpreter.interpret("(eq? 1 1") should be (Right(BooleanValue(true)))
    interpreter.interpret("(eq? 1 2") should be (Right(BooleanValue(false)))
    interpreter.interpret("(eq? #t #t") should be (Right(BooleanValue(true)))
    interpreter.interpret("(eq? #f #f") should be (Right(BooleanValue(true)))
    interpreter.interpret("(eq? #t #f") should be (Right(BooleanValue(false)))
    interpreter.interpret("""(eq? "hoge" "hoge")""") should be (Right(BooleanValue(true)))
    interpreter.interpret("""(eq? "hoge" "fuga")""") should be (Right(BooleanValue(false)))
    interpreter.interpret("""(eq? 1 "1")""") should be (Right(BooleanValue(false)))
    interpreter.interpret("(eq? lambda lambda)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(eq? lambda quote)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(eq? + +)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(eq? + -)") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(eq?)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(eq? 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(eq? 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }
}
