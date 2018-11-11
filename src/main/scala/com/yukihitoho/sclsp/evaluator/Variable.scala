package com.yukihitoho.sclsp.evaluator

trait Variable {
  def name: String
  def value: Value
}

case class UserDefinedVariable(symbol: SymbolValue, value: Value) extends Variable {
  override def name: String = symbol.value
}

case class BuiltinVariable(name: String, value: Value) extends Variable

trait VariablesRepository {
  def find(symbol: SymbolValue): Option[Variable]

  def store(variable: Variable): Unit
}

trait VariableFactory {
  def create(): Variable
}
