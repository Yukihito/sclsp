package com.yukihitoho.sclsp.implementations

import com.yukihitoho.sclsp.evaluator._

import scala.collection.mutable

object DefaultEnvironmentFactory extends EnvironmentFactory {
  override def create(argBase: Option[Environment]): Environment = {
    new Environment {
      override protected val base: Option[Environment] = argBase
      protected val variables: VariablesRepository = new DefaultVariablesRepository
      protected val environmentFactory: EnvironmentFactory = DefaultEnvironmentFactory
    }
  }
}

class DefaultVariablesRepository extends VariablesRepository {
  private val nameToVariables = new mutable.HashMap[String, Variable]

  override def find(symbol: SymbolValue): Option[Variable] = nameToVariables.get(symbol.value)

  override def store(variable: Variable): Unit = nameToVariables(variable.name) = variable
}
