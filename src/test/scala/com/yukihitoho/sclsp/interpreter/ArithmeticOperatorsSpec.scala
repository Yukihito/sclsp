package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.EvaluationError.{DivisionByZero, InvalidArgumentType, InvalidNumberOfArguments}
import com.yukihitoho.sclsp.evaluator.{NumberValue, StringValue}
import com.yukihitoho.sclsp.interpreter.InterpretationError.EvaluationError
import org.scalatest._

class ArithmeticOperatorsSpec extends FlatSpec with Matchers {
  // scalastyle:off
  "The + operator" should "return the sum of arguments" in new WithInterpreter {
    interpreter.interpret("(+ 1)") should be (Right(NumberValue(1)))
    interpreter.interpret("(+ 1 2)") should be (Right(NumberValue(3)))
    interpreter.interpret("(+ 1 2 3 5)") should be (Right(NumberValue(11)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments equals 0"  in new WithInterpreter {
    interpreter.interpret("(+)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, true, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("""(+ 1 "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(+ "hoge" 1)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
  }

  "The - operator" should "return the difference of arguments" in new WithInterpreter {
    interpreter.interpret("(- 1)") should be (Right(NumberValue(1)))
    interpreter.interpret("(- 1 2)") should be (Right(NumberValue(-1)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments equals 0"  in new WithInterpreter {
    interpreter.interpret("(-)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, true, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("""(- 1 "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(- "hoge" 1)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
  }

  "The * operator" should "return the product of arguments" in new WithInterpreter {
    interpreter.interpret("(* 1)") should be (Right(NumberValue(1)))
    interpreter.interpret("(* 1 2)") should be (Right(NumberValue(2)))
    interpreter.interpret("(* 1 2 3 5)") should be (Right(NumberValue(30)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments equals 0"  in new WithInterpreter {
    interpreter.interpret("(*)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0,  true, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("""(* 1 "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(* "hoge" 1)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
  }

  "The / operator" should "return the quotient of arguments" in new WithInterpreter {
    interpreter.interpret("(/ 1)") should be (Right(NumberValue(1)))
    interpreter.interpret("(/ 1 2)") should be (Right(NumberValue(0.5)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments equals 0"  in new WithInterpreter {
    interpreter.interpret("(/)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(1, 0, true, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("""(/ 1 "hoge")""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
    interpreter.interpret("""(/ "hoge" 1)""") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", StringValue("hoge"), _))) =>
    }
  }

  it should "return DivisionByZero if the denominator equals 0"  in new WithInterpreter {
    interpreter.interpret("(/ 1 0)") should matchPattern {
      case Left(EvaluationError(DivisionByZero(_))) =>
    }
  }
  // scalastyle:on
}
