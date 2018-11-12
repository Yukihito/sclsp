package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator.EvaluationError.{InvalidArgumentType, InvalidNumberOfArguments}
import com.yukihitoho.sclsp.evaluator._

object Not extends VariableFactory {
  private val variable = BuiltinVariable(new PrimitiveProcedureValue {
    override protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value] =
      for {
        _ <- Either.cond(arguments.length == 1, (), InvalidNumberOfArguments(1, arguments.length, variadic = false, stackTrace.toList))
        boolean <- arguments.head match {
          case BooleanValue(v) => Right(v)
          case invalidTypeValue => Left(InvalidArgumentType("boolean", invalidTypeValue, stackTrace.toList))
        }
      } yield BooleanValue(!boolean)

    override def builtinSymbol: String = "not"
  })

  override def create(): Variable = variable
}
