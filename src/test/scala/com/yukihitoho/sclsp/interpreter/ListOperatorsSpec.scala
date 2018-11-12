package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.EvaluationError.InvalidNumberOfArguments
import com.yukihitoho.sclsp.evaluator.{NumberValue, PairValue}
import com.yukihitoho.sclsp.interpreter.InterpretationError.EvaluationError
import org.scalatest._

class ListOperatorsSpec extends FlatSpec with Matchers {
  "The cons operator" should "construct a cons cell" in new WithInterpreter {
    interpreter.interpret("(cons 1 2)") should be (Right(PairValue(NumberValue(1), NumberValue(2), None)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(cons)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(cons 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(cons 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  "The car operator" should "return the car field of the cons cell" in new WithInterpreter {
    interpreter.interpret("(car (cons 1 2))") should be (Right(NumberValue(1)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 1"  in new WithInterpreter {
    interpreter.interpret("(car)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, false, _))) =>
    }
    interpreter.interpret("(car 1 2)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 2, false, _))) =>
    }
  }

  "The cdr operator" should "return the cdr field of the cons cell" in new WithInterpreter {
    interpreter.interpret("(cdr (cons 1 2))") should be (Right(NumberValue(2)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 1"  in new WithInterpreter {
    interpreter.interpret("(cdr)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, false, _))) =>
    }
    interpreter.interpret("(cdr 1 2)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 2, false, _))) =>
    }
  }
}
