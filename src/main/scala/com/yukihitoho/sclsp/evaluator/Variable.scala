package com.yukihitoho.sclsp.evaluator

trait Variable {
  def name: String
  def value: Value
}

case class UserDefinedVariable(symbol: SymbolValue, value: Value) extends Variable {
  override def name: String = symbol.value
}

case class BuiltinVariable(value: Value with Builtin) extends Variable {
  override def name: String = value.builtinSymbol
}

trait VariablesRepository {
  def find(name: String): Option[Variable]

  def store(variable: Variable): Unit
}

trait VariableFactory {
  def create(): Variable
}
