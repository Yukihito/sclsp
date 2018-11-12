package com.yukihitoho.sclsp.interpreter

import com.yukihitoho.sclsp.evaluator
import com.yukihitoho.sclsp.evaluator._
import com.yukihitoho.sclsp.parsing
import com.yukihitoho.sclsp.parsing.Parser

trait Interpreter {
  import InterpretationError._
  protected val parser: Parser
  protected val evaluator: Evaluator
  protected val modules: Seq[Module]
  protected val environmentFactory: EnvironmentFactory
  private lazy val environment: Environment = environmentFactory.create(None).store(modules)

  def interpret(src: String): Either[InterpretationError, Value] =
    for {
      ast <- parser.parseToNode(src).right.map(_.toAST).left.map(ParsingError)
      value <- evaluator.evaluate(ast, environment).left.map(EvaluationError)
    } yield value
}

trait InterpretationError

object InterpretationError {
  case class ParsingError(parsingError: parsing.ParsingError) extends InterpretationError
  case class EvaluationError(evaluationError: evaluator.EvaluationError) extends InterpretationError
}
