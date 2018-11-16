package com.yukihitoho.sclsp.modules.prelude

import com.yukihitoho.sclsp.evaluator._

object ListOp extends VariableFactory {
  private val variable = BuiltinVariable(new PrimitiveProcedureValue {
    override protected def call(arguments: List[Value], stackTrace: StackTrace): Either[EvaluationError, Value] =
      Right(arguments.reverse.foldLeft[Value](NilValue) { (acc, value) => PairValue(value, acc, None) })

    override def builtinSymbol: String = "list"
  })

  override def create(): Variable = variable
}
