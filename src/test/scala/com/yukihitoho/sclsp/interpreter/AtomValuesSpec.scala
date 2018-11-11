package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidNumberOfArguments
import com.yukihitoho.sclsp.evaluator.{BooleanValue, NilValue, NumberValue, StringValue}
import com.yukihitoho.sclsp.interpreter.InterpretingError.EvaluationError
import com.yukihitoho.sclsp.modules.prelude.{Addition, Lambda}
import org.scalatest._

class AtomValuesSpec extends FlatSpec with Matchers {
  "Atom values" should "be interpreted correctly" in new WithInterpreter {
    interpreter.interpret("#nil") should be (Right(NilValue))
    interpreter.interpret("()") should be (Right(NilValue))
    interpreter.interpret("1") should be (Right(NumberValue(1)))
    interpreter.interpret("#t") should be (Right(BooleanValue(true)))
    interpreter.interpret(""""hoge"""") should be (Right(StringValue("hoge")))
    interpreter.interpret("lambda") should be (Right(Lambda.create().value))
    interpreter.interpret("+") should be (Right(Addition.create().value))
  }

  "The atom? operator" should "return boolean that argument is atom or not" in new WithInterpreter {
    interpreter.interpret("(atom? 1)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(atom? #t)") should be (Right(BooleanValue(true)))
    interpreter.interpret("""(atom? "hoge")""") should be (Right(BooleanValue(true)))
    interpreter.interpret("(atom? lambda)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(atom? +)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(atom? (cons 1 2))") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 1"  in new WithInterpreter {
    interpreter.interpret("(atom?)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, false, _))) =>
    }
    interpreter.interpret("(atom? 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 2, false, _))) =>
    }
  }
}
