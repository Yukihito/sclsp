package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator.EvaluationError.{DivisionByZero, InvalidArgumentType, InvalidNumberOfArguments}
import com.yukihitoho.sclsp.evaluator.{BooleanValue, NumberValue, StringValue}
import com.yukihitoho.sclsp.interpreter.InterpretationError.EvaluationError
import org.scalatest._

class ComparisonOperatorsSpec extends FlatSpec with Matchers {
  "The == operator" should "return true if the arguments are equal; false if they are not equal" in new WithInterpreter {
    interpreter.interpret("(== 1 1)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(== 1 2)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(== #t #t)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(== #f #f)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(== #t #f)") should be (Right(BooleanValue(false)))
    interpreter.interpret("""(== "hoge" "hoge")""") should be (Right(BooleanValue(true)))
    interpreter.interpret("""(== "hoge" "fuga")""") should be (Right(BooleanValue(false)))
    interpreter.interpret("""(== 1 "1")""") should be (Right(BooleanValue(false)))
    interpreter.interpret("(== lambda lambda)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(== lambda quote)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(== + +)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(== + -)") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(==)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(== 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(== 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  "The != operator" should "return true if the arguments are not equal; false if they are equal" in new WithInterpreter {
    interpreter.interpret("(!= 1 1)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(!= 1 2)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(!= #t #t)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(!= #f #f)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(!= #t #f)") should be (Right(BooleanValue(true)))
    interpreter.interpret("""(!= "hoge" "hoge")""") should be (Right(BooleanValue(false)))
    interpreter.interpret("""(!= "hoge" "fuga")""") should be (Right(BooleanValue(true)))
    interpreter.interpret("""(!= 1 "1")""") should be (Right(BooleanValue(true)))
    interpreter.interpret("(!= lambda lambda)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(!= lambda quote)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(!= + +)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(!= + -)") should be (Right(BooleanValue(true)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(==)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(== 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(== 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  "The > operator" should "return true if the lhs is greater than the rhs." in new WithInterpreter {
    interpreter.interpret("(> 2 1)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(> 1 1)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(> 0 1)") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(>)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(> 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(> 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("(> 1 #f)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
    interpreter.interpret("(> #f 1)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
  }

  "The >= operator" should "return true if the lhs is greater than or equal to the rhs." in new WithInterpreter {
    interpreter.interpret("(>= 2 1)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(>= 1 1)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(>= 0 1)") should be (Right(BooleanValue(false)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(>=)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(>= 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(>= 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("(>= 1 #f)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
    interpreter.interpret("(>= #f 1)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
  }

  "The < operator" should "return true if the lhs is less than the rhs." in new WithInterpreter {
    interpreter.interpret("(< 2 1)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(< 1 1)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(< 0 1)") should be (Right(BooleanValue(true)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(<)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(< 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(< 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("(< 1 #f)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
    interpreter.interpret("(< #f 1)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
  }

  "The <= operator" should "return true if the lhs is less than or equal to the rhs." in new WithInterpreter {
    interpreter.interpret("(<= 2 1)") should be (Right(BooleanValue(false)))
    interpreter.interpret("(<= 1 1)") should be (Right(BooleanValue(true)))
    interpreter.interpret("(<= 0 1)") should be (Right(BooleanValue(true)))
  }

  it should "return InvalidNumberOfArguments if the number of arguments is not equal to 2"  in new WithInterpreter {
    interpreter.interpret("(<=)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 0, false, _))) =>
    }
    interpreter.interpret("(<= 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 1, false, _))) =>
    }
    interpreter.interpret("(<= 1 1 1)") should matchPattern {
      case Left(EvaluationError(InvalidNumberOfArguments(2, 3, false, _))) =>
    }
  }

  it should "return InvalidArgumentType if arguments contain value that is not a number"  in new WithInterpreter {
    interpreter.interpret("(<= 1 #f)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
    interpreter.interpret("(<= #f 1)") should matchPattern {
      case Left(EvaluationError(InvalidArgumentType("number", BooleanValue(false), _))) =>
    }
  }
}
