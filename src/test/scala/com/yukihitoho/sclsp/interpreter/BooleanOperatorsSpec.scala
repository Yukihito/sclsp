package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.{BooleanValue, NumberValue, StringValue}
import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidArgumentType, InvalidNumberOfArguments}
import com.yukihitoho.sclsp.interpreter.InterpretingError.EvaluationError
import org.scalatest._

class BooleanOperatorsSpec extends FlatSpec with Matchers {
  "The and operator" should "return the conjunction of arguments" in new WithInterpreter {
    interpreter.interpret("(and #t)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(and #f)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(and #t #t #t)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(and #t #f #t)") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments equals 0"  in new WithInterpreter {
    interpreter.interpret("(and)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, true, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a boolean"  in new WithInterpreter {
    interpreter.interpret("""(and #t "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(and "hoge" #t)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(and 1 #t)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", NumberValue(1), _))) =>
    }
  }

  "The or operator" should "return the disjunction of arguments" in new WithInterpreter {
    interpreter.interpret("(or #t)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(or #f)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(or #t #f #t)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(and #f #f #f)") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments equals 0"  in new WithInterpreter {
    interpreter.interpret("(or)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, true, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a boolean"  in new WithInterpreter {
    interpreter.interpret("""(or #t "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(or "hoge" #t)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(or 1 #t)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", NumberValue(1), _))) =>
    }
  }

  "The not operator" should "return negation of the argument" in new WithInterpreter {
    interpreter.interpret("(not #t)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(not #f)") should be (Right(BooleanValue(true)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 1"  in new WithInterpreter {
    interpreter.interpret("(not)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, false, _))) =>
    }
    interpreter.interpret("(not #t #t)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 2, false, _))) =>
    }
  }

  it should "return InvalidArgumentType if the argument is not a boolean"  in new WithInterpreter {
    interpreter.interpret("""(not "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(not 1)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("boolean", NumberValue(1), _))) =>
    }
  }
}
